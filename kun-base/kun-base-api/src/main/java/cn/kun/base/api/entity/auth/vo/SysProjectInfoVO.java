package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "项目信息-返回值")
@Data
public class SysProjectInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "项目id")
    private String projectId;
    @Schema(description = "项目编号")
    private String projectNo;
    @Schema(description = "区域id")
    private String areaId;
    @Schema(description = "项目名称")
    private String projectName;
    @Schema(description = "坐标")
    private String coordinate;
    @Schema(description = "简称")
    private String abbreviation;
    @Schema(description = "项目详情id")
    private String projectDetailId;
    @Schema(description = "行业")
    private String industry;
    @Schema(description = "销售状态")
    private String saleStatus;

    @Schema(description = "内部用户")
    private List<UserRedisVO> userInnerList;
    @Schema(description = "外部用户")
    private List<UserRedisVO> userOuterList;
}
