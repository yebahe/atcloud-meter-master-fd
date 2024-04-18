package net.lishen.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：gpcloud-meter
 * @name：JsonUtil
 * @Date：2024-01-27 18:47
 * @Filename：JsonUtil
 */
@Slf4j
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static{
        //设置可用单引号
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //序列对象的所有属性
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        //反序列化的时候如果多了其他属性，不抛出异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //下划线与驼峰互转
//        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);


        //如果是空对象，不抛出异常
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //取消实际的转换格式，默认是时间戳，可以取消，同时需要设置要表现的时间格式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    }

    public static ObjectMapper get(){
        return MAPPER;
    }

    /**
     * 将对象转换为json字符串
     * @param obj
     * @return
     */
     public static String obj2Json(Object obj){
         try {
             return MAPPER.writeValueAsString(obj);
         } catch (JsonProcessingException e) {
             log.error("json转换失败:{}",e);
         }
         return null;
     }


    /**
     * json字符串转换为对象
     */
    public static <T> T json2Obj(String json, Class<T> beanType){
         try {
             return MAPPER.readValue(json, beanType);
         } catch (JsonProcessingException e) {
             log.error("json转换失败:{}",e);
         }
         return null;
    }

    /**
     * json数据转换为pojo对象list
     */
    public static <T> List<T> json2List(String json, Class<T> beanType){
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, beanType));
        } catch (JsonProcessingException e) {
            log.error("json转换失败:{}",e);
        }
        return new ArrayList<>(0);
    }

}
