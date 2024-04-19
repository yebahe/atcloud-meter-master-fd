package net.lishen.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto.api
 * @Project：atcloud-meter
 * @name：RequestBodyDTO
 * @Date：2024-03-09 14:35
 * @Filename：RequestBodyDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyDTO {
    private String body;

    private String bodyType;
}
