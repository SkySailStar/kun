package cn.kun.auth.system.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "用户职位授权-传入值")
@Data
public class UserJobDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "职位id列表")
    private List<Long> jobIdList;
}
