package net.lishen.service.api;

import net.lishen.controller.api.req.ApiDelReq;
import net.lishen.controller.api.req.ApiSaveReq;
import net.lishen.controller.api.req.ApiUpdateReq;
import net.lishen.dto.api.ApiDTO;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service
 * @Project：gpcloud-meter
 * @name：ApiService
 * @Date：2024-03-06 18:11
 * @Filename：ApiService
 */
public interface ApiService {
    ApiDTO getById(Long projectId, Long id);

    int save(ApiSaveReq req);

    int update(ApiUpdateReq req);

    int delete(ApiDelReq req);


}
