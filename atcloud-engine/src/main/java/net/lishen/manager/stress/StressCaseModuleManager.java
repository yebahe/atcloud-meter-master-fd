package net.lishen.manager.stress;

import net.lishen.controller.stress.req.StressCaseModuleSaveReq;
import net.lishen.controller.stress.req.StressCaseModuleUpdateReq;
import net.lishen.dto.stress.StressCaseModuleDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.stress
 * @Project：gpcloud-meter
 * @name：StressCaseMoudleManager
 * @Date：2024-01-18 22:44
 * @Filename：StressCaseMoudleManager
 */
public interface StressCaseModuleManager {
    List<StressCaseModuleDTO> list(Long projectId);

    StressCaseModuleDTO findById(Long projectId, Long moudleId);

    int delete(Long id, Long projectId);

    int save(StressCaseModuleSaveReq req);

    int update(StressCaseModuleUpdateReq req);
}
