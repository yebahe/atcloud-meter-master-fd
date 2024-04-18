package net.lishen.dto.stress;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.common.stress
 * @Project：gpcloud-meter
 * @name：StressCaseModuleDTO
 * @Date：2024-01-18 22:31
 * @Filename：StressCaseModuleDTO
 */
@Data
public class StressCaseModuleDTO {
    private Long id;

    /**
     * 所属项目ID
     */
    private Long projectId;

    /**
     * 接口模块名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 用例列表
     */
    private List<StressCaseDTO> list;


}
