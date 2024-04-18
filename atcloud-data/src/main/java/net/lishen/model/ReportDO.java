package net.lishen.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 测试报告表
 * </p>
 *
 * @author joseph,
 * @since 2024-01-06
 */
@Getter
@Setter
@TableName("report")
@Schema(name = "ReportDO", description = "测试报告表")
public class ReportDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "所属项目ID")
    @TableField("project_id")
    private Long projectId;

    @TableField("case_id")
    private Long caseId;

    @Schema(description = "报告类型 ")
    @TableField("type")
    private String type;

    @Schema(description = "报告名称")
    @TableField("name")
    private String name;

    @Schema(description = "执行状态")
    @TableField("execute_state")
    private String executeState;

    @Schema(description = "概要")
    @TableField("summary")
    private String summary;

    @Schema(description = "开始时间")
    @TableField("start_time")
    private Long startTime;

    @Schema(description = "结束时间")
    @TableField("end_time")
    private Long endTime;

    @Schema(description = "消耗时间")
    @TableField("expand_time")
    private Long expandTime;

    @Schema(description = "步骤数量")
    @TableField("quantity")
    private Long quantity;

    @Schema(description = "通过数量")
    @TableField("pass_quantity")
    private Long passQuantity;

    @Schema(description = "失败数量")
    @TableField("fail_quantity")
    private Long failQuantity;

    @Schema(description = "创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

    @Schema(description = "更新时间")
    @TableField("gmt_modified")
    private Date gmtModified;
}
