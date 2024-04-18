package net.lishen.dto.stress;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.stress
 * @Project：gpcloud-meter
 * @name：StressAssertionDTO
 * @Date：2024-03-03 15:28
 * @Filename：StressAssertionDTO
 */

@Data
public class StressAssertionDTO {

    /**
     * 断言名称
     */
    private String name;

    /**
     * 断言规则，"contain|equal"
     */
    private String action;

    /**
     * 断言字段类型， "responseCode|responseData|responseHeader"
     */
    private String from;

    /**
     * 断言目标值
     */
    private String value;
}
