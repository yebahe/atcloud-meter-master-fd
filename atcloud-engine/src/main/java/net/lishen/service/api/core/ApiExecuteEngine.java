package net.lishen.service.api.core;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import net.lishen.dto.ApiCaseResultDTO;
import net.lishen.dto.ReportDTO;
import net.lishen.dto.ApiCaseResultItemDTO;
import net.lishen.dto.ApiCaseStepDTO;
import net.lishen.dto.common.CaseInfoDTO;
import net.lishen.enums.ApiBodyTypeEnum;
import net.lishen.enums.ReportTypeEnum;
import net.lishen.exeception.BizException;
import net.lishen.mapper.EnvironmentMapper;
import net.lishen.model.ApiCaseStepDO;
import net.lishen.model.EnvironmentDO;
import net.lishen.service.common.ResultSenderService;
import net.lishen.util.*;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.core
 * @Project：atcloud-meter
 * @name：ApiExecuteEngine
 * @Date：2024-03-07 21:06
 * @Filename：ApiExecuteEngine
 */
@Data
public class ApiExecuteEngine {

    private ReportDTO reportDTO;

    private EnvironmentMapper environmentMapper;

    private ResultSenderService resultSenderService;

    public ApiExecuteEngine(ReportDTO reportDTO){
        this.reportDTO = reportDTO;
        environmentMapper = SpringContextHolder.getBean(EnvironmentMapper.class);
        resultSenderService = SpringContextHolder.getBean(ResultSenderService.class);
    }

    /**
     * 重点、难点
     * @param caseInfoDTO
     * @param apiCaseStepDOList
     * @return
     */
    public ApiCaseResultDTO execute(CaseInfoDTO caseInfoDTO, List<ApiCaseStepDO> apiCaseStepDOList){

        try{
            int quantity = apiCaseStepDOList.size();
            long startTime = System.currentTimeMillis();
            Collections.sort(apiCaseStepDOList, (o1, o2) -> o1.getNum().compareTo(o2.getNum()));
            //进行执行
            ApiCaseResultDTO result = doExecute(null,apiCaseStepDOList);
            //结束时间
            long endTime = System.currentTimeMillis();
            result.setEndTime(endTime);
            result.setReportId(reportDTO.getId());
            result.setStartTime(startTime);
            result.setExpendTime(endTime-startTime);
            result.setQuantity(quantity);

            int passQuantity = result.getList().stream().filter(item -> {
                item.setReportId(reportDTO.getId());
                return item.getExecuteState() && item.getAssertionState();
            }).toList().size();

            result.setPassQuantity(passQuantity);
            result.setFailQuantity(quantity-passQuantity);
            result.setExecuteState(Objects.equals(result.getQuantity(),result.getPassQuantity()));

            //发送结果
            resultSenderService.sendResult(caseInfoDTO, ReportTypeEnum.API, JsonUtil.obj2Json(result));
            return result;
        }finally {
            //释放相关资源
            ApiRelationContext.remove();
        }


    }

    private ApiCaseResultDTO doExecute(ApiCaseResultDTO result, List<ApiCaseStepDO> setpList) {

        if(result==null){
            result=new ApiCaseResultDTO();
            result.setList(new ArrayList<>(setpList.size()));
        }
        if(setpList==null || setpList.isEmpty()){
            return result;
        }

        //用例步骤结果初始化
        ApiCaseStepDO step = setpList.get(0);
        ApiCaseResultItemDTO resultItem = new ApiCaseResultItemDTO();
        resultItem.setApiCaseStep(SpringBeanUtil.copyProperties(step, ApiCaseStepDTO.class));
        resultItem.setExecuteState(true);
        resultItem.setAssertionState(true);

        result.getList().add(resultItem);
        List<ApiCaseResultItemDTO> list = result.getList();
        //将list元素apiCaseStep中的num排序
        Collections.sort(list, (o1, o2) -> o1.getApiCaseStep().getNum().compareTo(o2.getApiCaseStep().getNum()));


        EnvironmentDO environmentDO = environmentMapper.selectById(step.getEnvironmentId());
        String baseUrl = getBaseUrl(environmentDO);
        ApiRequest request = new ApiRequest(baseUrl, step.getPath(), step.getAssertion(), step.getRelation(), step.getQuery(), step.getHeader(), step.getBody(), step.getBodyType());
        RequestSpecification given = request.createRequest();

        try{
            long startTime = System.currentTimeMillis();
            //发起请求
            Response response = given.request(step.getMethod())
                    .thenReturn();

            long endTime = System.currentTimeMillis();

            resultItem.setExpendTime(endTime-startTime);
            resultItem.setRequestHeader(JsonUtil.obj2Json(request.getHeaderList()));
            resultItem.setRequestQuery(JsonUtil.obj2Json(request.getQueryList()));

            if(StringUtils.isNotBlank(request.getRequestBody().getBody())){
                if(ApiBodyTypeEnum.JSON.name().equals(step.getBodyType())){
                    resultItem.setRequestBody(request.getRequestBody().getBody());
                }else {
                    resultItem.setRequestBody(JsonUtil.obj2Json(request.getRequestBody()));
                }
            }

            //处理响应结果
            resultItem.setResponseBody(response.getBody().asString());
            resultItem.setResponseHeader(JsonUtil.obj2Json(response.getHeaders()));

            //关联relation取值
            ApiRelationSaveUtil.dispatcher(request,response);

            //断言处理
            ApiAssertionUtil.dispatcher(request,response);



        }catch (BizException e){
            e.printStackTrace();

            //断言失败
            resultItem.setAssertionState(false);
        }catch (Exception e){
            e.printStackTrace();
            resultItem.setExecuteState(false);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            resultItem.setExceptionMsg(sw.toString());
        }
        //下轮递归
        setpList.remove(0);
        return doExecute(result,setpList);
    }

    private static String getBaseUrl(EnvironmentDO environmentDO){
        String protocol = environmentDO.getProtocol();
        String domain = environmentDO.getDomain();
        Integer port = environmentDO.getPort();
        return protocol+"://"+domain+":"+port;
    }

}
