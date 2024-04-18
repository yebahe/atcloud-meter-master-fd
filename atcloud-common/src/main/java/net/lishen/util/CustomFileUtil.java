package net.lishen.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.util
 * @Project：gpcloud-meter
 * @name：FileUtil
 * @Date：2024-01-20 22:17
 * @Filename：FileUtil
 */
public class CustomFileUtil {
    /**
     * 生成文件名称
     * @param filename
     * @return
     */
    public static String getFileName(String  filename) {
        //hutool的UUID
        return System.currentTimeMillis()+"-"+ UUID.fastUUID()+"-" +filename;
    }


    /**
     * 通过url读取远程文本内容
     */
    public static String readRemoteFile(String urlStr) {
        try{
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                //换行符
                sb.append(System.lineSeparator());
            }
            reader.close();
            return sb.toString();

        }catch (Exception e){
            throw new RuntimeException("读取远程文本文件异常");
        }
    }

    public static void mkdir(String dir) {
        FileUtil.mkdir(dir);
    }

    /**
     * 获取文件后缀
     * @param remoteFilePath
     * @return
     */
    public static String getSuffix(String remoteFilePath) {
        //后缀
        if(remoteFilePath.contains(".")){
            return remoteFilePath.substring(remoteFilePath.lastIndexOf("."));
        }
        return "";
    }
}
