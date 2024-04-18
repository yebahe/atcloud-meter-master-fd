package net.lishen.controller.api;

import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiCaseModuleDelReq;
import net.lishen.controller.api.req.ApiCaseModuleSaveReq;
import net.lishen.controller.api.req.ApiCaseModuleUpdateReq;
import net.lishen.service.api.ApiCaseModuleService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**

 * @Package：net.joseph.controller.api
 * @Project：gpcloud-meter
 * @name：ApiCaseModuleController
 * @Date：2024-03-06 18:53
 * @Filename：ApiCaseModuleController
 */
@RestController
@RequestMapping("/api/v1/api_case_module")
public class ApiCaseModuleController {

    @Resource
    private ApiCaseModuleService apiCaseModuleService;

    /**
     * 列表接口
     */
    @GetMapping("/list")
    public JsonData list(@RequestParam("projectId") Long projectId) {
        return JsonData.buildSuccess(apiCaseModuleService.list(projectId));
    }

    /**
     * find
     */
    @GetMapping("/find")
    public JsonData find(@RequestParam("projectId") Long projectId, @RequestParam("moduleId") Long moduleId) {
        return JsonData.buildSuccess(apiCaseModuleService.getById(projectId, moduleId));
    }

    /**
     * save
     */
    @PostMapping("/save")
    public JsonData save(@RequestBody ApiCaseModuleSaveReq req) {
        return JsonData.buildSuccess(apiCaseModuleService.save(req));
    }

    /**
     * update
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ApiCaseModuleUpdateReq req) {
        return JsonData.buildSuccess(apiCaseModuleService.update(req));
    }
    /**
     * delete
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ApiCaseModuleDelReq req) {
        return JsonData.buildSuccess(apiCaseModuleService.delete(req));
    }


}
