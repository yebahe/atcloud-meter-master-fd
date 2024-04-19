package net.lishen.manager.stress.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.lishen.controller.stress.req.StressCaseModuleSaveReq;
import net.lishen.controller.stress.req.StressCaseModuleUpdateReq;
import net.lishen.dto.stress.StressCaseDTO;
import net.lishen.dto.stress.StressCaseModuleDTO;
import net.lishen.manager.stress.StressCaseModuleManager;
import net.lishen.mapper.StressCaseMapper;
import net.lishen.mapper.StressCaseModuleMapper;
import net.lishen.model.StressCaseDO;
import net.lishen.model.StressCaseModuleDO;
import net.lishen.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.stress.impl
 * @Project：atcloud-meter
 * @name：StressCaseModuleManagerImpl
 * @Date：2024-01-18 22:45
 * @Filename：StressCaseModuleManagerImpl
 */
@Component
public class StressCaseModuleManagerImpl implements StressCaseModuleManager {

    @Autowired
    private StressCaseModuleMapper stressCaseModuleMapper;

    @Autowired
    private StressCaseMapper stressCaseMapper;
    @Override
    public List<StressCaseModuleDTO> list(Long projectId) {
        LambdaQueryWrapper<StressCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StressCaseModuleDO::getProjectId, projectId);
        List<StressCaseModuleDO> stressCaseModuleDOS = stressCaseModuleMapper.selectList(queryWrapper);
        List<StressCaseModuleDTO> list = SpringBeanUtil.copyProperties(stressCaseModuleDOS, StressCaseModuleDTO.class);

        //再找每个模块下面的用例列表
        list.forEach(source->{
            //查询模块里面关联的压测用例
            LambdaQueryWrapper<StressCaseDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(StressCaseDO::getModuleId, source.getId());
            List<StressCaseDO> stressCaseDOS = stressCaseMapper.selectList(queryWrapper2);
            List<StressCaseDTO> res = SpringBeanUtil.copyProperties(stressCaseDOS, StressCaseDTO.class);
            source.setList(res);
        });
        return list;
    }

    @Override
    public StressCaseModuleDTO findById(Long projectId, Long moudleId) {
        LambdaQueryWrapper<StressCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StressCaseModuleDO::getProjectId, projectId).eq(StressCaseModuleDO::getId, moudleId);
        StressCaseModuleDO stressCaseModuleDO = stressCaseModuleMapper.selectOne(queryWrapper);
        if(stressCaseModuleDO==null){
            return null;
        }
        StressCaseModuleDTO stressCaseModuleDTO = SpringBeanUtil.copyProperties(stressCaseModuleDO, StressCaseModuleDTO.class);

        LambdaQueryWrapper<StressCaseDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(StressCaseDO::getModuleId, stressCaseModuleDO.getId());
        List<StressCaseDO> stressCaseDOS = stressCaseMapper.selectList(queryWrapper2);
        List<StressCaseDTO> stressCaseDTOS = SpringBeanUtil.copyProperties(stressCaseDOS, StressCaseDTO.class);

        stressCaseModuleDTO.setList(stressCaseDTOS);
        return stressCaseModuleDTO;
    }

    @Override
    public int delete(Long id, Long projectId) {
        LambdaQueryWrapper<StressCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StressCaseModuleDO::getProjectId, projectId).eq(StressCaseModuleDO::getId, id);
        int delete = stressCaseModuleMapper.delete(queryWrapper);
        if(delete>0){
            //模块下的用例也删除
            LambdaQueryWrapper<StressCaseDO> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(StressCaseDO::getModuleId, id);
            stressCaseMapper.delete(queryWrapper2);
        }

        return delete;
    }

    @Override
    public int save(StressCaseModuleSaveReq req) {
        StressCaseModuleDO stressCaseModuleDO = SpringBeanUtil.copyProperties(req, StressCaseModuleDO.class);
        return stressCaseModuleMapper.insert(stressCaseModuleDO);
    }

    @Override
    public int update(StressCaseModuleUpdateReq req) {
        LambdaQueryWrapper<StressCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StressCaseModuleDO::getId, req.getId());
        queryWrapper.eq(StressCaseModuleDO::getProjectId, req.getProjectId());

        StressCaseModuleDO stressCaseModuleDO = SpringBeanUtil.copyProperties(req, StressCaseModuleDO.class);
        return stressCaseModuleMapper.update(stressCaseModuleDO, queryWrapper);
    }
}
