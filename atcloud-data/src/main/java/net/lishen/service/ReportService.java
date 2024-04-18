package net.lishen.service;

import net.lishen.dto.ReportDTO;
import net.lishen.req.ReportSaveReq;
import net.lishen.req.ReportUpdateReq;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service
 * @Project：gpcloud-meter
 * @name：ReportService
 * @Date：2024-01-19 19:53
 * @Filename：ReportService
 */
public interface ReportService {
    ReportDTO save(ReportSaveReq req);

    /**
     * 更新状态
     * @param req
     */
    void updateReportState(ReportUpdateReq req);
}
