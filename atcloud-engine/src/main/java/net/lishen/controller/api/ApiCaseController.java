package net.lishen.controller.api;

import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiCaseDelReq;
import net.lishen.controller.api.req.ApiCaseSaveReq;
import net.lishen.controller.api.req.ApiCaseUpdateReq;
import net.lishen.service.api.ApiCaseService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**

 * @Package：net.joseph.controller.api.req
 * @Project：gpcloud-meter
 * @name：ApiCaseController
 * @Date：2024-03-06 20:19
 * @Filename：ApiCaseController
 */
@RestController
@RequestMapping("/api/v1/api_case")
public class ApiCaseController {
    @Resource
    private ApiCaseService apiCaseService;


    /**
     * 根据id查找用例
     */
    @GetMapping("/find")
    public JsonData find(@RequestParam("projectId") Long projectId, @RequestParam("id") Long id){
        return JsonData.buildSuccess(apiCaseService.getById(projectId,id));
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public JsonData save(@RequestBody ApiCaseSaveReq req){
        return JsonData.buildSuccess(apiCaseService.save(req));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ApiCaseUpdateReq req){
        return JsonData.buildSuccess(apiCaseService.update(req));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ApiCaseDelReq req){
        return JsonData.buildSuccess(apiCaseService.delete(req));
    }


    @GetMapping("execute")
    public JsonData execute(@RequestParam("projectId") Long projectId, @RequestParam("id") Long caseId) {
        return apiCaseService.execute(projectId,caseId);
    }
}
