package net.lishen.service;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service
 * @Project：atcloud-meter
 * @name：ReportDetailService
 * @Date：2024-02-22 20:58
 * @Filename：ReportDetailService
 */
public interface ReportDetailService {
    void handleStressReportDetailMessage(String topicContent);

    void handleApiReportDetailMessage(String topicContent);

}
