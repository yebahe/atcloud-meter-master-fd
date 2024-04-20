package net.lishen.controller.stress;

import net.lishen.controller.stress.req.StressCaseModuleSaveReq;
import net.lishen.controller.stress.req.StressCaseModuleUpdateReq;
import net.lishen.controller.stress.req.StressCaseMoudleDelReq;
import net.lishen.service.stress.StressCaseModuleService;
import net.lishen.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

 * @Package：net.joseph.controller.stress
 * @Project：atcloud-meter
 * @name：StressCaseModule
 * @Date：2024-01-18 22:28
 * @Filename：StressCaseModule 用例模块管理接口开发实战**
 */
@RestController
@RequestMapping("/api/v1/stress_case_module")
public class StressCaseModuleController {

    @Autowired
    private StressCaseModuleService stressCaseMoudleService;

    //list
    @RequestMapping("/list")
    public JsonData list(@RequestParam("projectId") Long projectId) {
        return JsonData.buildSuccess(stressCaseMoudleService.list(projectId));
    }

    //find
    @RequestMapping("/find")
    public JsonData findById(@RequestParam("projectId") Long projectId, @RequestParam("id") Long moudleId) {
        return JsonData.buildSuccess(stressCaseMoudleService.findById(projectId, moudleId));
    }

    //delete
    @RequestMapping("/delete")
    public JsonData delete(@RequestBody StressCaseMoudleDelReq req) {
        return JsonData.buildSuccess(stressCaseMoudleService.delete(req.getId(), req.getProjectId()));
    }

    //save
    @RequestMapping("/save")
    public JsonData save(@RequestBody StressCaseModuleSaveReq req) {
        return JsonData.buildSuccess(stressCaseMoudleService.save(req));
    }

    //update
    @RequestMapping("/update")
    public JsonData update(@RequestBody StressCaseModuleUpdateReq req) {
        return JsonData.buildSuccess(stressCaseMoudleService.update(req));
    }
}
