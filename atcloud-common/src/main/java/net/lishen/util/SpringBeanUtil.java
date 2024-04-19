package net.lishen.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：SpringBeanUtil
 * @Date：2024-01-07 15:38
 * @Filename：SpringBeanUtil
 */
public class SpringBeanUtil {
    /**
     * Bean类型转换
     *
     * @param <T> 泛型类型
     * @param source 源对象
     * @param target 目标类
     * @return 复制后的目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            T targetObj = target.newInstance();
            BeanUtils.copyProperties(source, targetObj);
            return targetObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  List Bean类型转换
     * @param sourceList 源集合
     * @param target 目标类
     * @param <T> 目标类类型
     * @return 复制后的目标类集合
     */
    public static <T> List<T> copyProperties(List<?> sourceList, Class<T> target){
        ArrayList<T> targetList = new ArrayList<>();
        sourceList.forEach(source->{
            T t = copyProperties(source,target);
            targetList.add(t);
        });
        return targetList;
    }


}
