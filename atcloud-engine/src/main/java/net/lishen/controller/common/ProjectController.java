package net.lishen.controller.common;

import net.lishen.controller.common.req.ProjectDeleteReq;
import net.lishen.controller.common.req.ProjectSaveReq;
import net.lishen.controller.common.req.ProjectUpdateReq;
import net.lishen.service.common.ProjectService;
import net.lishen.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：li.shenF

 * @Project：gpcloud-meter
 * @name：ProjectController
 * @Date：2024-01-07 16:39
 * @Filename：ProjectController ：项目管理模块的接口
 */
@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    public JsonData list() {
        return JsonData.buildSuccess(projectService.list());
    }

    @PostMapping("/save")
    public JsonData save(@RequestBody ProjectSaveReq projectSaveReq) {

        return JsonData.buildSuccess(projectService.save(projectSaveReq));

    }


    /**
     * 更新项目
     *
     * @param req 项目更新请求对象
     * @return JsonData 返回更新结果
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ProjectUpdateReq req) {
        return JsonData.buildSuccess(projectService.update(req));
    }

    /**
     * 删除项目
     *
     * @param req 项目删除请求对象
     * @return JsonData 返回删除结果
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ProjectDeleteReq req) {
        // 调用项目服务删除项目，并返回删除结果
        return JsonData.buildSuccess(projectService.del(req.getId()));
    }



}
