package net.lishen.dto;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto
 * @Project：gpcloud-meter
 * @name：ApiCaseResultItemDTO
 * @Date：2024-03-08 14:54
 * @Filename：ApiCaseResultItemDTO
 */
@Data
public class ApiCaseResultItemDTO  {

    private Long reportId;

    private Boolean executeState;

    private Boolean assertionState;

    private String exceptionMsg;

    private Long expendTime;

    private String requestHeader;

    private String requestBody;

    private String requestQuery;

    private String responseHeader;

    private String responseBody;

    private ApiCaseStepDTO apiCaseStep;
}