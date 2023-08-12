package cn.kun.auth.system.project.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.List;

/**
 * @author eric
 * @date 2023/3/15 11:08
 */
@Schema(description = "项目-人员配置分页-传入值")
@Data
public class SavePersonnelDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private List<Long> userIds;

    @Schema(description = "项目编号")
    @NotNull(message = "项目编号不能为空")
    private String projectNo;
}