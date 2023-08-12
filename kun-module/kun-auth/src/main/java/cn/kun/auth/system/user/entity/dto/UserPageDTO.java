package cn.kun.auth.system.user.entity.dto;

import cn.kun.base.core.global.entity.dto.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Schema(description = "用户分页列表-传入值")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDTO extends BasePageDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long userId;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "所属公司")
    private Long companyId;

    @Schema(description = "所属公司列表id")
    private List<String> companyList;
}
