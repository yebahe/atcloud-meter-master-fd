package net.lishen.service.common;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.common
 * @Project：atcloud-meter
 * @name：FileService
 * @Date：2024-01-20 22:13
 * @Filename：FileService
 */
public interface FileService {

    String upload(MultipartFile file);

    /**
     * 这个不是controller用的接口，而是异步任务需要获取代理类
     * @param file
     * @param fileName
     */
    void uploadFile(MultipartFile file, String fileName);

    /**
     * 获得对象的临时访问url 有效期1minute
     * @param romeFilaPath
     * @return
     */
    String getTempAccessFileUrl(String romeFilaPath);

    /**
     * 读取远程文件到本地临时文件
     * @param remoteFilePath
     * @return
     */
    String copyRemoteFileToLocalTempFile(String remoteFilePath);
}
