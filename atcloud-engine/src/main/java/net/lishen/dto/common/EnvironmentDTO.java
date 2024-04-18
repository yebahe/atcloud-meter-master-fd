package net.lishen.dto.common;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 环境表
 * </p>
 *
 * @author joseph,
 * @since 2024-01-06
 */
@Data
public class EnvironmentDTO {




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

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
