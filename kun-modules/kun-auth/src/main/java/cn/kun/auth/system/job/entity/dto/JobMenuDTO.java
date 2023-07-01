package cn.kun.auth.system.job.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Schema(description = "职位菜单授权-传入值")
@Data
public class JobMenuDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "职位编号")
    @NotNull(message = "职位编号不能为空")
    private Long jobId;

    @Schema(description = "菜单编号列表")
    @NotEmpty(message = "菜单编号列表不能为空")
    private List<Long> menuIdList;
}
