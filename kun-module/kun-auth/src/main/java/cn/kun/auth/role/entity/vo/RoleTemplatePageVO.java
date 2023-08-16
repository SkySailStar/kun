package cn.kun.auth.role.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "角色分页列表-返回值")
@Data
public class RoleTemplatePageVO implements Serializable {

    @Schema(description = "产品类型")
    private String productType;

    @Schema(description = "更新人")
    private String updateByName;

    @Schema(description = "更新时间")
    private LocalDateTime updateDate;
}
