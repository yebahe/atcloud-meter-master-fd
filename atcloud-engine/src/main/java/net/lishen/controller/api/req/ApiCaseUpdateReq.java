package net.lishen.controller.api.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api.req
 * @Project：gpcloud-meter
 * @name：ApiCaseSaveReq
 * @Date：2024-03-06 20:24
 * @Filename：ApiCaseSaveReq
 */
@Data
public class ApiCaseUpdateReq {

    private Long id;

    private Long projectId;


    private Long moduleId;


    private String name;


    private String description;


    private String level;
}
