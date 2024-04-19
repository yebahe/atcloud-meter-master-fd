package net.lishen.enums;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.enums
 * @Project：atcloud-meter
 * @name：ApiRelateFieldFromEnum
 * @Date：2024-03-06 16:24
 * @Filename：ApiRelateFieldFromEnum
 */
/**
 * API关联字段来源
 */
public enum ApiRelateFieldFromEnum {
    /**
     * 请求头
     */
    REQUEST_HEADER,
    /**
     * 请求体
     */
    REQUEST_BODY,
    /**
     * 请求查询参数
     */
    REQUEST_QUERY,
    /**
     * 响应头
     */
    RESPONSE_HEADER,
    /**
     * 响应体
     */
    RESPONSE_DATA;

}