package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *  用户基本信息返回VO
 * @author kuangjc
 * @date 2023/5/15 17:26
 */
@Schema(description = "用户详情-返回值")
@Data
public class SimpleUserVO implements Serializable {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "登录名")
    private String loginName;

}
