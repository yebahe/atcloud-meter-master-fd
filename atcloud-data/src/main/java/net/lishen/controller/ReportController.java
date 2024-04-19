package net.lishen.controller;

import jakarta.annotation.Resource;
import net.lishen.dto.ReportDTO;
import net.lishen.req.ReportSaveReq;
import net.lishen.req.ReportUpdateReq;
import net.lishen.service.ReportService;
import net.lishen.util.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller
 * @Project：atcloud-meter
 * @name：ReportController
 * @Date：2024-01-19 19:46
 * @Filename：ReportController
 * 数据服务 -
 */
@RestController
@RequestMapping("/api/v1/report")
public class  ReportController {


    @Resource
    private ReportService reportService;

    @PostMapping("/save")
    public JsonData save(@RequestBody ReportSaveReq req){
        ReportDTO reportDTO =reportService.save(req);
        return JsonData.buildSuccess(reportDTO);
    }

    @PostMapping("/update")
    public JsonData update(@RequestBody ReportUpdateReq req){
        reportService.updateReportState(req);
        return JsonData.buildSuccess();
    }

}
