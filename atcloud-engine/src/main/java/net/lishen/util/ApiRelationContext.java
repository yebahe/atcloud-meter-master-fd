package net.lishen.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：ApiRelationContext
 * @Date：2024-03-08 19:21
 * @Filename：ApiRelationContext
 */
public class ApiRelationContext {
    private static final  ThreadLocal<Map<String,String>> THREAD_LOCAL = new ThreadLocal<>();


    public static Map<String,String> get(){
        return THREAD_LOCAL.get();
    }

    /**
     * 指定key获取
     */
    public static String get(String key){
        Map<String, String> map = THREAD_LOCAL.get();

        if(map==null){
            return null;
        }
        return map.get(key);
    }


    /**
     * set
     */
    public static void set(String key,String value){
        if(get()==null){
            THREAD_LOCAL.set(new HashMap<>());
        }
        THREAD_LOCAL.get().put(key,value);
    }

    /**
     * 除常规内存变量清空，还需要把相关资源文件进行删除
     */
    public static void remove(){
        String filePaths = get("filePaths");
        if(filePaths!=null){
            String[] split = filePaths.split(",");
            for(String pathStr:split){
                Path filePath = Paths.get(pathStr);
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        THREAD_LOCAL.remove();
    }
}
