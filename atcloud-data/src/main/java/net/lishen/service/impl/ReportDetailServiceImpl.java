package net.lishen.service.impl;

import jakarta.annotation.Resource;
import net.lishen.dto.ApiCaseResultDTO;
import net.lishen.dto.ApiCaseResultItemDTO;
import net.lishen.dto.ApiCaseStepDTO;
import net.lishen.dto.StressSampleResultDTO;
import net.lishen.enums.ReportStateEnum;
import net.lishen.mapper.ReportDetailApiMapper;
import net.lishen.mapper.ReportDetailStressMapper;
import net.lishen.mapper.ReportMapper;
import net.lishen.model.ReportDO;
import net.lishen.model.ReportDetailApiDO;
import net.lishen.model.ReportDetailStressDO;
import net.lishen.service.ReportDetailService;
import net.lishen.util.JsonUtil;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.impl
 * @Project：gpcloud-meter
 * @name：ReportDetailServiceImpl
 * @Date：2024-02-22 20:58
 * @Filename：ReportDetailServiceImpl
 */
@Service
public class ReportDetailServiceImpl implements ReportDetailService {

    @Resource
    private ReportDetailStressMapper reportDetailStressMapper;


    @Resource
    private ReportDetailApiMapper reportDetailApiMapper;


    @Resource
    private ReportMapper reportMapper;

    @Override
    public void handleStressReportDetailMessage(String topicContent) {
        StressSampleResultDTO stressSampleResultDTO = JsonUtil.json2Obj(topicContent, StressSampleResultDTO.class);

        ReportDetailStressDO reportDetailStressDO = SpringBeanUtil.copyProperties(stressSampleResultDTO, ReportDetailStressDO.class);

        reportDetailStressMapper.insert(reportDetailStressDO);


    }

    @Override
    public void handleApiReportDetailMessage(String topicContent) {
        ApiCaseResultDTO apiCaseResultDTO = JsonUtil.json2Obj(topicContent, ApiCaseResultDTO.class);

        //测试报告概述
        ReportDO reportDO = reportMapper.selectById(apiCaseResultDTO.getReportId());
        reportDO.setEndTime(apiCaseResultDTO.getEndTime());
        reportDO.setExecuteState(ReportStateEnum.EXECUTE_SUCCESS.name());
        reportDO.setExpandTime(apiCaseResultDTO.getExpendTime());
        reportDO.setQuantity(Long.valueOf(apiCaseResultDTO.getQuantity()));
        reportDO.setFailQuantity(Long.valueOf(apiCaseResultDTO.getFailQuantity()));
        reportDO.setPassQuantity(Long.valueOf(apiCaseResultDTO.getPassQuantity()));
        //更新概述
        reportMapper.updateById(reportDO);

        //处理测试报告明细
        List<ApiCaseResultItemDTO> stepList = apiCaseResultDTO.getList();
        stepList.forEach(item -> {
            ReportDetailApiDO reportDetailApiDO = SpringBeanUtil.copyProperties(item, ReportDetailApiDO.class);
            ApiCaseStepDTO step = item.getApiCaseStep();
            reportDetailApiDO.setEnvironmentId(step.getEnvironmentId());
            reportDetailApiDO.setCaseId(step.getCaseId());
            reportDetailApiDO.setNum(step.getNum());
            reportDetailApiDO.setName(step.getName());
            reportDetailApiDO.setDescription(step.getDescription());
            reportDetailApiDO.setAssertion(step.getAssertion());
            reportDetailApiDO.setRelation(step.getRelation());
            reportDetailApiDO.setPath(step.getPath());
            reportDetailApiDO.setMethod(step.getMethod());
            reportDetailApiDO.setQuery(step.getQuery());
            reportDetailApiDO.setHeader(step.getHeader());
            reportDetailApiDO.setBody(step.getBody());
            reportDetailApiDO.setBodyType(step.getBodyType());
            reportDetailApiMapper.insert(reportDetailApiDO);

        });


    }
}
