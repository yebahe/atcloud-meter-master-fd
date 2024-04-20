package net.lishen.controller.stress;

import net.lishen.controller.stress.req.StressCaseDelReq;
import net.lishen.controller.stress.req.StressCaseSaveReq;
import net.lishen.controller.stress.req.StressCaseUpdateReq;
import net.lishen.dto.stress.StressCaseDTO;
import net.lishen.service.stress.StressCaseService;
import net.lishen.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.stress
 * @Project：atcloud-meter
 * @name：StressCaseController
 * @Date：2024-01-18 10:25
 * @Filename：StressCaseController 测试用例接口开发实战
 */
@RestController
@RequestMapping("/api/v1/stress_case")
public class StressCaseController {


    @Autowired
    private StressCaseService stressCaseService;

    //根据projectId和用例id查找
    @GetMapping("/find")
    public JsonData findById(@RequestParam("projectId") Long projectId, @RequestParam("Id") Long caseId) {
        StressCaseDTO stressCaseDTO = stressCaseService.findById(projectId, caseId);
        return JsonData.buildSuccess(stressCaseDTO);
    }

    //delete
    @PostMapping("/delete")
    public JsonData delete(@RequestBody StressCaseDelReq req) {

        return JsonData.buildSuccess(stressCaseService.del(req.getId(),req.getProjectId()));
    }

    //save
    @PostMapping("/save")
    public JsonData save(@RequestBody StressCaseSaveReq req) {

        return JsonData.buildSuccess(stressCaseService.save(req));
    }

    //update
    @PostMapping("/update")
    public JsonData update(@RequestBody StressCaseUpdateReq req) {

        return JsonData.buildSuccess(stressCaseService.update(req));
    }

    /**
     * 执行测试用例的接口、
     * 1、查询用例详情
     * 2、初始化测试报告
     * 3、判断压测类型
     * 4、初始化测试引擎、组装测试计划
     * 5、执行压测
     * 6、发送压测结果
     * 7、清理数据
     * 8、通知压测结果
     * @param projectId
     * @param caseId
     * @return
     */
    //execute
    @GetMapping("/execute")
    public JsonData execute(@RequestParam("projectId") Long projectId, @RequestParam("id") Long caseId) {
        stressCaseService.execute(projectId,caseId);
        return JsonData.buildSuccess();
    }
}
