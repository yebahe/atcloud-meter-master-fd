package net.lishen.controller.common.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.common.req
 * @Project：atcloud-meter
 * @name：EnvironmentUpdateReq
 * @Date：2024-01-07 21:30
 * @Filename：EnvironmentUpdateReq
 */
@Data
public class EnvironmentUpdateReq {
    private Long id;
    /**
     * 所属项目ID
     */
    private Long projectId;

    /**
     * 环境名称
     */
    private String name;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 环境域名
     */
    private String domain;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 描述
     */
    private String description;
}
