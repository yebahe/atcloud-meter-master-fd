package net.lishen.service.stress.core;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.StressSampleResultDTO;
import net.lishen.dto.common.CaseInfoDTO;
import net.lishen.enums.ReportTypeEnum;
import net.lishen.model.StressCaseDO;
import net.lishen.service.common.ResultSenderService;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.visualizers.SamplingStatCalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.core
 * @Project：atcloud-meter
 * @name：EngineSimpleCollector
 * @Date：2024-01-21 23:06
 * @Filename：EngineSimpleCollector
 */
@Slf4j
public class EngineSampleCollector extends ResultCollector {

    //一个线程组多个请求，用map区分
    //SamplingStatCalculator是用来计算压测报告的一些相关指标的
    private Map<String, SamplingStatCalculator> calculatorMap = new HashMap<>();


    private ResultSenderService resultSenderService;

    private ReportDTO reportDTO;

    private StressCaseDO stressCaseDO;



    public EngineSampleCollector() {
        super();
    }

    public EngineSampleCollector(StressCaseDO stressCaseDO, Summariser summer, ResultSenderService resultSenderService, ReportDTO reportDTO) {
        super(summer);
        this.stressCaseDO = stressCaseDO;
        this.resultSenderService = resultSenderService;
        this.reportDTO = reportDTO;
    }

    @Override
    public void sampleOccurred(SampleEvent event) {
        super.sampleOccurred(event);
        //采样器结果
        SampleResult result = event.getResult();
        //请求名称，用作key
        String sampleLabel = result.getSampleLabel();

        //针对不同的请求，分别计算压测报告的指标
        SamplingStatCalculator calculator = calculatorMap.get(sampleLabel);
        if (calculator == null) {
            calculator = new SamplingStatCalculator(sampleLabel);
            calculator.addSample(result);
            calculatorMap.put(sampleLabel, calculator);
        }else {
            //如果计数器存在，就添加新的采样器结果到计算器计算
            calculator.addSample(result);
        }

        //封装采样器结果
        // 创建一个 StressSampleResultDTO 对象，用于存储压力测试结果的DTO信息
        StressSampleResultDTO sampleResultDTO = StressSampleResultDTO.builder()
                .reportId(reportDTO.getId()) // 设置报告的 ID
                .sampleTime(result.getTimeStamp()) // 设置采样时间
                .samplerLabel(result.getSampleLabel()) // 设置采样标签 也是计算器map的key
                .samplerCount(calculator.getCount()) // 设置采样计数
                .meanTime(calculator.getMean()) // 设置平均时间
                .minTime(calculator.getMin().intValue()) // 设置最小时间
                .maxTime(calculator.getMax().intValue()) // 设置最大时间
                .errorPercentage(calculator.getErrorPercentage()) // 设置错误百分比
                .errorCount(calculator.getErrorCount()) // 设置错误计数
                .requestRate(calculator.getRate()) // 设置请求速率
                .receiveKBPerSecond(calculator.getKBPerSecond()) // 设置接收 KB/秒
                .sentKBPerSecond(calculator.getSentKBPerSecond()) // 设置发送 KB/秒
                .requestLocation(result.getUrlAsString()) // 设置请求地址
                .requestHeader(result.getRequestHeaders()) // 设置请求头
                .requestBody(result.getSamplerData()) // 设置请求体
                .responseCode(result.getResponseCode()) // 设置响应码
                .responseData(result.getResponseDataAsString()) // 设置响应数据
                .responseHeader(result.getResponseHeaders()) // 设置响应头
                .build(); // 构建并返回 StressSampleResultDTO 对象

        // 获取结果的断言结果数组
        AssertionResult[] assertionResults = result.getAssertionResults();
        StringBuilder assertMsg = new StringBuilder();
        if(Objects.nonNull(assertionResults)){
            for(AssertionResult assertionResult : assertionResults){
                assertMsg.append("name=").append(assertionResult.getName())
                        .append(",msg=").append(assertionResult.getFailureMessage()).append(",");

            }
        }

        sampleResultDTO.setAssertInfo(assertMsg.toString());

        //序列化json
        String sampleResultDTOJsonString = JSON.toJSONString(sampleResultDTO);
        log.error(sampleResultDTOJsonString);
        //发送测试结果
        CaseInfoDTO caseInfoDTO = new CaseInfoDTO(stressCaseDO.getId(),stressCaseDO.getModuleId(),stressCaseDO.getName());

        resultSenderService.sendResult(caseInfoDTO, ReportTypeEnum.STRESS,sampleResultDTOJsonString);
    }
}
