package net.lishen.controller.common.req;

import lombok.Data;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.controller.common.req
 * @Project：gpcloud-meter
 * @name：EnvironmentSaveReq
 * @Date：2024-01-07 21:13
 * @Filename：EnvironmentSaveReq
 */
@Data
public class EnvironmentDelReq {

        /**
         * 环境ID
         */
        private Long id;
        /**
         * 所属项目ID
         */
        private Long projectId;



}
