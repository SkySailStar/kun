package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/29 10:46
 */
@Schema(description = "项目信息缓存-返回值")
@Data
public class UserRedisVO {
    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "用户登录名")
    private String loginName;
}