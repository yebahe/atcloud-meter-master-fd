package net.lishen.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class SpringBeanUtil {
    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            T t = target.getConstructor().newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> copyProperties(List<?> sourceList, Class<T> target) {
        ArrayList<T> targetList = new ArrayList<>();
        sourceList.forEach(item -> targetList.add(copyProperties(item, target)));
        return targetList;
    }
}