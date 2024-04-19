package net.lishen.controller.common;

import jakarta.annotation.Resource;
import net.lishen.service.common.FileService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.common.req
 * @Project：atcloud-meter
 * @name：FileController
 * @Date：2024-01-20 22:11
 * @Filename：FileController ： 文件上传和下载
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileController {


    @Resource
    private FileService fileService;

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public JsonData upload(@RequestParam("file")MultipartFile file) {
        String path = fileService.upload(file);

        return JsonData.buildSuccess(path);
    }

}
