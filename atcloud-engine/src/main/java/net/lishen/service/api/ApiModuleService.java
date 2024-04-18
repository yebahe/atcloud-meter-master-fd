package net.lishen.service.api;

import net.lishen.controller.api.req.ApiModuleSaveReq;
import net.lishen.controller.api.req.ApiModuleUpdateReq;
import net.lishen.dto.api.ApiModuleDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api
 * @Project：gpcloud-meter
 * @name：ApiModuleService
 * @Date：2024-03-06 16:42
 * @Filename：ApiModuleService
 */
public interface ApiModuleService {
    List<ApiModuleDTO> list(Long projectId);

    ApiModuleDTO getById(Long projectId, Long moduleId);

    int delete(Long id, Long projectId);



    int save(ApiModuleSaveReq req);

    int update(ApiModuleUpdateReq req);
}
