package net.lishen.controller.common;

import jakarta.annotation.Resource;
import net.lishen.controller.common.req.EnvironmentDelReq;
import net.lishen.controller.common.req.EnvironmentSaveReq;
import net.lishen.controller.common.req.EnvironmentUpdateReq;
import net.lishen.service.common.EnvironmentService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Package：net.joseph.controller.common
 * @Project：gpcloud-meter
 * @name：EnvironmentController
 * @Date：2024-01-07 20:07
 * @Filename：EnvironmentController ：环境管理基础模块开发
 *
 */
@RestController
@RequestMapping("/api/v1/env")
public class EnvironmentController {

    @Resource
    private EnvironmentService environmentService;
    @GetMapping("/list")
    public JsonData list(@RequestParam("projectId") long projectId){
        return JsonData.buildSuccess(environmentService.list(projectId));
    }
    @PostMapping("/save")
    public JsonData save(@RequestBody EnvironmentSaveReq req){
        return JsonData.buildSuccess(environmentService.save(req));
    }


    @PostMapping("/update")
    public JsonData update(@RequestBody EnvironmentUpdateReq req){
        return JsonData.buildSuccess(environmentService.update(req));
    }
    @PostMapping("/del")
    public JsonData del(@RequestBody EnvironmentDelReq req){
        return JsonData.buildSuccess(environmentService.del(req));
    }

}
