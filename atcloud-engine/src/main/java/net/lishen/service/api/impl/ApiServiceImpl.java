package net.lishen.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiDelReq;
import net.lishen.controller.api.req.ApiSaveReq;
import net.lishen.controller.api.req.ApiUpdateReq;
import net.lishen.dto.api.ApiDTO;
import net.lishen.mapper.ApiMapper;
import net.lishen.model.ApiDO;
import net.lishen.service.api.ApiService;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.impl
 * @Project：atcloud-meter
 * @name：ApiServiceImpl
 * @Date：2024-03-06 18:12
 * @Filename：ApiServiceImpl
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Resource
    private ApiMapper apiMapper;

    @Override
    public ApiDTO getById(Long projectId, Long id) {
        //根据projectId和id查找api对象
        LambdaQueryWrapper<ApiDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDO::getProjectId, projectId).eq(ApiDO::getId, id);
        ApiDO apiDO = apiMapper.selectOne(queryWrapper);
        ApiDTO apiDTO = SpringBeanUtil.copyProperties(apiDO, ApiDTO.class);
        return apiDTO;
    }

    @Override
    public int save(ApiSaveReq req) {
        ApiDO apiDO = SpringBeanUtil.copyProperties(req, ApiDO.class);
        int insert = apiMapper.insert(apiDO);
        return insert;
    }

    @Override
    public int update(ApiUpdateReq req) {
        ApiDO apiDO = SpringBeanUtil.copyProperties(req, ApiDO.class);
        LambdaQueryWrapper<ApiDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDO::getProjectId, req.getProjectId()).eq(ApiDO::getId,req.getId());
        int update = apiMapper.update(apiDO, queryWrapper);
        return update;
    }

    @Override
    public int delete(ApiDelReq req) {
        LambdaQueryWrapper<ApiDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDO::getProjectId, req.getProjectId()).eq(ApiDO::getId,req.getId());
        int delete = apiMapper.delete(queryWrapper);
        return delete;
    }


}
