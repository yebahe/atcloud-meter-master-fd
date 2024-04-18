package net.lishen.service.stress.core;

import net.lishen.dto.KeyValueDTO;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.stress.CSVDataFileDTO;
import net.lishen.dto.stress.StressAssertionDTO;
import net.lishen.dto.stress.ThreadGroupConfigDTO;
import net.lishen.enums.StressAssertActionEnum;
import net.lishen.enums.StressAssertFieldFromEnum;
import net.lishen.model.EnvironmentDO;
import net.lishen.model.StressCaseDO;
import net.lishen.service.common.FileService;
import net.lishen.service.common.impl.KafkaResultSenderServiceImpl;
import net.lishen.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.core
 * @Project：gpcloud-meter
 * @name：StressSimpleEngine
 * @Date：2024-01-22 11:41
 * @Filename：StressSimpleEngine
 */
public class StressSimpleEngine extends BaseStressEngine{

    private EnvironmentDO environmentDO;
    public StressSimpleEngine(){

    }
    public StressSimpleEngine(EnvironmentDO environmentDO, StressCaseDO stressCaseDO, ReportDTO reportDTO, ApplicationContext applicationContext) {
        this.environmentDO = environmentDO;
        super.stressCaseDO = stressCaseDO;
        super.reportDTO = reportDTO;
        super.applicationContext = applicationContext;
    }

    @Override
    public void assembleTestPlan() {
        //获取压测结果收集器
        EngineSampleCollector engineSampleCollector = super.getSampleCollector(applicationContext.getBean(KafkaResultSenderServiceImpl.class));

        //组装测试计划

        //创建hashTree
        ListedHashTree testHashTree = new ListedHashTree();

        //创建测试计划
        TestPlan testPlan = createTestPlan();
        //创建线程组
        ThreadGroup threadGroup = createThreadGroup();

        //，请求头和参数
        HeaderManager headerManager = createHeaderManager();

        //创建采样器
        HTTPSamplerProxy httpSamplerProxy = createHttpSamplerProxy();

        //创建断言列表
        List<ResponseAssertion> responseAssertionList = createResponseAssertionList();
        //创建参数化
        List<CSVDataSet> csvDataSetList = createCsvDataSetList();
        //组装到测试计划里面
        HashTree threadGroupHashTree = testHashTree.add(testPlan, threadGroup);

        //将http采样器添加到线程组
        threadGroupHashTree.add(httpSamplerProxy);

        //结果收集器添加到线程组下面
        threadGroupHashTree.add(engineSampleCollector);

        if(headerManager!=null){
            threadGroupHashTree.add(headerManager);
        }

        if(responseAssertionList!=null){
            threadGroupHashTree.add(responseAssertionList);
        }
        if(csvDataSetList!=null) {
            threadGroupHashTree.add(csvDataSetList);
        }

        super.setTestPlanHashTree(testHashTree);

        //
    }

    private List<CSVDataSet> createCsvDataSetList() {
        if(StringUtils.isBlank(stressCaseDO.getRelation())){
            return null;
        }
        FileService fileService = applicationContext.getBean(FileService.class);
        List<CSVDataFileDTO> csvDataFileDTOS = JsonUtil.json2List(stressCaseDO.getRelation(), CSVDataFileDTO.class);

        //list存储CSVDataSet
        List<CSVDataSet> csvDataSetList = new ArrayList<>(csvDataFileDTOS.size());

        // 定义CSV data set的列表，CSVDataSet坑，不能直接操作属性名，比如，csvDataSet.setFilename("xxxxx"); 不然不生效

        for (CSVDataFileDTO csvDataFileDTO : csvDataFileDTOS) {
            // 创建CSVDataSet并设置属性
            CSVDataSet csvDataSet = new CSVDataSet();
            csvDataSet.setName(csvDataFileDTO.getName());
            csvDataSet.setEnabled(true);
            csvDataSet.setProperty("fileEncoding", "UTF-8");
            csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
            csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
            // 设置分隔符
            csvDataSet.setProperty("delimiter", csvDataFileDTO.getDelimiter());
            // 设置是否引用数据
            csvDataSet.setProperty("quotedData", false);
            // 设置共享模式
            csvDataSet.setProperty("shareMode", "shareMode.all");
            // 设置是否停止线程
            csvDataSet.setProperty("stopThread", false);
            // 设置CSV文件路径
            String localTempFilePath = fileService.copyRemoteFileToLocalTempFile(csvDataFileDTO.getRemoteFilePath());

            csvDataSet.setProperty("filename", localTempFilePath);
            // 设置是否忽略第一行
            csvDataSet.setProperty("ignoreFirstLine", csvDataFileDTO.getIgnoreFirstLine());
            // 设置是否循环读取数据
            csvDataSet.setProperty("recycle", csvDataFileDTO.getRecycle());
            // 设置变量名
            csvDataSet.setProperty("variableNames", csvDataFileDTO.getVariableNames());

            csvDataSet.setProperty("fileEncoding","UTF-8");
            csvDataSetList.add(csvDataSet);
        }



        return csvDataSetList;
    }

