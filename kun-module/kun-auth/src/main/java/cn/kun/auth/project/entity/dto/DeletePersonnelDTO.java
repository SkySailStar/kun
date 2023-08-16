package cn.kun.auth.project.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/27 9:33
 */
@Schema(description = "项目-删除人员配置-传入值")
@Data
public class DeletePersonnelDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "项目编号")
    @NotBlank(message = "项目编号不能为空")
    private String projectNo;
}