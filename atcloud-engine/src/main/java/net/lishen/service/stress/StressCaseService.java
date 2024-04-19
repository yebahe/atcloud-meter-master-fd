package net.lishen.service.stress;

import net.lishen.controller.stress.req.StressCaseSaveReq;
import net.lishen.controller.stress.req.StressCaseUpdateReq;
import net.lishen.dto.stress.StressCaseDTO;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.service.stress
 * @Project：atcloud-meter
 * @name：StressCaseService
 * @Date：2024-01-18 10:26
 * @Filename：StressCaseService
 */
public interface StressCaseService {

    StressCaseDTO findById(Long projectId, Long caseId);


    int del(Long id, Long projectId);

    int save(StressCaseSaveReq req);

    int update(StressCaseUpdateReq req);

    void execute(Long projectId, Long caseId);
}
