package net.lishen.util;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.lishen.dto.api.ApiJsonRelationDTO;
import net.lishen.enums.ApiRelateFieldFromEnum;
import net.lishen.enums.ApiRelateTypeEnum;
import net.lishen.enums.BizCodeEnum;
import net.lishen.exeception.BizException;
import net.lishen.service.api.core.ApiRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：ApiRelationSaveUtil
 * @Date：2024-03-09 19:38
 * @Filename：ApiRelationSaveUtil
 */
public class ApiRelationSaveUtil {

    public static void dispatcher(ApiRequest request, Response response){
        if(request.getRelation()==null){
            return;
        }
        for(ApiJsonRelationDTO relationDTO:request.getRelationList()){
            ApiRelateTypeEnum typeEnum = ApiRelateTypeEnum.valueOf(relationDTO.getType());
            switch (typeEnum){
                case REGEXP -> {
                    regexp(request,response,relationDTO);
                }
                case JSONPATH -> {
                    jsonPath(request,response,relationDTO);
                }
                default->throw new RuntimeException("不支持的表达式解析类型");
            }
        }
    }

    private static void jsonPath(ApiRequest request, Response response, ApiJsonRelationDTO relationDTO) {
        try{
            ApiRelateFieldFromEnum fieldFromEnum = ApiRelateFieldFromEnum.valueOf(relationDTO.getFrom());
            String value = switch (fieldFromEnum) {
                case REQUEST_HEADER -> request.getHeader();
                case REQUEST_BODY -> request.getRequestBody().getBody();
                case REQUEST_QUERY -> request.getQuery();
                case RESPONSE_HEADER -> JsonUtil.obj2Json(response.getHeaders());
                case RESPONSE_DATA -> response.getBody().asString();
                default -> throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
            };
            Object json = JsonPath.read(value, relationDTO.getExpress());
            if(json!=null){
                ApiRelationContext.set(relationDTO.getName(),String.valueOf(json));
            }
        }catch (Exception e){

            throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
        }


    }

    private static void regexp(ApiRequest request, Response response, ApiJsonRelationDTO relationDTO) {

        try {
            Pattern pattern = Pattern.compile(relationDTO.getExpress());
            ApiRelateFieldFromEnum fieldFromEnum = ApiRelateFieldFromEnum.valueOf(relationDTO.getFrom());
            String value = switch (fieldFromEnum) {
                case REQUEST_HEADER -> request.getHeader();
                case REQUEST_BODY -> request.getRequestBody().getBody();
                case REQUEST_QUERY -> request.getQuery();
                case RESPONSE_HEADER -> JsonUtil.obj2Json(response.getHeaders());
                case RESPONSE_DATA -> response.getBody().asString();
                default -> throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
            };
            Matcher matcher = pattern.matcher(value);
            if(matcher.find()){
                ApiRelationContext.set(relationDTO.getName(),matcher.group(1));
            }
        }catch (Exception e){
            throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
        }

    }

}
