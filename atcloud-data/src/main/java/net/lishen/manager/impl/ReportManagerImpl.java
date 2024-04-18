package net.lishen.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.manager.ReportManager;
import net.lishen.mapper.ReportMapper;
import net.lishen.model.ReportDO;
import net.lishen.req.ReportSaveReq;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Component;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.impl
 * @Project：gpcloud-meter
 * @name：ReportManagerImpl
 * @Date：2024-01-19 19:54
 * @Filename：ReportManagerImpl
 */
@Component
@Slf4j
public class ReportManagerImpl implements ReportManager {

    @Resource
    private ReportMapper reportMapper;

    @Override
    public ReportDO save(ReportSaveReq req) {
        ReportDO reportDO = SpringBeanUtil.copyProperties(req, ReportDO.class);
        int rows = reportMapper.insert(reportDO);
        if(rows > 0){
            log.info("save report success");
            //查找插入后的，填充id
            LambdaQueryWrapper<ReportDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ReportDO::getId, reportDO.getId());

            ReportDO newReportDO = reportMapper.selectOne(queryWrapper);
            return newReportDO;
        }
        log.info("save report fail");
        return null;
    }
}
