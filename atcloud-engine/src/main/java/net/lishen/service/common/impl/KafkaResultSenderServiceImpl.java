package net.lishen.service.common.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.config.KafkaTopicConfig;
import net.lishen.dto.common.CaseInfoDTO;
import net.lishen.enums.ReportTypeEnum;
import net.lishen.service.common.ResultSenderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.common.impl
 * @Project：atcloud-meter
 * @name：KafkaResultSenderServiceImpl
 * @Date：2024-01-21 22:54
 * @Filename：KafkaResultSenderServiceImpl
 */
@Service
@Slf4j
public class KafkaResultSenderServiceImpl  implements ResultSenderService {

    private static final String TOPIC_KEY = "case_id_";

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sendResult(CaseInfoDTO caseInfoDTO, ReportTypeEnum reportTypeEnum, String result) {
        //根据ReportTypeEnum发送到不同的方法
        switch (reportTypeEnum){
            case STRESS:
                sendStressResult(caseInfoDTO, result);
                break;
            case API:
                sendApiResult(caseInfoDTO,result);
                break;
            case UI:
                sendUiResult(caseInfoDTO,result);
                break;
            default:
                log.error("未知的ReportTypeEnum");
                break;
        }

    }


    /**
     * 发送压测结果明细
     * @param caseInfoDTO
     * @param result
     */
    private void sendStressResult(CaseInfoDTO caseInfoDTO,String result){
        kafkaTemplate.send(KafkaTopicConfig.STRESS_TOPIC_NAME,TOPIC_KEY+caseInfoDTO.getId().toString(),result);
    }

    /**
     * 发送接口测试结果明细
     * @param caseInfoDTO
     * @param result
     */
    private void sendApiResult(CaseInfoDTO caseInfoDTO,String result){
        kafkaTemplate.send(KafkaTopicConfig.API_TOPIC_NAME,TOPIC_KEY+caseInfoDTO.getId().toString(),result);
    }

    /**
     *
     * @param caseInfoDTO
     * @param result
     */
    private void sendUiResult(CaseInfoDTO caseInfoDTO,String result){
        kafkaTemplate.send(KafkaTopicConfig.UI_TOPIC_NAME,TOPIC_KEY+caseInfoDTO.getId().toString(),result);
    }

}
