package net.lishen.service.common.impl;

import lombok.extern.slf4j.Slf4j;
import net.lishen.controller.common.req.EnvironmentDelReq;
import net.lishen.controller.common.req.EnvironmentSaveReq;
import net.lishen.controller.common.req.EnvironmentUpdateReq;
import net.lishen.dto.common.EnvironmentDTO;
import net.lishen.manager.common.EnvironmentManager;
import net.lishen.service.common.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.common.impl
 * @Project：atcloud-meter
 * @name：EnvironmentServiceImpl
 * @Date：2024-01-07 20:10
 * @Filename：EnvironmentServiceImpl
 */
@Service
@Slf4j
public class EnvironmentServiceImpl  implements EnvironmentService {

    @Autowired
    private EnvironmentManager environmentManager;

    @Override
    public List<EnvironmentDTO> list(long projectId) {

        return environmentManager.list(projectId);
    }

    @Override
    public int save(EnvironmentSaveReq req) {
        return environmentManager.save(req);
    }

    @Override
    public int update(EnvironmentUpdateReq req) {
        return environmentManager.update(req);
    }

    @Override
    public int del(EnvironmentDelReq req) {
        return environmentManager.del(req);
    }
}
