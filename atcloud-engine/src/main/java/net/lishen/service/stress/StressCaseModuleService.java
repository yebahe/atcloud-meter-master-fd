package net.lishen.service.stress;

import net.lishen.controller.stress.req.StressCaseModuleSaveReq;
import net.lishen.controller.stress.req.StressCaseModuleUpdateReq;
import net.lishen.dto.stress.StressCaseModuleDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress
 * @Project：gpcloud-meter
 * @name：StressCaseMoudleService
 * @Date：2024-01-18 22:29
 * @Filename：StressCaseMoudleService
 */
public interface StressCaseModuleService {
    List<StressCaseModuleDTO> list(Long projectId);

    StressCaseModuleDTO findById(Long projectId, Long moudleId);

    int delete(Long id, Long projectId);

    int save(StressCaseModuleSaveReq req);

    int update(StressCaseModuleUpdateReq req);
}
