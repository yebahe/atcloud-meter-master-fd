package net.lishen.manager.common.impl;

import jakarta.annotation.Resource;
import net.lishen.controller.common.req.ProjectSaveReq;
import net.lishen.controller.common.req.ProjectUpdateReq;
import net.lishen.dto.common.ProjectDTO;
import net.lishen.manager.common.ProjectManager;
import net.lishen.mapper.ProjectMapper;
import net.lishen.model.ProjectDO;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.common.impl
 * @Project：gpcloud-meter
 * @name：ProjectManagerImpl
 * @Date：2024-01-07 17:18
 * @Filename：ProjectManagerImpl
 */
@Component
public class ProjectManagerImpl implements ProjectManager {

    @Resource
    private ProjectMapper projectMapper;
    @Override
    public List<ProjectDTO> list() {
        List<ProjectDO> projectDOS = projectMapper.selectList(null);

        return SpringBeanUtil.copyProperties(projectDOS, ProjectDTO.class);
    }

    @Override
    public int insert(ProjectSaveReq projectSaveReq) {
        ProjectDO projectDO = SpringBeanUtil.copyProperties(projectSaveReq, ProjectDO.class);
        return projectMapper.insert(projectDO);
    }

    @Override
    public int update(ProjectUpdateReq projectUpdateReq) {
        //更新
        ProjectDO projectDO = SpringBeanUtil.copyProperties(projectUpdateReq, ProjectDO.class);
        return projectMapper.updateById(projectDO);
    }

    @Override
    public int del(Long id) {
        return projectMapper.deleteById(id);
    }


}
