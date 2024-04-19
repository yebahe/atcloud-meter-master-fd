package net.lishen.service.stress.impl;

import jakarta.annotation.Resource;
import net.lishen.controller.stress.req.StressCaseSaveReq;
import net.lishen.controller.stress.req.StressCaseUpdateReq;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.stress.StressCaseDTO;
import net.lishen.enums.BizCodeEnum;
import net.lishen.enums.ReportStateEnum;
import net.lishen.enums.ReportTypeEnum;
import net.lishen.enums.StressSourceTypeEnum;
import net.lishen.exeception.BizException;
import net.lishen.feign.ReportFeignService;
import net.lishen.manager.stress.StressCaseManager;
import net.lishen.mapper.EnvironmentMapper;
import net.lishen.model.EnvironmentDO;
import net.lishen.model.StressCaseDO;
import net.lishen.req.ReportSaveReq;
import net.lishen.service.stress.StressCaseService;
import net.lishen.service.stress.core.BaseStressEngine;
import net.lishen.service.stress.core.StressJmxEngine;
import net.lishen.service.stress.core.StressSimpleEngine;
import net.lishen.util.JsonData;
import net.lishen.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.impl
 * @Project：atcloud-meter
 * @name：StressCaseServiceImpl
 * @Date：2024-01-18 10:31
 * @Filename：StressCaseServiceImpl
 */
@Service
public class StressCaseServiceImpl implements StressCaseService {
    @Autowired
    private StressCaseManager stressCaseManager;

    @Resource
    private EnvironmentMapper environmentMapper;

    @Override
    public StressCaseDTO findById(Long projectId, Long caseId) {
        StressCaseDO stressCaseDO = stressCaseManager.findById(projectId, caseId);
        StressCaseDTO stressCaseDTO = SpringBeanUtil.copyProperties(stressCaseDO, StressCaseDTO.class);
        return stressCaseDTO;
    }

    @Override
    public int del(Long id, Long projectId) {
        return stressCaseManager.delteById(id, projectId);
    }

    @Override
    public int save(StressCaseSaveReq req) {
        return stressCaseManager.save(req);
    }


    @Autowired
    private ReportFeignService reportFeignService;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public int update(StressCaseUpdateReq req) {
        return stressCaseManager.update(req);
    }

    /**
     *执行用例
     * 1、查询用例详情
     * 2、初始化测试报告 （包括概述和明细）
     * 3、判断压测类型是jmx还是 simple在线组装
     * 4、初始化测试引擎
     * 5、组装测试计划
     * 6、执行压测
     * 7、发送压测明细
     * 8、压测完成清理数据
     * 9、通知压测结束
     */
    @Override
    @Async("testExecutor")
    public void execute(Long projectId, Long caseId) {
        //1、查询用例详情
        StressCaseDO stressCaseDO = stressCaseManager.findById(projectId, caseId);

        if(stressCaseDO!=null){
            //2、初始化测试报告 （包括概述和明细）
            // ===>  生成测试报告并且初始化、调用数据服务将测试报告保存在数据服务的数据库当中

            /*
            建造者模式来创建对象，类似于使用new关键字实例化一个对象。建造者模式的优势在于可以通过链式调用方法来设置对象的属性，
            使代码更加清晰易读，并且可以方便地处理对象的可选参数。最后，调用.build()方法来构建对象并返回
             */
            ReportSaveReq reportSaveReq = ReportSaveReq.builder().projectId(projectId).caseId(caseId)
                    .startTime(System.currentTimeMillis())
                    .executeState(ReportStateEnum.EXECUTING.name())
                    .name(stressCaseDO.getName())
                    .type(ReportTypeEnum.STRESS.name())
                    .build();
            JsonData jsonData = reportFeignService.save(reportSaveReq);
            if(jsonData.isSuccess()){
                ReportDTO reportDTO = jsonData.getData(ReportDTO.class);

                //3、判断压测类型是jmx还是 simple在线组装
                if(StressSourceTypeEnum.JMX.name().equals(stressCaseDO.getStressSourceType())){
                    runJmxStressCase(stressCaseDO,reportDTO);
                }else if(StressSourceTypeEnum.SIMPLE.name().equals(stressCaseDO.getStressSourceType())){
                    runSimpleStressCase(stressCaseDO,reportDTO);
                }else {
                    throw new BizException(BizCodeEnum.STRESS_UNSUPPORTED);
                }
            }

        }




    }

    private void runJmxStressCase(StressCaseDO stressCaseDO, ReportDTO reportDTO) {
        //创建引擎
        BaseStressEngine baseStressEngine = new StressJmxEngine(stressCaseDO,reportDTO,applicationContext);

        baseStressEngine.startStressTest();
    }

    private void runSimpleStressCase(StressCaseDO stressCaseDO, ReportDTO reportDTO) {
        EnvironmentDO environmentDO = environmentMapper.selectById(stressCaseDO.getEnvironmentId());
        //创建引擎
        BaseStressEngine stressSimpleEngine = new StressSimpleEngine(environmentDO,stressCaseDO,reportDTO,applicationContext);
        stressSimpleEngine.startStressTest();
    }


}
