package net.lishen.controller.api;

import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiCaseStepDelReq;
import net.lishen.controller.api.req.ApiCaseStepSaveReq;
import net.lishen.controller.api.req.ApiCaseStepUpdateReq;
import net.lishen.service.api.ApiCaseStepService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**

 * @Package：net.joseph.controller.api
 * @Project：gpcloud-meter
 * @name：ApiCaseStepController
 * @Date：2024-03-06 20:56
 * @Filename：ApiCaseStepController
 */
@RestController
@RequestMapping("/api/v1/api_case_step")
public class ApiCaseStepController {
    @Resource
    private ApiCaseStepService apiCaseStepService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public JsonData save(@RequestBody ApiCaseStepSaveReq req) {
        return JsonData.buildSuccess(apiCaseStepService.save(req));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ApiCaseStepUpdateReq req) {
        return JsonData.buildSuccess(apiCaseStepService.update(req));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ApiCaseStepDelReq req) {
        return JsonData.buildSuccess(apiCaseStepService.delete(req.getId(),req.getProjectId()));
    }
}
