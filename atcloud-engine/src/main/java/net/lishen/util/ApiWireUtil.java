package net.lishen.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import net.lishen.dto.KeyValueDTO;
import net.lishen.dto.api.RequestBodyDTO;
import net.lishen.enums.ApiBodyTypeEnum;
import net.lishen.service.common.FileService;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：gpcloud-meter
 * @name：ApiWireUtil
 * @Date：2024-03-08 18:42
 * @Filename：ApiWireUtil
 */
@Slf4j
public class ApiWireUtil {

    /**
     * 基础路径
     * @param request
     * @param base
     * @param path
     */
    public static void wireBase(RequestSpecification request, String base, String path) {
        request.baseUri(base+path);
    }

    public static void  wireHeader(RequestSpecification request, List<KeyValueDTO> headerList) {
        if(headerList==null || headerList.isEmpty()){
            return;
        }

        HashMap<String, Object> map = new HashMap<>(headerList.size());
        for (KeyValueDTO header : headerList) {
            String value = ApiRelationGetUtil.getParameter(header.getValue());
            header.setValue(value);
            map.put(header.getKey(), value);
        }
        request.headers(map);
    }

    public static void wireQuery(RequestSpecification request, List<KeyValueDTO> queryList) {

        if(queryList==null || queryList.isEmpty()){
            return;
        }
        HashMap<String, Object> map = new HashMap<>(queryList.size());
        for (KeyValueDTO query : queryList) {
            String value = ApiRelationGetUtil.getParameter(query.getValue());
            query.setValue(value);
            map.put(query.getKey(), value);
        }
        request.queryParams(map);

    }

    public static void wireBody(RequestSpecification request, RequestBodyDTO requestBody, List<KeyValueDTO> bodyList) {
        if(StringUtils.isBlank(requestBody.getBody())){
            return;
        }
        if(ApiBodyTypeEnum.JSON.name().equals(requestBody.getBodyType())){
            //解析Json请求  递归
            String modifiedJson = traverseAndModify(requestBody.getBody());
            requestBody.setBody(modifiedJson);
            request.body(modifiedJson);
        }else{
            //其他类型
            ApiBodyTypeEnum bodyTypeEnum = ApiBodyTypeEnum.valueOf(requestBody.getBodyType());
            for(KeyValueDTO item : bodyList){
                switch (bodyTypeEnum){
                    case FORM_DATA,X_WWW_FORM_URLENCODED -> {
                        String parameter = ApiRelationGetUtil.getParameter(item.getValue());
                        item.setValue(parameter);
                        request.formParam(item.getKey(),item.getValue());
                    }

                    case BINARY -> {
                        FileService fileService = SpringContextHolder.getBean(FileService.class);
                        String localTempFile = fileService.copyRemoteFileToLocalTempFile(item.getValue());

                        File file = new File(localTempFile);
                        request.multiPart(file);

                        //资源清理放置路径
                        String filePaths = ApiRelationContext.get("filePaths");
                        if(filePaths!=null){
                            filePaths = filePaths+","+file.getAbsolutePath();
                            ApiRelationContext.set("filePaths",filePaths);
                        }else {
                            ApiRelationContext.set("filePaths",filePaths);
                        }
                    }
                    default -> {
                        try {
                            throw new IllegalAccessException("不支持的请求类型");
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

    }

    private static String traverseAndModify(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //递归
        traverseJsonObject(jsonObject);
        return JSON.toJSONString(jsonObject);
    }

    private static void traverseJsonObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    traverseJsonObject((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Object subValue = jsonArray.get(i);
                        if (subValue instanceof String subStringValue) {
                            String parameterizedValue = ApiRelationGetUtil.getParameter(subStringValue);
                            jsonArray.remove(subStringValue);
                            jsonArray.add(parameterizedValue);
                            // 仅在实际需要日志记录时，使用占位符记录
                            // log.info("Parameterized string: {} -> {}", subStringValue, parameterizedValue);
                        } else if (subValue instanceof JSONObject subJsonObject) {
                            traverseJsonObject(subJsonObject);
                        } else {
                            log.error("Unsupported type encountered: {}", subValue.getClass().getName());
                        }
                    }
                } else if (value instanceof String stringValue) {
                    String parameterizedValue = ApiRelationGetUtil.getParameter(stringValue);
                    jsonObject.put(key, parameterizedValue);
                    // 仅在实际需要日志记录时，使用占位符记录
                    // log.info("Parameterized string: {} -> {}", stringValue, parameterizedValue);
                } else {
                    log.error("Unsupported type encountered: {}", value.getClass().getName());
                }
            }
        }
    }
}
