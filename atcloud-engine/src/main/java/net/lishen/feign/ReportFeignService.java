package net.lishen.feign;

import net.lishen.req.ReportSaveReq;
import net.lishen.req.ReportUpdateReq;
import net.lishen.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.feign
 * @Project：atcloud-meter
 * @name：ReportFeignService
 * @Date：2024-01-20 18:44
 * @Filename：ReportFeignService
 */
@FeignClient("data-service")
public interface ReportFeignService {

    /**
     * 初始化测试报告接口
     */
    @PostMapping("/api/v1/report/save")
    JsonData save(@RequestBody ReportSaveReq req);


    @PostMapping("/api/v1/report/update")
    void update(@RequestBody ReportUpdateReq req);
}
