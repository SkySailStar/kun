package cn.kun.auth.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户项目授权-传入值")
@Data
public class UserProjectDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;
}
