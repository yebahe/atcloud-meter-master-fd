package net.lishen.manager.common;

import net.lishen.controller.common.req.ProjectSaveReq;
import net.lishen.controller.common.req.ProjectUpdateReq;
import net.lishen.dto.common.ProjectDTO;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.common
 * @Project：gpcloud-meter
 * @name：ProjectManager
 * @Date：2024-01-07 17:10
 * @Filename：ProjectManager
 */
public interface ProjectManager {
    /**
     * 项目列表
     * @return
     */
    List<ProjectDTO> list();

    /**
     * 项目保存
     * @param projectSaveReq
     * @return
     */
    int insert(ProjectSaveReq projectSaveReq);


    int update(ProjectUpdateReq projectUpdateReq);

    int del(Long id);
}
