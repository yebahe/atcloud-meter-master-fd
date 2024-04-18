package net.lishen.service.api.core;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import net.lishen.dto.KeyValueDTO;
import net.lishen.dto.api.ApiJsonAssertionDTO;
import net.lishen.dto.api.ApiJsonRelationDTO;
import net.lishen.dto.api.RequestBodyDTO;
import net.lishen.enums.ApiBodyTypeEnum;
import net.lishen.util.ApiWireUtil;
import net.lishen.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.api.core
 * @Project：gpcloud-meter
 * @name：ApiRequest
 * @Date：2024-03-08 18:01
 * @Filename：ApiRequest
 */
@Data
public class ApiRequest {

    private String base;

    private String path;

    private String assertion;

    private String relation;

    private String query;

    private String header;


    private RequestBodyDTO requestBody;


    private List<ApiJsonAssertionDTO> assertionList;

    private List<ApiJsonRelationDTO> relationList;

    private List<KeyValueDTO> queryList;

    private List<KeyValueDTO> headerList;

    private List<KeyValueDTO> bodyList;

    private RequestSpecification request = RestAssured.given();


    public ApiRequest(String base, String path, String assertion, String relation, String query, String header, String body, String bodyType) {
        this.base = base;
        this.path = path;
        this.assertion = assertion;
        this.relation = relation;
        this.query = query;
        this.header = header;
        this.requestBody = new RequestBodyDTO(body,bodyType);

        this.assertionList= StringUtils.isBlank(assertion)?null : JsonUtil.json2List(assertion, ApiJsonAssertionDTO.class);
        this.relationList= StringUtils.isBlank(relation)?null : JsonUtil.json2List(relation, ApiJsonRelationDTO.class);
        this.queryList= StringUtils.isBlank(query)?null : JsonUtil.json2List(query, KeyValueDTO.class);
        this.headerList= StringUtils.isBlank(header)?null : JsonUtil.json2List(header, KeyValueDTO.class);

        if(!ApiBodyTypeEnum.JSON.name().equals(bodyType)){
            this.bodyList= StringUtils.isBlank(body)?null : JsonUtil.json2List(body, KeyValueDTO.class);
        }

    }

    /**
     * 创建请求对象
     * @return
     */
    public RequestSpecification createRequest(){
        // 装配基础路径
        ApiWireUtil.wireBase(request, base, path);

        // 装配请求头
        ApiWireUtil.wireHeader(request, headerList);

        // 装配请求参数
        ApiWireUtil.wireQuery(request, queryList);

        // 装配请求体
        ApiWireUtil.wireBody(request,requestBody, bodyList);

        return request;
    }

}
