package net.lishen.service.api;

import net.lishen.controller.api.req.ApiCaseModuleDelReq;
import net.lishen.controller.api.req.ApiCaseModuleSaveReq;
import net.lishen.controller.api.req.ApiCaseModuleUpdateReq;
import net.lishen.dto.api.ApiCaseModuleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api
 * @Project：atcloud-meter
 * @name：ApiCaseModuleService
 * @Date：2024-03-06 18:54
 * @Filename：ApiCaseModuleService
 */
@Service
public interface ApiCaseModuleService {
    List<ApiCaseModuleDTO> list(Long projectId);

    ApiCaseModuleDTO getById(Long projectId, Long moduleId);

    int save(ApiCaseModuleSaveReq req);

    int update(ApiCaseModuleUpdateReq req);

    int delete(ApiCaseModuleDelReq req);
}
