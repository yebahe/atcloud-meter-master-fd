package net.lishen.controller.api;

import jakarta.annotation.Resource;
import net.lishen.controller.api.req.ApiDelReq;
import net.lishen.controller.api.req.ApiSaveReq;
import net.lishen.controller.api.req.ApiUpdateReq;
import net.lishen.service.api.ApiService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.api
 * @Project：gpcloud-meter
 * @name：ApiController
 * @Date：2024-03-06 18:07
 * @Filename：ApiController
 */
@RestController
@RequestMapping("/api/v1/api")
public class ApiController {

    @Resource
    private ApiService apiService;

    /**
     * 根据projectId 和 id 查询API信息
     */
    @GetMapping("/find")
    public JsonData find(@RequestParam("projectId") Long projectId, @RequestParam("id") Long id)
    {
        return JsonData.buildSuccess(apiService.getById(projectId,id));
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public JsonData save(ApiSaveReq req) {
        return JsonData.buildSuccess(apiService.save(req));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public JsonData update(@RequestBody ApiUpdateReq req) {
        return JsonData.buildSuccess(apiService.update(req));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public JsonData delete(@RequestBody ApiDelReq req) {
        return JsonData.buildSuccess(apiService.delete(req));
    }



}
