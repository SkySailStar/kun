package cn.kun.base.api.entity.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 项目编号列表传入值
 *
 * @author 胡鹤龄
 * @date 2023-05-22 10:51
 */
@Schema(description = "项目列表-传入值")
@Data
public class ProjectNoListDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目编号列表")
    @NotEmpty(message = "项目编号列表不能为空")
    private List<String> projectNoList;
}
