package cn.kun.base.api.entity.product.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.kun.base.core.global.entity.vo.BasePageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目下挂靠的产品信息表
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-15 17:08
 */
@Getter
@Setter
@TableName("project_product_info")
@Schema(name = "ProjectProductInfo", description = "项目下挂靠的产品信息表")
public class ProjectProductInfoPageVO extends BasePageVO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品编号")
    private String productNo;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "产品ID")
    private Long productId;

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "产品定义名称")
    private String productDefineName;

    @Schema(description = "产品类别第一级ID")
    private Long productFirstCategoryId;

    @Schema(description = "产品类别第二级ID")
    private Long productSecondCategoryId;

    @Schema(description = "产品类别第三级ID")
    private Long productThirdCategoryId;

    @Schema(description = "产品类别第一级名称")
    private String productFirstCategoryName;

    @Schema(description = "产品类别第二级名称")
    private String productSecondCategoryName;

    @Schema(description = "产品类别第三级名称")
    private String productThirdCategoryName;

    @Schema(description = "产品建设状态,关联字典类型:BUILD_STATUS")
    private String buildStatus;

    @Schema(description = "产品建设状态名称")
    private String buildStatusName;

//    @Schema(description = "产品运行状态;关联字典类型:PRODUCT_RUNNING_STATUS（冗余）")
//    private String status;

//    @Schema(description = "产品装配状态,关联字典类型:ASSEMBLING_STATUS（冗余）")
//    private String assemblingStatus;

    @Schema(description = "装机时间")
    private LocalDateTime assemblingTime;

    @Schema(description = "拆机时间")
    private LocalDateTime dismantleTime;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "安装位置")
    private String installLocation;

}
