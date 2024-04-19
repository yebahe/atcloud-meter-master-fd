package net.lishen.service.stress.core;

import cn.hutool.core.util.IdUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lishen.dto.ReportDTO;
import net.lishen.enums.ReportStateEnum;
import net.lishen.feign.ReportFeignService;
import net.lishen.model.StressCaseDO;
import net.lishen.req.ReportUpdateReq;
import net.lishen.service.common.ResultSenderService;
import net.lishen.util.CustomFileUtil;
import net.lishen.util.StressTestUtil;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.SearchByClass;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.core
 * @Project：atcloud-meter
 * @name：BaseStressEngine
 * @Date：2024-01-22 11:38
 * @Filename：BaseStressEngine  执行引擎类
 */
@Data
@Slf4j
public abstract  class BaseStressEngine {

/**
 *
 *
 *
 */
    /**
     * 最终的测试计划
     */
    private HashTree testPlanHashTree;

    /**
     * 测试引擎
     */
    protected StandardJMeterEngine engine;

    /**
     * 测试用例
     */
    protected StressCaseDO stressCaseDO;

    /**
     * 测试报告
     */

    protected ReportDTO reportDTO;

    /**
     * spring的上下文
     */
    protected ApplicationContext applicationContext;
    /**
     * 模板方法 ，具体的开启压测的逻辑交给子类
     */
    public void startStressTest(){
        //初始化测试引擎
        this.initStressEngine();

        //组装测试计划
        //抽象方法
        this.assembleTestPlan();

        //把hashTree生成jmx文件方便本地调试、
        this.hashTreeToJmx();

        //运行测试
        this.run();

        //运行完用例后，清理内存资源
        this.clearData();

        //更新测试报告
        this.updateReport();
    }

    /**
     * 获取结果收集器  JMX与SIMPLE公用
     * @return
     */
    public EngineSampleCollector getSampleCollector(ResultSenderService resultSenderService){
        //Summariser对象
        Summariser summer = null;
        //Summariser名称
        String summaryName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (!summaryName.isEmpty()) {
            //创建Summariser对象
            summer = new Summariser(summaryName);
        }
        //使用自定义结果收集器
        EngineSampleCollector collector = new EngineSampleCollector(stressCaseDO, summer, resultSenderService, reportDTO);

        //若要调整收集器属性
        collector.setName(stressCaseDO.getName());
        collector.setEnabled(Boolean.TRUE);
        return collector;
    }
    /**
     * 更新测试报告
     */
    public void updateReport() {
        while (!engine.isActive()){
            ReportFeignService reportFeignService  = applicationContext.getBean(ReportFeignService.class);
            ReportUpdateReq reportUpdateReq= ReportUpdateReq.builder().id(reportDTO.getId())
                    .executeState(ReportStateEnum.COUNTING_REPORT.name())
                    .endTime(System.currentTimeMillis()).build();
            reportFeignService.update(reportUpdateReq);
            break;
        }
    }

    /**
     * 清理相关资源文件
     */
    public void clearData() {
        //寻找JMX里面的CSVDataSet
        SearchByClass<TestElement> testElementVisitor = new SearchByClass<>(TestElement.class);
        testPlanHashTree.traverse(testElementVisitor);

        Collection<TestElement> searchResults = testElementVisitor.getSearchResults();

        //提取里面是CSVDataSet的类，获取fileName 删除

        for(TestElement testElement: searchResults){
            if(testElement instanceof CSVDataSet csvDataSet){
                String filename = csvDataSet.getProperty("filename").getStringValue();
                Path path = Paths.get(filename);
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    log.error("删除文件失败",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 运行压测
     */
    public void run() {
        if(Objects.nonNull(testPlanHashTree)){
            engine.configure(testPlanHashTree);
            engine.run();
        }
    }

    /**
     * 把测试计划转为本地jmx文件
     */
    public void hashTreeToJmx() {
        try{
            StressTestUtil.initJmeterProperties();
            SaveService.loadProperties();
            String dir = System.getProperty("user.dir")+ File.separator+"static";
            CustomFileUtil.mkdir(dir);
            String tempJmxPath = dir +File.separator+ IdUtil.simpleUUID()+".jmx";
            SaveService.saveTree(testPlanHashTree, new FileOutputStream(tempJmxPath));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("测试计划hashTree转本地jmx失败");
        }
    }

    /**
     * 交给子类实现组装测试计划，jmx simple
     */
    public abstract void assembleTestPlan();

    /**
     * 初始化测试引擎
     */
    public void initStressEngine() {
        engine = StressTestUtil.getStandardJmeterEngine();
    }



}
