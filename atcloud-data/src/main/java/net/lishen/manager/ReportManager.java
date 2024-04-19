package net.lishen.manager;

import net.lishen.model.ReportDO;
import net.lishen.req.ReportSaveReq;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager
 * @Project：atcloud-meter
 * @name：ReportManager
 * @Date：2024-01-19 19:53
 * @Filename：ReportManager
 */
public interface ReportManager {
    ReportDO save(ReportSaveReq req);
}
