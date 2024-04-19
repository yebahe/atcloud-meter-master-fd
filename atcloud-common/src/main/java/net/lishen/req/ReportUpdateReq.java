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
 * @name：ReportUpdateReq
 * @Date：2024-03-02 16:09
 * @Filename：ReportUpdateReq
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateReq {

    /**
     * 测试报告id
     */
    private Long id;

    /**
     * 执行状态
     */
    private String executeState;
    /**
     * 结束时间
     */
    private Long endTime;

}
