package net.lishen.dto.common;

import lombok.Data;

import java.util.Date;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.common
 * @Project：atcloud-meter
 * @name：ProjectDTO
 * @Date：2024-01-07 16:49
 * @Filename：ProjectDTO
 */
@Data
public class ProjectDTO {


    private Long id;

    /**
     * 管理员id
     */
    private Long projectAdmin;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
