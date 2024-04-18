package net.lishen.service.common;

import net.lishen.controller.common.req.ProjectSaveReq;
import net.lishen.controller.common.req.ProjectUpdateReq;
import net.lishen.dto.common.ProjectDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.common
 * @Project：gpcloud-meter
 * @name：ProjectService
 * @Date：2024-01-07 16:43
 * @Filename：ProjectService
 */
public interface ProjectService {
    /**
     * 用户的项目列表
     * @return
     */
    List<ProjectDTO> list();


    int save(ProjectSaveReq projectSaveReq);

    int update(ProjectUpdateReq projectUpdateReq);

    int del(Long id);
}
