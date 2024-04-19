package net.lishen.manager.stress.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.lishen.controller.stress.req.StressCaseSaveReq;
import net.lishen.controller.stress.req.StressCaseUpdateReq;
import net.lishen.manager.stress.StressCaseManager;
import net.lishen.mapper.StressCaseMapper;
import net.lishen.model.StressCaseDO;
import net.lishen.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.stress.impl
 * @Project：atcloud-meter
 * @name：StressCaseManagerImpl
 * @Date：2024-01-18 11:16
 * @Filename：StressCaseManagerImpl
 */
@Component
public class StressCaseManagerImpl implements StressCaseManager {

    @Autowired
    private StressCaseMapper stressCaseMapper;

    @Override
    public StressCaseDO findById(Long projectId, Long caseId) {

        //通过lambdac查询
        LambdaQueryWrapper<StressCaseDO> queryWrapper = new LambdaQueryWrapper<StressCaseDO>()
                .eq(StressCaseDO::getProjectId, projectId)
                .eq(StressCaseDO::getId, caseId);
        StressCaseDO stressCaseDO = stressCaseMapper.selectOne(queryWrapper);
        return stressCaseDO;


    }

    @Override
    public int delteById(Long id, Long projectId) {
        //通过lambda删除
        LambdaQueryWrapper<StressCaseDO> queryWrapper = new LambdaQueryWrapper<StressCaseDO>()
                .eq(StressCaseDO::getProjectId, projectId)
                .eq(StressCaseDO::getId, id);
        return  stressCaseMapper.delete(queryWrapper);
    }

    @Override
    public int save(StressCaseSaveReq req) {
        StressCaseDO stressCaseDO = SpringBeanUtil.copyProperties(req, StressCaseDO.class);
        return stressCaseMapper.insert(stressCaseDO);

    }

    @Override
    public int update(StressCaseUpdateReq req) {

        LambdaQueryWrapper<StressCaseDO> queryWrapper = new LambdaQueryWrapper<StressCaseDO>()
                .eq(StressCaseDO::getProjectId, req.getProjectId())
                .eq(StressCaseDO::getId, req.getId());

        StressCaseDO stressCaseDO = SpringBeanUtil.copyProperties(req, StressCaseDO.class);
        return stressCaseMapper.update(stressCaseDO, queryWrapper);

    }


}
