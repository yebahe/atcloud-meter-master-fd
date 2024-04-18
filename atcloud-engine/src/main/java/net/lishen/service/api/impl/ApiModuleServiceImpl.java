package net.lishen.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiModuleSaveReq;
import net.lishen.controller.api.req.ApiModuleUpdateReq;
import net.lishen.dto.api.ApiDTO;
import net.lishen.dto.api.ApiModuleDTO;
import net.lishen.mapper.ApiMapper;
import net.lishen.mapper.ApiModuleMapper;
import net.lishen.model.ApiDO;
import net.lishen.model.ApiModuleDO;
import net.lishen.service.api.ApiModuleService;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.impl
 * @Project：gpcloud-meter
 * @name：ApiModuleServiceImpl
 * @Date：2024-03-06 16:43
 * @Filename：ApiModuleServiceImpl
 */
@Service
public class ApiModuleServiceImpl implements ApiModuleService {

    @Resource
    private ApiModuleMapper apiModuleMapper;


    @Resource
    private ApiMapper apiMapper;
    /**
     * 列出指定项目下的所有API模块信息。
     *
     * @param projectId 项目ID，用于查询该项目下的所有API模块。
     * @return 返回一个API模块信息列表，其中每个模块包含其下的所有API信息。
     */
    @Override
    public List<ApiModuleDTO> list(Long projectId) {
        // 根据项目ID查询对应的API模块信息
        LambdaQueryWrapper<ApiModuleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiModuleDO::getProjectId, projectId);
        List<ApiModuleDO> apiModuleDOS = apiModuleMapper.selectList(lambdaQueryWrapper);

        // 将查询到的API模块DO对象转换为DTO对象
        List<ApiModuleDTO> apiModuleDTOS = SpringBeanUtil.copyProperties(apiModuleDOS, ApiModuleDTO.class);

        // 遍历DTO列表，为每个模块查询并设置其下的API信息
        apiModuleDTOS.forEach(apiModuleDTO -> {
            LambdaQueryWrapper<ApiDO> apiDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apiDOLambdaQueryWrapper.eq(ApiDO::getModuleId,apiModuleDTO.getId()).orderByDesc(ApiDO::getId);
            // 查询指定模块下的所有API信息，并进行转换
            List<ApiDO> apiDOList = apiMapper.selectList(apiDOLambdaQueryWrapper);
            apiModuleDTO.setList(SpringBeanUtil.copyProperties(apiDOList, ApiDTO.class));
        });

        return apiModuleDTOS;
    }

    @Override
    public ApiModuleDTO getById(Long projectId, Long moduleId) {

        LambdaQueryWrapper<ApiModuleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiModuleDO::getProjectId,projectId).eq(ApiModuleDO::getId,moduleId);
        ApiModuleDTO apiModuleDTO = SpringBeanUtil.copyProperties(apiModuleMapper.selectOne(lambdaQueryWrapper), ApiModuleDTO.class);
        //查询模块下面 的 关联接口
        if(apiModuleDTO!=null){
            LambdaQueryWrapper<ApiDO> apiDOWrapper = new LambdaQueryWrapper<>();
            apiDOWrapper.eq(ApiDO::getModuleId,moduleId).orderByDesc(ApiDO::getId);
            List<ApiDO> apiDOS = apiMapper.selectList(apiDOWrapper);
            apiModuleDTO.setList(SpringBeanUtil.copyProperties(apiDOS, ApiDTO.class));
        }
        return apiModuleDTO;
    }

    /**
     * 根据projectId和moduleId删除用例模块及其下的接口
     * @param id 模块的ID
     * @param projectId 项目的ID
     * @return 返回删除的行数
     */
    @Override
    public int delete(Long id, Long projectId) {
        // 构造查询条件，删除指定项目ID和模块ID的模块
        LambdaQueryWrapper<ApiModuleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ApiModuleDO::getProjectId,projectId).eq(ApiModuleDO::getId,id);
        int rows = apiModuleMapper.delete(lambdaQueryWrapper);

        // 如果成功删除模块，则删除该模块下的所有接口
        if(rows!=0){
            apiMapper.delete(new LambdaQueryWrapper<ApiDO>().eq(ApiDO::getModuleId,id).eq(ApiDO::getProjectId,projectId));
        }

        return rows;
    }

    @Override
    public int save(ApiModuleSaveReq req) {
        ApiModuleDO apiModuleDO = SpringBeanUtil.copyProperties(req, ApiModuleDO.class);
        if(apiModuleDO!=null){
            return apiModuleMapper.insert(apiModuleDO);
        }
        return -1;
    }

    @Override
    public int update(ApiModuleUpdateReq req) {
        ApiModuleDO apiModuleDO = SpringBeanUtil.copyProperties(req, ApiModuleDO.class);
        if (apiModuleDO!=null){
            LambdaQueryWrapper<ApiModuleDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ApiModuleDO::getId,req.getId())
                    .eq(ApiModuleDO::getProjectId,req.getProjectId());
            return apiModuleMapper.update(apiModuleDO,lambdaQueryWrapper);
        }
        return 0;
    }


}