    private List<ResponseAssertion> createResponseAssertionList() {
        if(StringUtils.isBlank(stressCaseDO.getAssertion())){
            return null;
        }
        //将断言转为DTO
        List<StressAssertionDTO> stressAssertionDTOS = JsonUtil.json2List(stressCaseDO.getAssertion(), StressAssertionDTO.class);

        //创建list存储ResponseAssertion

        List<ResponseAssertion> responseAssertionList = new ArrayList<>(stressAssertionDTOS.size());
        for (StressAssertionDTO stressAssertionDTO : stressAssertionDTOS) {
            //创建响应断言
            ResponseAssertion responseAssertion = new ResponseAssertion();
            responseAssertion.setName(stressAssertionDTO.getName());
            responseAssertion.setAssumeSuccess(false);
            //获取断言规则
            String action = stressAssertionDTO.getAction();

            StressAssertActionEnum stressAssertActionEnum = StressAssertActionEnum.valueOf(action);
            //匹配规则
            switch (stressAssertActionEnum){
                case CONTAIN -> responseAssertion.setToContainsType();
                case EQUAL -> responseAssertion.setToEqualsType();
                default -> throw new RuntimeException("不支持的断言规则");
            }

            //断言字段类型来源，响应头 响应体
            StressAssertFieldFromEnum filedFromEnum = StressAssertFieldFromEnum.valueOf(stressAssertionDTO.getFrom());

            switch (filedFromEnum){
                case RESPONSE_CODE -> responseAssertion.setTestFieldResponseCode();
                case RESPONSE_HEADER -> responseAssertion.setTestFieldResponseHeaders();
                case RESPONSE_DATA -> responseAssertion.setTestFieldResponseData();
                default -> throw new RuntimeException("不支持的断言字段来源");
            }
            //用户期望的值
            responseAssertion.addTestString(stressAssertionDTO.getValue());
            responseAssertionList.add(responseAssertion);
        }

        return responseAssertionList;
    }

    private HTTPSamplerProxy createHttpSamplerProxy() {
        // 设置HTTP请求的名称、协议、域名、端口、路径和方法
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();

        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        httpSampler.setEnabled(true);

        httpSampler.setName(stressCaseDO.getName());
        httpSampler.setProtocol(environmentDO.getProtocol());
        httpSampler.setDomain( environmentDO.getDomain());
        httpSampler.setPort(environmentDO.getPort());
//        httpSampler.setPath("/api/v1/test/query");
//        httpSampler.setPath(stressCaseDO.getPath());
        httpSampler.setProperty("HTTPSampler.path",stressCaseDO.getPath());
        httpSampler.setMethod(stressCaseDO.getMethod());

        httpSampler.setAutoRedirects(false);
        httpSampler.setUseKeepAlive(true);
        httpSampler.setFollowRedirects(true);
        httpSampler.setPostBodyRaw(true);

        //处理请求参数
        if(HttpMethod.GET.name().equals(stressCaseDO.getMethod())&&StringUtils.isNotBlank(stressCaseDO.getQuery())){
            List<KeyValueDTO> keyValueDTOS = JsonUtil.json2List(stressCaseDO.getQuery(), KeyValueDTO.class);
            for(KeyValueDTO keyValueDTO:keyValueDTOS){
                httpSampler.addArgument(keyValueDTO.getKey(),keyValueDTO.getValue());
            }
        }else {
            Arguments arguments = createArguments();
            httpSampler.setArguments(arguments);
        }

//        httpSampler.setMethod("GET");
//
//        // 添加请求参数
//        httpSampler.addArgument("id", "1");
        return httpSampler;
    }

