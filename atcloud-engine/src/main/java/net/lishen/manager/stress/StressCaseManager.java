package net.lishen.manager.stress;

import net.lishen.controller.stress.req.StressCaseSaveReq;
import net.lishen.controller.stress.req.StressCaseUpdateReq;
import net.lishen.model.StressCaseDO;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.manager.stress
 * @Project：gpcloud-meter
 * @name：StressCaseManager
 * @Date：2024-01-18 11:16
 * @Filename：StressCaseManager
 */
public interface StressCaseManager  {
    StressCaseDO findById(Long projectId, Long caseId);


    int delteById(Long id, Long projectId);

    int save(StressCaseSaveReq req);

    int update(StressCaseUpdateReq req);
}
