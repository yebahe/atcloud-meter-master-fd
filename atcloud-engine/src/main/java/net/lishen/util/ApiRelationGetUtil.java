package net.lishen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：ApiRelationGetUtil
 * @Date：2024-03-08 19:55
 * @Filename：ApiRelationGetUtil
 */
public class ApiRelationGetUtil {
    /**
     * 获取参数，或者关联参数，
     * 正则匹配是否为关联参数表达式，如果不是则返回原始参数
     * @param string
     * @return
     */
    public static String  getParameter(String string){
        String newString = string;
        Pattern pattern = Pattern.compile(ApiRegexpUtil.REGEXP);
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()){
            String parameter = matcher.group(1);
            
            //去容器取关联参数
            String fetched = ApiRelationContext.get(parameter);

            if(fetched==null){
                //关联参数不存在
                throw new RuntimeException("关联参数不存在");
            }

            newString = string.replaceAll(ApiRegexpUtil.byName(parameter), fetched);
        }
        return newString;
    }

}