    private Arguments createArguments() {
        Arguments argumentsManager = new Arguments();
        argumentsManager.setProperty(TestElement.TEST_CLASS,Argument.class.getName());

        argumentsManager.setProperty(TestElement.GUI_CLASS, HTTPArgumentsPanel.class.getName());

        //类型，当前没用，后续可以根据content-type判断
        String bodyType = stressCaseDO.getBodyType();

        HTTPArgument httpArgument = new HTTPArgument();
        httpArgument.setValue(stressCaseDO.getBody());

        httpArgument.setAlwaysEncoded(false);

        argumentsManager.addArgument(httpArgument);

        return argumentsManager;
    }

    private HeaderManager createHeaderManager() {
        if(StringUtils.isBlank(stressCaseDO.getHeader())){
            return null;
        }

        List<KeyValueDTO> requestHeaders = JsonUtil.json2List(stressCaseDO.getHeader(), KeyValueDTO.class);
        HeaderManager headerManager = new HeaderManager();
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());

        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        headerManager.setEnabled(true);
        headerManager.setName(stressCaseDO.getName()+" headers ");

        requestHeaders.forEach(keyValueConfig->{
            headerManager.add(new Header(keyValueConfig.getKey(),keyValueConfig.getValue()));
        });
        return headerManager  ;
    }

    /**
     * 创建线程组
     *
     * 根据给定的测试案例配置信息，创建并配置一个线程组对象。线程组定义了JMeter测试计划中并发执行测试样本的线程数量和行为。
     *
     * @return 配置好的ThreadGroup对象，用于在JMeter测试计划中使用。
     */
    private ThreadGroup createThreadGroup() {
        // 将测试案例中的线程组配置信息转换为DTO对象
        ThreadGroupConfigDTO configDTO = JsonUtil.json2Obj(stressCaseDO.getThreadGroupConfig(), ThreadGroupConfigDTO.class);

        // 创建线程组并设置基本属性
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        // 根据配置设置线程组的名称、线程数、 ramp-up期、是否在下一次迭代中使用相同的用户以及调度器设置
        threadGroup.setName(configDTO.getThreadGroupName());
        threadGroup.setNumThreads(configDTO.getNumThreads());
        threadGroup.setRampUp(configDTO.getRampUp());
        threadGroup.setIsSameUserOnNextIteration(true);
        threadGroup.setScheduler(false);
        threadGroup.setEnabled(true);
        // 设置线程组在样本错误时的行为
        threadGroup.setProperty(new StringProperty(ThreadGroup.ON_SAMPLE_ERROR,"continue"));

        // 如果启用了调度器，在线程组中设置持续时间和延迟
        if(configDTO.getSchedulerEnabled()){
            threadGroup.setScheduler(true);
            threadGroup.setDuration(configDTO.getDuration());
            threadGroup.setDelay(configDTO.getDelay());
        }

        // 创建并配置循环控制器，然后将其设置为线程组的采样器控制器
        LoopController loopController = createLoopController(configDTO.getLoopCount());
        threadGroup.setSamplerController(loopController);

        return  threadGroup;
    }


    private LoopController createLoopController(Integer loopCount) {

        LoopController loopController =new LoopController();
        loopController.setProperty(TestElement.TEST_CLASS,LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS,LoopController.class.getName());

        loopController.setLoops(loopCount);
        loopController.setFirst(true);
        loopController.initialize();

        loopController.setEnabled(true);
        return loopController;
    }

    private TestPlan createTestPlan() {
        //Test Plan，将Test Plan、Thread Group和HTTP Sampler添加到Test Plan树结构中
        TestPlan testPlan = new TestPlan(stressCaseDO.getName());
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        testPlan.setSerialized(true);
        testPlan.setTearDownOnShutdown(true);


        return testPlan;
    }
}
