package net.lishen.service.common.impl;

import cn.hutool.core.util.IdUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lishen.config.MinIOConfig;
import net.lishen.service.common.FileService;
import net.lishen.util.CustomFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.common.impl
 * @Project：atcloud-meter
 * @name：FileServiceImpl
 * @Date：2024-01-20 22:13
 * @Filename：FileServiceImpl
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private MinIOConfig minIOConfig;


    @Resource
    private MinioClient minioClient;

    @Autowired
    @Lazy
    private FileService fileService;

    @Override
    public String upload(MultipartFile file) {
        //获取文件名
        String fileName = CustomFileUtil.getFileName(file.getOriginalFilename());
        //异步上传
        //代理类对行，为了使异步生效
        fileService.uploadFile(file, fileName);
        String url = minIOConfig.getEndpoint()+"/"+minIOConfig.getBucketName()+"/"+fileName;

        return url;
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void uploadFile(MultipartFile file, String fileName) {

        if(file==null||file.getSize()==0){
            throw new RuntimeException("文件为空");
        }else {
            try {
                InputStream inputStream = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                                .bucket(minIOConfig.getBucketName())
                                .object(fileName)

                                .stream(inputStream,file.getSize(),-1)
                                .contentType(file.getContentType())

                                .build());


            } catch (Exception e) {
                throw new RuntimeException("file upload fail!!!!!");
            }

        }

    }

    /**
     * 文件上传后不能公开读写 ，文件临时访问URL接口开发
     * @param romeFilaPath
     * @return
     */
    @Override
    public String getTempAccessFileUrl(String romeFilaPath) {

        String filename = romeFilaPath.substring(romeFilaPath.lastIndexOf("/") + 1);
        //预签名
        GetPresignedObjectUrlArgs objectUrlArgs = GetPresignedObjectUrlArgs.builder().bucket(minIOConfig.getBucketName())
                .object(filename)
                .expiry(1, TimeUnit.MINUTES)
                .method(Method.GET)
                .build();
        try {
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(objectUrlArgs);
            return presignedObjectUrl;
        } catch (Exception e) {
            log.error("获得临时文件访问连接失败getPresignedObjectUrl fail!!!!!!");
            throw new RuntimeException("获得临时文件访问连接失败getPresignedObjectUrl fail");
        }

    }

    @Override
    public String copyRemoteFileToLocalTempFile(String remoteFilePath) {
        String localTempFilePath = System.getProperty("user.dir") + File.separator + "static"
                + File.separator + IdUtil.simpleUUID() + CustomFileUtil.getSuffix(remoteFilePath);
        //创建
        CustomFileUtil.mkdir(localTempFilePath);
        try{
            String tempAccessFileUrl = getTempAccessFileUrl(remoteFilePath);

            URL url = new URL(tempAccessFileUrl);
            InputStream inputStream = url.openStream();

            Path localFile = Path.of(localTempFilePath);
            Files.copy(inputStream, localFile, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            return localFile.toFile().getPath();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("读取远程文件失败");
        }
    }
}
