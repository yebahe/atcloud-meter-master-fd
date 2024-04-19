package net.lishen.controller.stress.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.stress.req
 * @Project：atcloud-meter
 * @name：StressCaseModuleSaveReq
 * @Date：2024-01-19 14:22
 * @Filename：StressCaseModuleSaveReq
 */
@Data
public class StressCaseModuleUpdateReq {
    private Long id;
    /**
     * 所属项目ID
     */
    private Long projectId;

    /**
     * 接口模块名称
     */
    private String name;

}
