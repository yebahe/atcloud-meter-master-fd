package net.lishen.manager.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.controller.common.req.EnvironmentDelReq;
import net.lishen.controller.common.req.EnvironmentSaveReq;
import net.lishen.controller.common.req.EnvironmentUpdateReq;
import net.lishen.dto.common.EnvironmentDTO;
import net.lishen.manager.common.EnvironmentManager;
import net.lishen.mapper.EnvironmentMapper;
import net.lishen.mapper.ProjectMapper;
import net.lishen.model.EnvironmentDO;
import net.lishen.model.ProjectDO;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.common.impl
 * @Project：gpcloud-meter
 * @name：EnvironmentManagerImpl
 * @Date：2024-01-07 20:56
 * @Filename：EnvironmentManagerImpl
 */
@Slf4j
@Component
public class EnvironmentManagerImpl implements EnvironmentManager {

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private EnvironmentMapper environmentMapper;

    @Override
    public List<EnvironmentDTO> list(long projectId) {

        List<EnvironmentDO> environmentDOList = environmentMapper.selectList(new QueryWrapper<EnvironmentDO>().eq("project_id", projectId));

        return SpringBeanUtil.copyProperties(environmentDOList, EnvironmentDTO.class);

    }

    @Override
    public int save(EnvironmentSaveReq req) {
        ProjectDO projectDO = projectMapper.selectById(req.getProjectId());
        if(projectDO!=null){
            EnvironmentDO environmentDO = SpringBeanUtil.copyProperties(req, EnvironmentDO.class);
            return environmentMapper.insert(environmentDO);
        }
        return -1;
    }

    @Override
    public int update(EnvironmentUpdateReq req) {
        ProjectDO projectDO = projectMapper.selectById(req.getProjectId());
        LambdaQueryWrapper<EnvironmentDO> queryWrapper = new LambdaQueryWrapper<EnvironmentDO>()
                .eq(EnvironmentDO::getId, req.getId())
                .eq(EnvironmentDO::getProjectId, req.getProjectId());
        EnvironmentDO environmentDO = environmentMapper.selectOne(queryWrapper);
        if(projectDO!=null && environmentDO!=null){
            return environmentMapper.updateById(SpringBeanUtil.copyProperties(req, EnvironmentDO.class));
        }
        return -1;
    }

    @Override
    public int del(EnvironmentDelReq req) {


        return environmentMapper.delete(new LambdaQueryWrapper<EnvironmentDO>()
                .eq(EnvironmentDO::getId, req.getId())
                .eq(EnvironmentDO::getProjectId, req.getProjectId()));

    }


}
