package net.lishen.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.req
 * @Project：atcloud-meter
 * @name：ReportSaveReq
 * @Date：2024-01-19 19:48
 * @Filename：ReportSaveReq
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSaveReq {


    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 用例id
     */
    private Long caseId;

    /**
     * ui  api stress
     */
    private String type;

    /**
     * 报告名称
     */
    private String name;

    /**
     * 执行状态
     */
    private String executeState;

    /**
     * 概要
     */
    private String summary;


    private Long startTime;




}
