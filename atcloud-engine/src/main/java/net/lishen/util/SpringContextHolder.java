package net.lishen.util;

import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：SpringContextHolder
 * @Date：2024-03-08 15:16
 * @Filename：SpringContextHolder
 */
public class SpringContextHolder {
    // 应用上下文容器，用于存储和管理Bean
    private static ApplicationContext applicationContext;

    /**
     * 设置应用上下文容器
     * @param applicationContext 应用上下文实例
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 通过名称获取Bean实例
     * @param name Bean的名称
     * @return 返回对应名称的Bean实例
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取所有匹配给定类型的Bean实例
     * @param clazz 要查询的Bean的类型
     * @return 返回一个包含所有匹配Bean的Map，键为Bean名称，值为Bean实例
     */
    public static <T> Map<String,T> getBeans(Class<T> clazz){
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 通过类型获取唯一Bean实例
     * @param clazz 要查询的Bean的类型
     * @return 返回匹配的唯一Bean实例
     */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }


}
