package net.lishen.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiCaseModuleDelReq;
import net.lishen.controller.api.req.ApiCaseModuleSaveReq;
import net.lishen.controller.api.req.ApiCaseModuleUpdateReq;
import net.lishen.dto.api.ApiCaseDTO;
import net.lishen.dto.api.ApiCaseModuleDTO;
import net.lishen.mapper.ApiCaseMapper;
import net.lishen.mapper.ApiCaseModuleMapper;
import net.lishen.mapper.ApiCaseStepMapper;
import net.lishen.model.ApiCaseDO;
import net.lishen.model.ApiCaseModuleDO;
import net.lishen.model.ApiCaseStepDO;
import net.lishen.service.api.ApiCaseModuleService;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.impl
 * @Project：gpcloud-meter
 * @name：ApiCaseModuleServiceImpl
 * @Date：2024-03-06 18:55
 * @Filename：ApiCaseModuleServiceImpl
 */
@Service
public class ApiCaseModuleServiceImpl implements ApiCaseModuleService {

    @Resource
    private ApiCaseModuleMapper apiCaseModuleMapper;


    @Resource
    private ApiCaseMapper apiCaseMapper;

    @Resource
    private ApiCaseStepMapper apiCaseStepMapper;

    @Override
    public List<ApiCaseModuleDTO> list(Long projectId) {
        LambdaQueryWrapper<ApiCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseModuleDO::getProjectId,projectId);
        List<ApiCaseModuleDO> list = apiCaseModuleMapper.selectList(queryWrapper);
        List<ApiCaseModuleDTO> resList = SpringBeanUtil.copyProperties(list, ApiCaseModuleDTO.class);
        resList.forEach(item -> {
            LambdaQueryWrapper<ApiCaseDO> lambdaQueryWrapper= new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ApiCaseDO::getModuleId,item.getId());
            List<ApiCaseDO> apiCaseDOS = apiCaseMapper.selectList(lambdaQueryWrapper);
            item.setList(SpringBeanUtil.copyProperties(apiCaseDOS, ApiCaseDTO.class));
        });
        return resList;
    }
    @Override
    public ApiCaseModuleDTO getById(Long projectId, Long moduleId) {
        LambdaQueryWrapper<ApiCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseModuleDO::getProjectId,projectId);
        queryWrapper.eq(ApiCaseModuleDO::getId,moduleId);
        ApiCaseModuleDO apiCaseModuleDO = apiCaseModuleMapper.selectOne(queryWrapper);
        ApiCaseModuleDTO apiCaseModuleDTO = SpringBeanUtil.copyProperties(apiCaseModuleDO, ApiCaseModuleDTO.class);

        LambdaQueryWrapper<ApiCaseDO> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiCaseDO::getModuleId,moduleId);
        List<ApiCaseDO> apiCaseDOS = apiCaseMapper.selectList(lambdaQueryWrapper);
        apiCaseModuleDTO.setList(SpringBeanUtil.copyProperties(apiCaseDOS, ApiCaseDTO.class));

        return apiCaseModuleDTO;
    }

    @Override
    public int save(ApiCaseModuleSaveReq req) {
        ApiCaseModuleDO apiCaseModuleDO = SpringBeanUtil.copyProperties(req, ApiCaseModuleDO.class);
        int insert = apiCaseModuleMapper.insert(apiCaseModuleDO);
        return insert;
    }

    @Override
    public int update(ApiCaseModuleUpdateReq req) {
        ApiCaseModuleDO apiCaseModuleDO = SpringBeanUtil.copyProperties(req, ApiCaseModuleDO.class);
        LambdaQueryWrapper<ApiCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseModuleDO::getId,req.getId());
        queryWrapper.eq(ApiCaseModuleDO::getProjectId,req.getProjectId());
        int update = apiCaseModuleMapper.update(apiCaseModuleDO, queryWrapper);
        return update;
    }

    @Override
    public int delete(ApiCaseModuleDelReq req) {
        LambdaQueryWrapper<ApiCaseModuleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseModuleDO::getId,req.getId());
        queryWrapper.eq(ApiCaseModuleDO::getProjectId,req.getProjectId());
        int delete = apiCaseModuleMapper.delete(queryWrapper);

        //删除模块下的用例
        LambdaQueryWrapper<ApiCaseDO> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiCaseDO::getModuleId,req.getId());
        lambdaQueryWrapper.eq(ApiCaseDO::getProjectId,req.getProjectId());
        apiCaseMapper.delete(lambdaQueryWrapper);

        //删除用例下的列表
        List<ApiCaseDO> apiCaseDOList = apiCaseMapper.selectList(lambdaQueryWrapper);
        apiCaseDOList.forEach(apiCaseDO -> {
            LambdaQueryWrapper<ApiCaseStepDO> lambdaQueryWrapper1= new LambdaQueryWrapper<>();

            lambdaQueryWrapper1.eq(ApiCaseStepDO::getCaseId,apiCaseDO.getId());
            apiCaseStepMapper.delete(lambdaQueryWrapper1);
        });


        return delete;
    }
}
