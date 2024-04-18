package net.lishen.controller.api.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api.req
 * @Project：gpcloud-meter
 * @name：ApiSaveReq
 * @Date：2024-03-06 18:16
 * @Filename：ApiSaveReq
 */
@Data
public class ApiUpdateReq {

    private Long id;

    private Long projectId;


    private Long moduleId;


    private Long environmentId;


    private String name;


    private String description;


    private String level;


    private String path;


    private String method;


    private String query;


    private String header;


    private String body;


    private String bodyType;
}
