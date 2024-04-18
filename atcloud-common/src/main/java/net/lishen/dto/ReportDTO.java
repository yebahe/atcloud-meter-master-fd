package net.lishen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto
 * @Project：gpcloud-meter
 * @name：ReportDTO
 * @Date：2024-01-19 20:00
 * @Filename：ReportDTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Long id;


    private Long projectId;


    private Long caseId;



    private String type;

    private String name;


    private String executeState;


    private String summary;


    private Long startTime;


    private Long endTime;

    /**
     * 消耗时间
     */
    private Long expandTime;

    /**
     * 步骤数量
     */
    private Long quantity;

    /**
     * 通过数量
     */
    private Long passQuantity;

    /**
     * 失败数量
     */
    private Long failQuantity;


    private Date gmtCreate;


    private Date gmtModified;
}
