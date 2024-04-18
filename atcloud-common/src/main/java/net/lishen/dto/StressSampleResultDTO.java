package net.lishen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.dto
 * @Project：gpcloud-meter
 * @name：StressSampleResultDTO
 * @Date：2024-01-22 10:08
 * @Filename：StressSampleResultDTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StressSampleResultDTO {


    /**
     * 所属报告ID 结果ID
     */
    private Long reportId;

    /**
     * 请求时间戳
     */
    private Long sampleTime;

    /**
     *采样器标签，请求名称
    */
    private String samplerLabel;
    /***
     * 采样次数
     */
    private Long samplerCount;
    /**
     * 平均响应时间
     */
    private Double meanTime;
    /**
     * 最小响应时间
     */
    private Integer minTime;
    /**
     * 最大响应时间
     */
    private Integer maxTime;
    /**
     * 错误百分比
     */
    private Double errorPercentage;
    /**
     * 错误请求数
     */
    private Long errorCount;
    /**
     * 每秒请求速率
     */
    private Double requestRate;
    /**
     * 每秒接收KB
     */
    private Double receiveKBPerSecond;
    /**
     * 每秒发送KB
     */
    private Double sentKBPerSecond;
    /**
     * 线程数
     */
    private Integer threadCount;
    /**
     * 请求协议 主机、端口、参数
     */
    private String requestLocation;

    /**
     * 请求头
     */
    private String requestHeader;
    /**
     * 请求体
     */
    private String requestBody;
    /**
     * 响应码
     */
    private String responseCode;
    /**
     * 响应头
     */
    private String responseHeader;
    /**
     * 响应数据
     */
    private String responseData;
    /**
     * 断言信息
     */
    private String assertInfo;

}
