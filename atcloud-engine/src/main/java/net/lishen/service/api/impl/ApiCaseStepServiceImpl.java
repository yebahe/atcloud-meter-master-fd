package net.lishen.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiCaseStepSaveReq;
import net.lishen.controller.api.req.ApiCaseStepUpdateReq;
import net.lishen.mapper.ApiCaseStepMapper;
import net.lishen.model.ApiCaseStepDO;
import net.lishen.service.api.ApiCaseStepService;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.impl
 * @Project：atcloud-meter
 * @name：ApiCaseStepServiceImpl
 * @Date：2024-03-06 20:57
 * @Filename：ApiCaseStepServiceImpl
 */
@Service
public class ApiCaseStepServiceImpl implements ApiCaseStepService {

    @Resource
    private ApiCaseStepMapper apiCaseStepMapper;


    @Override
    public int save(ApiCaseStepSaveReq req) {
        ApiCaseStepDO apiCaseStepDO = SpringBeanUtil.copyProperties(req, ApiCaseStepDO.class);
        int insert = apiCaseStepMapper.insert(apiCaseStepDO);
        return insert;
    }

    @Override
    public int update(ApiCaseStepUpdateReq req) {
        ApiCaseStepDO apiCaseStepDO = SpringBeanUtil.copyProperties(req, ApiCaseStepDO.class);
        LambdaQueryWrapper<ApiCaseStepDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiCaseStepDO::getId, req.getId())
                .eq(ApiCaseStepDO::getProjectId, req.getProjectId());
        int update = apiCaseStepMapper.update(apiCaseStepDO, lambdaQueryWrapper);
        return update;
    }

    @Override
    public int delete(Long id, Long projectId) {

        LambdaQueryWrapper<ApiCaseStepDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiCaseStepDO::getId, id)
                .eq(ApiCaseStepDO::getProjectId, projectId);
        int delete = apiCaseStepMapper.delete(lambdaQueryWrapper);
        return delete;
    }
}
