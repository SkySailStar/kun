package cn.kun.auth.menu.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/7 9:29
 */

@Schema(description = "菜单缓存-返回值")
@Data
public class MenuRedisVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "菜单是否可见")
    private String isShow;

    @Schema(description = "权限标识")
    private String permission;
}