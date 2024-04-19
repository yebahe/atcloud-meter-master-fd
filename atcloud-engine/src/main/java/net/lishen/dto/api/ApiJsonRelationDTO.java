package net.lishen.dto.api;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.api
 * @Project：atcloud-meter
 * @name：ApiJsonRelationDTO
 * @Date：2024-03-08 18:26
 * @Filename：ApiJsonRelationDTO
 */
@Data
public class ApiJsonRelationDTO {

    /**
     * 取值来源 header body
     */
    private String from;
    /**
     * 类型  jsonpath  regexp
     */
    private String type;

    /**
     * 表达式
     */
    private String express;
    /**
     * 取出来后变量名称
     */
    private String name;
}
