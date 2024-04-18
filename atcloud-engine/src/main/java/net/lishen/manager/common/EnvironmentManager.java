package net.lishen.manager.common;

import net.lishen.controller.common.req.EnvironmentDelReq;
import net.lishen.controller.common.req.EnvironmentSaveReq;
import net.lishen.controller.common.req.EnvironmentUpdateReq;
import net.lishen.dto.common.EnvironmentDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager
 * @Project：gpcloud-meter
 * @name：EnvironmentManager
 * @Date：2024-01-07 20:53
 * @Filename：EnvironmentManager
 */
public interface EnvironmentManager {

    List<EnvironmentDTO> list(long projectId);

    int save(EnvironmentSaveReq req);

    int update(EnvironmentUpdateReq req);

    int del(EnvironmentDelReq req);
}
