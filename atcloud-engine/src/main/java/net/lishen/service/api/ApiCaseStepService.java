package net.lishen.service.api;

import net.lishen.controller.api.req.ApiCaseStepSaveReq;
import net.lishen.controller.api.req.ApiCaseStepUpdateReq;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api
 * @Project：atcloud-meter
 * @name：ApiCaseStepService
 * @Date：2024-03-06 20:56
 * @Filename：ApiCaseStepService
 */
public interface ApiCaseStepService {
    int save(ApiCaseStepSaveReq req);

    int update(ApiCaseStepUpdateReq req);

    int delete(Long id, Long projectId);


}
