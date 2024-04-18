package net.lishen.controller.common.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.common.req
 * @Project：gpcloud-meter
 * @name：ProjectSaveReq
 * @Date：2024-01-07 17:28
 * @Filename：ProjectSaveReq
 */
@Data
public class ProjectUpdateReq {

    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
