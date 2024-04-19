package net.lishen.service.common;

import net.lishen.controller.common.req.EnvironmentDelReq;
import net.lishen.controller.common.req.EnvironmentSaveReq;
import net.lishen.controller.common.req.EnvironmentUpdateReq;
import net.lishen.dto.common.EnvironmentDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.common
 * @Project：atcloud-meter
 * @name：EnvironmentController
 * @Date：2024-01-07 20:09
 * @Filename：EnvironmentController
 */
public interface EnvironmentService {

    List<EnvironmentDTO> list(long projectId);

    int save(EnvironmentSaveReq req);

    int update(EnvironmentUpdateReq req);

    int del(EnvironmentDelReq req);
}
