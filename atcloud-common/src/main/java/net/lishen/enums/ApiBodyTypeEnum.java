package net.lishen.enums;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.enums
 * @Project：atcloud-meter
 * @name：ApiBodyTypeEnum
 * @Date：2024-03-06 16:27
 * @Filename：ApiBodyTypeEnum
 */
public enum ApiBodyTypeEnum {

    /**
     * 表单提交
     */
    FORM_DATA,
    /**
     * 表单提交
     */
    X_WWW_FORM_URLENCODED,
    /**
     * JSON提交
     */
    JSON,
    /**
     * TEXT提交
     */
    TEXT,
    /**
     * 二进制提交，比如file
     */
    BINARY;
}