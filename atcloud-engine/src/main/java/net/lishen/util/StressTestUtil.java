package net.lishen.util;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.util.JMeterUtils;

import java.io.File;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：atcloud-meter
 * @name：StressTestUtil
 * @Date：2024-01-19 15:21
 * @Filename：StressTestUtil
 */
public class StressTestUtil {
    /**
     * 获取jmeter的home路径，临时写法，后续部署上线部署
     * @return
     */
    public static String getJmeterHome(){
        try{
            //这里获取不到，先注释掉，手写代码
//            String path = StressTestUtil.class.getResource("jmeter").getPath();
            String path = "C:\\Users\\联想\\Desktop\\my_project\\atcloud-meter\\atcloud-engine\\src\\main\\resources\\jmeter";
            return path;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取bin目录
     */
    public static String getJmeterHomeBin(){
        return getJmeterHome()+ File.separator+"bin";
    }

    /**
     * 初始化jmeter配置文件
     */
    public static void initJmeterProperties(){
        String jmeterHome = getJmeterHome();
        String jmeterHomeBin = getJmeterHomeBin();
        //加载jmeter配置文件
        JMeterUtils.loadJMeterProperties(jmeterHomeBin+File.separator+"jmeter.properties");

        //设置jmeter安装目录
        JMeterUtils.setJMeterHome(jmeterHome);
        //覆盖配置文件，避免中文响应乱码
        JMeterUtils.setProperty("sampleresult.default.encoding","UTF-8");
        //初始化环境
        JMeterUtils.initLocale();
    }

    /**
     * 获取StandardJmeter标准引擎
     */
    public static StandardJMeterEngine getStandardJmeterEngine(){
        //初始化配置
        initJmeterProperties();
        StandardJMeterEngine standardJMeterEngine = new StandardJMeterEngine();
        return standardJMeterEngine;
    }
}
