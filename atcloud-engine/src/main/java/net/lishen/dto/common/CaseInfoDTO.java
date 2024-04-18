package net.lishen.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.common
 * @Project：gpcloud-meter
 * @name：CaseInfoDTO
 * @Date：2024-01-21 22:45
 * @Filename：CaseInfoDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseInfoDTO {

    /**
     * 用例id 或者步骤id
     */
    private Long id;

    /**
     * 模块id
     */
    private Long moduleId;

    /**
     * 测试用例名称
     */
    private String name;

}
