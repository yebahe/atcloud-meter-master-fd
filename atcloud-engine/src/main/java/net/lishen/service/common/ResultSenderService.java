package net.lishen.service.common;

import net.lishen.dto.common.CaseInfoDTO;
import net.lishen.enums.ReportTypeEnum;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.common
 * @Project：atcloud-meter
 * @name：TestResultSenderService
 * @Date：2024-01-21 22:37
 * @Filename：TestResultSenderService
 */
public interface ResultSenderService {
    /**
     * 发送测试结果 mq
     * @param caseInfoDTO
     * @param reportTypeEnum
     * @param result
     */
    void sendResult(CaseInfoDTO caseInfoDTO, ReportTypeEnum reportTypeEnum, String result);

}
