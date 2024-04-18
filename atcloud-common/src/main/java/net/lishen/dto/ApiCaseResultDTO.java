package net.lishen.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto
 * @Project：gpcloud-meter
 * @name：ApiCaseResultDTO
 * @Date：2024-03-07 21:12
 * @Filename：ApiCaseResultDTO
 */
@Data
public class ApiCaseResultDTO {

    private Long reportId;

    private Boolean executeState;

    private Long startTime;

    private Long endTime;

    private Long expendTime;

    private Integer quantity;

    private Integer passQuantity;

    private Integer failQuantity;

    private List<ApiCaseResultItemDTO> list;

}
