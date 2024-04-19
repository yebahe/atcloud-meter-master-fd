package net.lishen.controller.api.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api.req
 * @Project：atcloud-meter
 * @name：ApiModuleSaveReq
 * @Date：2024-03-06 17:49
 * @Filename：ApiModuleSaveReq
 */
@Data
public class ApiModuleUpdateReq {

    private Long id;

    private Long projectId;

    private String name;

}
