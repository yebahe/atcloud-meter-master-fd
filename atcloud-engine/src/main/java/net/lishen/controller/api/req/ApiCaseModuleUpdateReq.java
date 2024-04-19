package net.lishen.controller.api.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api.req
 * @Project：atcloud-meter
 * @name：ApiCaseModuleSaveReq
 * @Date：2024-03-06 19:04
 * @Filename：ApiCaseModuleSaveReq
 */
@Data
public class ApiCaseModuleUpdateReq {

    private Long id;

    private Long projectId;


    private String name;

}
