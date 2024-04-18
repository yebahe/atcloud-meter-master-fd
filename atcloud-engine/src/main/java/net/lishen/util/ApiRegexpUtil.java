package net.lishen.util;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：gpcloud-meter
 * @name：ApiRegexUtil
 * @Date：2024-03-08 19:40
 * @Filename：ApiRegexUtil
 */
public class ApiRegexpUtil {
    /**
     * 正则表达式 - 关联取值
     * 匹配以 {{ 开头，以 }} 结尾，且中间不包含 }} 的字符串
     */
    public static final String REGEXP = "\\{\\{([^}]+)}}";

    /**
     * 正则表达式具名 - 关联取值
     * @param name
     * @return
     */
    public static String byName(String name) {
        return "\\{\\{(" + name + ")}}";
    }
}