package net.lishen.service.common.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.controller.common.req.ProjectSaveReq;
import net.lishen.controller.common.req.ProjectUpdateReq;
import net.lishen.dto.common.ProjectDTO;
import net.lishen.manager.common.ProjectManager;
import net.lishen.service.common.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.common.impl
 * @Project：atcloud-meter
 * @name：ProjectServiceImpl
 * @Date：2024-01-07 16:44
 * @Filename：ProjectServiceImpl
 */
@Service
@Slf4j
public class ProjectServiceImpl  implements ProjectService {
    @Resource
    private ProjectManager projectManager;
    @Override
    public List<ProjectDTO> list() {
        return projectManager.list();
    }

    @Override
    public int save(ProjectSaveReq projectSaveReq) {
        return projectManager.insert(projectSaveReq);
    }

    @Override
    public int update(ProjectUpdateReq projectUpdateReq) {

        return projectManager.update(projectUpdateReq);
    }

    @Override
    public int del(Long id) {
        return projectManager.del(id);
    }
}
