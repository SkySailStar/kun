package cn.kun.base.api.entity.auth.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "用户信息-返回值")
@Data
public class SysUserInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long userId;

    @Schema(description = "用户名称")
    private String loginName;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "用户头像")
    private String photo;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "公司id")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyName;
}
