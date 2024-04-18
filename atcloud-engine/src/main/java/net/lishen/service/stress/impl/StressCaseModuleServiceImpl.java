package net.lishen.service.stress.impl;

import lombok.extern.slf4j.Slf4j;
import net.lishen.controller.stress.req.StressCaseModuleSaveReq;
import net.lishen.controller.stress.req.StressCaseModuleUpdateReq;
import net.lishen.dto.stress.StressCaseModuleDTO;
import net.lishen.manager.stress.StressCaseModuleManager;
import net.lishen.service.stress.StressCaseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress.impl
 * @Project：gpcloud-meter
 * @name：StressCaseMoudleServiceImpl
 * @Date：2024-01-18 22:29
 * @Filename：StressCaseMoudleServiceImpl
 */
@Service
@Slf4j
public class StressCaseModuleServiceImpl implements StressCaseModuleService {

    @Autowired
    private StressCaseModuleManager stressCaseModuleManager;

    @Override
    public List<StressCaseModuleDTO> list(Long projectId) {
        return stressCaseModuleManager.list(projectId);
    }

    @Override
    public StressCaseModuleDTO findById(Long projectId, Long moudleId) {
        return stressCaseModuleManager.findById(projectId,moudleId);
    }

    @Override
    public int delete(Long id, Long projectId) {
        return stressCaseModuleManager.delete(id,projectId);
    }

    @Override
    public int save(StressCaseModuleSaveReq req) {
        return stressCaseModuleManager.save(req);
    }

    @Override
    public int update(StressCaseModuleUpdateReq req) {
        return stressCaseModuleManager.update(req);
    }
}
