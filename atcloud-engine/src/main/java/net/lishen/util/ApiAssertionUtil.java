package net.lishen.util;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.lishen.dto.api.ApiJsonAssertionDTO;
import net.lishen.enums.ApiAssertActionEnum;
import net.lishen.enums.ApiAssertFieldFromEnum;
import net.lishen.enums.ApiAssertTypeEnum;
import net.lishen.enums.BizCodeEnum;
import net.lishen.exeception.BizException;
import net.lishen.service.api.core.ApiRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：gpcloud-meter
 * @name：ApiAssertionUtil
 * @Date：2024-03-09 21:09
 * @Filename：ApiAssertionUtil
 */
@Slf4j
public class ApiAssertionUtil {

    public static boolean assertionDispatcher(String action ,String value,String expectValue) {

        ApiAssertActionEnum actionEnum = ApiAssertActionEnum.valueOf(action);
        boolean result = switch (actionEnum){
            case CONTAIN -> value.contains(expectValue);
            case NOT_CONTAIN -> !value.contains(expectValue);
            case EQUAL -> value.equals(expectValue);
            case LESS_THEN -> Long.parseLong(value) < Long.parseLong(expectValue);
            case GREAT_THEN -> Long.parseLong(value) > Long.parseLong(expectValue);
            case NOT_EQUAL -> !value.equals(expectValue);
            default -> throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_ASSERTION);
        };

        return result;

    }

    public static void dispatcher(ApiRequest request, Response response) {
        if(request.getAssertion()==null){
            return;
        }

        for(ApiJsonAssertionDTO assertion : request.getAssertionList()){
            boolean state = true;
            String express = assertion.getExpress();
            String expectValue = assertion.getValue();
            ApiAssertFieldFromEnum fieldFromEnum = ApiAssertFieldFromEnum.valueOf(assertion.getFrom());
            try{
                if(ApiAssertTypeEnum.JSONPATH.name().equals(assertion.getType())){
                    state = switch (fieldFromEnum){
                        case RESPONSE_CODE -> {
                            String stringIfy = String.valueOf(response.getStatusCode());
                            Object responseCode = JsonPath.read(stringIfy, express);

                            yield assertionDispatcher(assertion.getAction(), String.valueOf(responseCode),expectValue);
                        }
                        case RESPONSE_HEADER -> {
                            String stringify = JsonUtil.obj2Json(response.getHeaders());
                            Object responseHeader = JsonPath.read(stringify, express);
                            yield assertionDispatcher(assertion.getAction(), String.valueOf(responseHeader),expectValue);
                        }
                        case RESPONSE_DATA -> {
                            String stringify = response.getBody().asString();
                            Object responseData = JsonPath.read(stringify, express);
                            yield assertionDispatcher(assertion.getAction(), String.valueOf(responseData),expectValue);
                        }
                        default -> {
                            log.error("不支持的断言来源:{}",fieldFromEnum);
                            throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
                        }
                    };
                } else if (ApiAssertTypeEnum.REGEXP.name().equals(assertion.getType())) {
                    Pattern pattern = Pattern.compile(express);
                    state = switch (fieldFromEnum){
                        case RESPONSE_CODE -> {
                            String stringIfy = String.valueOf(response.getStatusCode());
                            Matcher matcher = pattern.matcher(stringIfy);
                            yield matcher.find()&&assertionDispatcher(assertion.getAction(),matcher.group(),expectValue);
                        }
                        case RESPONSE_HEADER -> {
                            String stringify = JsonUtil.obj2Json(response.getHeaders());
                            Matcher matcher = pattern.matcher(stringify);

                            yield matcher.find()&&assertionDispatcher(assertion.getAction(), matcher.group(),expectValue);
                        }
                        case RESPONSE_DATA -> {
                            String stringify = response.getBody().asString();
                            Matcher matcher = pattern.matcher(stringify);
                            yield matcher.find()&&assertionDispatcher(assertion.getAction(), matcher.group(), expectValue);
                        }
                        default -> {
                            log.error("不支持的断言来源:{}",fieldFromEnum);
                            throw new BizException(BizCodeEnum.API_OPERATION_UNSUPPORTED_FROM);
                        }
                    };
                }
                if(!state){
                    throw new BizException(BizCodeEnum.API_ASSERTION_FAILED);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new BizException(BizCodeEnum.API_ASSERTION_FAILED);
            }

        }



    }
}
