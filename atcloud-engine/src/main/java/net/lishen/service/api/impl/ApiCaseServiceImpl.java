package net.lishen.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.controller.api.req.ApiCaseDelReq;
import net.lishen.controller.api.req.ApiCaseSaveReq;
import net.lishen.controller.api.req.ApiCaseUpdateReq;
import net.lishen.dto.ApiCaseResultDTO;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.api.ApiCaseDTO;
import net.lishen.dto.ApiCaseStepDTO;
import net.lishen.dto.common.CaseInfoDTO;
import net.lishen.enums.BizCodeEnum;
import net.lishen.enums.ReportStateEnum;
import net.lishen.enums.ReportTypeEnum;
import net.lishen.exeception.BizException;
import net.lishen.feign.ReportFeignService;
import net.lishen.mapper.ApiCaseMapper;
import net.lishen.mapper.ApiCaseStepMapper;
import net.lishen.model.ApiCaseDO;
import net.lishen.model.ApiCaseStepDO;
import net.lishen.req.ReportSaveReq;
import net.lishen.service.api.ApiCaseService;
import net.lishen.service.api.core.ApiExecuteEngine;
import net.lishen.util.JsonData;
import net.lishen.util.SpringBeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.impl
 * @Project：gpcloud-meter
 * @name：ApiCaseServiceImpl
 * @Date：2024-03-06 20:20
 * @Filename：ApiCaseServiceImpl
 */
@Service
@Slf4j
public class ApiCaseServiceImpl implements ApiCaseService {

    @Resource
    private ApiCaseMapper apiCaseMapper;

    @Resource
    private ApiCaseStepMapper apiCaseStepMapper;


    @Resource
    private ReportFeignService reportFeignService;

    @Override
    public ApiCaseDTO getById(Long projectId, Long id) {
        LambdaQueryWrapper<ApiCaseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseDO::getId, id).eq(ApiCaseDO::getProjectId, projectId);
        ApiCaseDO apiCaseDO = apiCaseMapper.selectOne(queryWrapper);
        ApiCaseDTO apiCaseDTO = SpringBeanUtil.copyProperties(apiCaseDO, ApiCaseDTO.class);

        LambdaQueryWrapper<ApiCaseStepDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ApiCaseStepDO::getCaseId, apiCaseDTO.getId());
        List<ApiCaseStepDO> apiCaseStepDOS = apiCaseStepMapper.selectList(queryWrapper1);
        List<ApiCaseStepDTO> apiCaseStepDTOS = SpringBeanUtil.copyProperties(apiCaseStepDOS, ApiCaseStepDTO.class);
        apiCaseDTO.setList(apiCaseStepDTOS);
        return apiCaseDTO;
    }

    @Override
    public int save(ApiCaseSaveReq req) {
        ApiCaseDO apiCaseDO = SpringBeanUtil.copyProperties(req, ApiCaseDO.class);
        int insert = apiCaseMapper.insert(apiCaseDO);
        req.getList().forEach(step->{
            ApiCaseStepDO apiCaseStepDO = SpringBeanUtil.copyProperties(step, ApiCaseStepDO.class);
            apiCaseStepDO.setCaseId(apiCaseDO.getId());
            apiCaseStepMapper.insert(apiCaseStepDO);
        });
        return insert;
    }

    @Override
    public int update(ApiCaseUpdateReq req) {
        ApiCaseDO apiCaseDO = SpringBeanUtil.copyProperties(req, ApiCaseDO.class);

        LambdaQueryWrapper<ApiCaseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseDO::getId, req.getId())
                .eq(ApiCaseDO::getProjectId, req.getProjectId());
        int update = apiCaseMapper.update(apiCaseDO, queryWrapper);



        return update;
    }

    @Override
    public int delete(ApiCaseDelReq req) {


        LambdaQueryWrapper<ApiCaseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseDO::getId, req.getId())
                .eq(ApiCaseDO::getProjectId, req.getProjectId());

        int delete = apiCaseMapper.delete(queryWrapper);

        //删除用例下的步骤
        LambdaQueryWrapper<ApiCaseStepDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ApiCaseStepDO::getCaseId, req.getId());
        apiCaseStepMapper.delete(queryWrapper1);
        return delete;
    }

    /**
     * 查询用例
     * 查询管理步骤
     * 初始化测试报告
     * 执行自动化测试
     * 响应结果
     * @param projectId
     * @param caseId
     * @return
     */
    @Override
    public JsonData execute(Long projectId, Long caseId) {
        LambdaQueryWrapper<ApiCaseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiCaseDO::getProjectId,projectId).eq(ApiCaseDO::getId,caseId);
        ApiCaseDO apiCaseDO = apiCaseMapper.selectOne(queryWrapper);
        if(apiCaseDO!=null){
            //查找用例关联步骤
            LambdaQueryWrapper<ApiCaseStepDO> stepQueryWrapper = new LambdaQueryWrapper<>();
            stepQueryWrapper.eq(ApiCaseStepDO::getCaseId,caseId).orderByDesc(ApiCaseStepDO::getNum);
            List<ApiCaseStepDO> apiCaseStepDOS = apiCaseStepMapper.selectList(stepQueryWrapper);
            if(apiCaseStepDOS==null || apiCaseStepDOS.isEmpty()){
                throw new BizException(BizCodeEnum.API_CASE_STEP_IS_EMPTY);
            }
            //初始化测试报告
            ReportSaveReq reportSaveReq = ReportSaveReq.builder().projectId(projectId)
                    .caseId(caseId)
                    .type(ReportTypeEnum.API.name())
                    .name(apiCaseDO.getName())
                    .executeState(ReportStateEnum.EXECUTING.name())
                    .startTime(System.currentTimeMillis())
                    .build();

            JsonData jsonData = reportFeignService.save(reportSaveReq);
            if(jsonData.isSuccess()) {
                //执行用例
                ReportDTO reportDTO = jsonData.getData(ReportDTO.class);
                CaseInfoDTO caseInfoDTO = new CaseInfoDTO();
                caseInfoDTO.setId(apiCaseDO.getId());
                caseInfoDTO.setName(apiCaseDO.getName());
                caseInfoDTO.setModuleId(apiCaseDO.getModuleId());

                ApiExecuteEngine apiExecuteEngine = new ApiExecuteEngine(reportDTO);
                ApiCaseResultDTO apiCaseResultDTO = apiExecuteEngine.execute(caseInfoDTO, apiCaseStepDOS);

                return JsonData.buildSuccess(apiCaseResultDTO);
            }else {
                log.error("API接口用例执行初始化测试报告失败");
                return JsonData.buildError("API接口用例执行初始化测试报告失败");
            }

        }else {
            return JsonData.buildError("用例不存在");
        }
    }
}
