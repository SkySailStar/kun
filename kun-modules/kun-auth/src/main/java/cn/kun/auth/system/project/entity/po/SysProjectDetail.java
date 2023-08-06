package cn.kun.auth.system.project.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>
 * 项目详情表
 * </p>
 *
 * @author SkySailStar
 * @since 2023-02-10 11:06
 */
@Getter
@Setter
@TableName("sys_project_detail")
@Schema(name = "SysProjectDetail", description = "项目详情表")
public class SysProjectDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目id（关联sys_project表中的project_no）")
    @TableField("project_no")
    private String projectNo;

    @Schema(description = "项目概述")
    @TableField("summary")
    private String summary;

    @Schema(description = "项目规模")
    @TableField("scale")
    private String scale;

    @Schema(description = "项目网址")
    @TableField("website")
    private String website;

    @Schema(description = "销售状态（0：试点；1：中标；2：交付）")
    @TableField("sale_status")
    private String saleStatus;

    @Schema(description = "平面区域")
    @TableField("plane_area")
    private String planeArea;
}
