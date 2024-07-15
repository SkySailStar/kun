package cn.kun.base.api.entity.product.vo;

import cn.kun.base.core.global.entity.vo.BaseDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 机器人地图信息-简单-返回值
 *
 * @author 天航星
 * @date 2023-05-19 15:50
 */
@Schema(description = "机器人地图信息-简单-返回值")
@Data
public class MapInfoSimpleVO extends BaseDetailVO {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目关联产品id;关联project_product_info表id")
    private Long projectProductId;

    @Schema(description = "地图名称")
    private String name;

    @Schema(description = "图片id")
    private Long picId;

    @Schema(description = "美化后图片id")
    private Long beautifyPicId;

    @Schema(description = "生效标识;FLAG")
    private String enableFlag;
}
