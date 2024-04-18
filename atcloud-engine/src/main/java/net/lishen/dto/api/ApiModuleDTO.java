package net.lishen.dto.api;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.api
 * @Project：gpcloud-meter
 * @name：ApiModuleDTO
 * @Date：2024-03-06 16:47
 * @Filename：ApiModuleDTO
 */
@Data
public class ApiModuleDTO {
    private Long id;

    private Long projectId;

    private String name;


    private Date gmtCreate;


    private Date gmtModified;

    private List<ApiDTO> list;

}
