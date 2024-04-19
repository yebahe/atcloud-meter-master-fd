package net.lishen.controller.stress.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.stress.req
 * @Project：atcloud-meter
 * @name：StressCaseSaveReq
 * @Date：2024-01-18 12:00
 * @Filename：StressCaseSaveReq
 */
@Data
public class StressCaseSaveReq {


    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 所属接口模块ID
     */

    private Long moduleId;
    /**
     * 环境id
     */
    private Long environmentId;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 响应断言
     */
    private String assertion;

    /**
     * 可变参数
     */
    private String relation;

    /**
     * 压测类型 [simple jmx]
     */
    private String stressSourceType;

    /**
     * 压测参数
     */
    private String threadGroupConfig;

    /**
     * jmx文件地址
     */
    private String jmxUrl;

    /**
     * 接口路径
     */
    private String path;

    /**
     * 请求方法 [GET POST PUT PATCH DELETE HEAD OPTIONS]
     */
    private String method;

    /**
     * 查询参数
     */
    private String query;

    /**
     * 请求头
     */
    private String header;

    /**
     * 请求体
     */
    private String body;

    /**
     * 请求体格式 [raw form-data json]
     */
    private String bodyType;


}
