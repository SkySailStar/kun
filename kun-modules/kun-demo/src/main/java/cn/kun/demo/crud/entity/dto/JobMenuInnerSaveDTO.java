package cn.kun.demo.crud.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 职位菜单内部关联-保存-传入值
 *
 * @author 廖航
 * @date 2023-02-27 17:28
 */
@Schema(description = "职位菜单内部关联-保存-传入值")
@Data
public class JobMenuInnerSaveDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "内部职位ID")
    @NotNull(message = "内部职位ID：不能为空")
    private Long jobInnerId;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIdList;
}