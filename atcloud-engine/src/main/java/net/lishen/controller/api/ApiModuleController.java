package net.lishen.controller.api;

import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiModuleDeqReq;
import net.lishen.controller.api.req.ApiModuleSaveReq;
import net.lishen.controller.api.req.ApiModuleUpdateReq;
import net.lishen.service.api.ApiModuleService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**

 * @Package：net.joseph.controller.api
 * @Project：gpcloud-meter
 * @name：ApiModuleController
 * @Date：2024-03-06 16:41
 * @Filename：ApiModuleController
 */
@RestController
@RequestMapping("/api/v1/api_module")
public class ApiModuleController {

    @Resource
    private ApiModuleService apiModuleService;
    @GetMapping("/list")
    public JsonData list(Long projectId) {
        return JsonData.buildSuccess(apiModuleService.list(projectId));
    }

    @GetMapping("/find")
    public JsonData find(@RequestParam("projectId") Long projectId,@RequestParam("moduleId") Long moduleId){
        return JsonData.buildSuccess(apiModuleService.getById(projectId,moduleId));
    }


    /**
     * 根据projectId和moduleId删除用例模块
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ApiModuleDeqReq req){
        int rows = apiModuleService.delete(req.getId(),req.getProjectId());
        return JsonData.buildSuccess(rows);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public JsonData save(@RequestBody ApiModuleSaveReq req){
        return JsonData.buildSuccess(apiModuleService.save(req));
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ApiModuleUpdateReq req){
        return JsonData.buildSuccess(apiModuleService.update(req));
    }
}
