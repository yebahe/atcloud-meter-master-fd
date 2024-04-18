package net.lishen.controller.api.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api.req
 * @Project：gpcloud-meter
 * @name：ApiCaseStepReq
 * @Date：2024-03-06 20:42
 * @Filename：ApiCaseStepReq
 */
@Data
public class ApiCaseStepUpdateReq {

    private Long id;

    private Long projectId;


    private Long environmentId;


    private Long caseId;


    private Long num;

    private String name;

    private String description;


    private String assertion;


    private String relation;

    private String path;


    private String method;


    private String query;


    private String header;


    private String body;


    private String bodyType;
}
