package net.lishen.service.api;

import net.lishen.controller.api.req.ApiCaseDelReq;
import net.lishen.controller.api.req.ApiCaseSaveReq;
import net.lishen.controller.api.req.ApiCaseUpdateReq;
import net.lishen.dto.api.ApiCaseDTO;
import net.lishen.util.JsonData;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service
 * @Project：gpcloud-meter
 * @name：ApiCaseService
 * @Date：2024-03-06 20:20
 * @Filename：ApiCaseService
 */
public interface ApiCaseService {
    ApiCaseDTO getById(Long projectId, Long id);

    int save(ApiCaseSaveReq req);

    int update(ApiCaseUpdateReq req);

    int delete(ApiCaseDelReq req);

    JsonData execute(Long projectId, Long caseId);
}
