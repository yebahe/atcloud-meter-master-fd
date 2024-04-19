package net.lishen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.config.KafkaTopicConfig;
import net.lishen.dto.ReportDTO;
import net.lishen.enums.ReportStateEnum;
import net.lishen.manager.ReportManager;
import net.lishen.mapper.ReportDetailStressMapper;
import net.lishen.mapper.ReportMapper;
import net.lishen.model.ReportDO;
import net.lishen.model.ReportDetailStressDO;
import net.lishen.req.ReportSaveReq;
import net.lishen.req.ReportUpdateReq;
import net.lishen.service.ReportService;
import net.lishen.util.JsonUtil;
import net.lishen.util.SpringBeanUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.impl
 * @Project：atcloud-meter
 * @name：ReportServiceImpl
 * @Date：2024-01-19 19:53
 * @Filename：ReportServiceImpl
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportManager reportManager;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private ReportDetailStressMapper reportDetailStressMapper;

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public ReportDTO save(ReportSaveReq req) {
        ReportDO reportDO = reportManager.save(req);
        ReportDTO reportDTO = SpringBeanUtil.copyProperties(reportDO, ReportDTO.class);
        return reportDTO;
    }

    @Override
    public void updateReportState(ReportUpdateReq req) {
        ReportDTO reportDTO = ReportDTO.builder().id(req.getId())
                .executeState(req.getExecuteState())
                .endTime(req.getEndTime()).build();
        ReportDO reportDO = reportMapper.selectById(reportDTO.getId());

        //查找数据库测试报告明细
        LambdaQueryWrapper<ReportDetailStressDO> queryWrapper = new LambdaQueryWrapper<>(ReportDetailStressDO.class);

        queryWrapper.eq(ReportDetailStressDO::getReportId,reportDTO.getId());
        queryWrapper.orderByDesc(ReportDetailStressDO::getSamplerCount).last("limit 1");

        ReportDetailStressDO oldReportDetailStressDO = reportDetailStressMapper.selectOne(queryWrapper);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error("超时等待错误");

            reportDO.setExecuteState(ReportStateEnum.EXECUTE_FAIL.name());
        }

        ReportDetailStressDO newReportDetailStressDO = reportDetailStressMapper.selectOne(queryWrapper);
        if(newReportDetailStressDO.getSamplerCount()>oldReportDetailStressDO.getSamplerCount()){
            //有新数据 ，发送mq消息
            kafkaTemplate.send(KafkaTopicConfig.REPORT_STATE_TOPIC_NAME,"report_id"+reportDTO.getId(), JsonUtil.obj2Json(req));

        }else {
            //没更新，则处理完成，更改状态
            reportDO.setExecuteState(ReportStateEnum.EXECUTE_SUCCESS.name());
        }

        //处理聚合统计信息
        reportDO.setEndTime(reportDTO.getEndTime());
        reportDO.setExpandTime(reportDTO.getEndTime()-reportDO.getStartTime());
        reportDO.setQuantity(newReportDetailStressDO.getSamplerCount());
        reportDO.setFailQuantity(newReportDetailStressDO.getErrorCount());
        reportDO.setPassQuantity(reportDO.getQuantity()-reportDO.getFailQuantity());

        //摘要
        Map<String,Object> summaryMap = new HashMap<>();

        summaryMap.put("QPS",newReportDetailStressDO.getRequestRate());
        summaryMap.put("错误请求百分比",newReportDetailStressDO.getErrorPercentage());
        summaryMap.put("平均响应时间(毫秒)",newReportDetailStressDO.getMeanTime());
        summaryMap.put("最大响应时间(毫秒)",newReportDetailStressDO.getMaxTime());
        summaryMap.put("最小响应时间(毫秒)",newReportDetailStressDO.getMinTime());

        reportDO.setSummary(JsonUtil.obj2Json(summaryMap));

        //更新
        reportMapper.updateById(reportDO);
    }
}
