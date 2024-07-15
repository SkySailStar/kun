package cn.kun.base.core.global.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 公用-分页-传入值
 *
 * @author 天航星
 * @date 2023-01-30 15:20
 */
@Schema(description = "公用-分页-传入值")
@Data
public class BasePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码")
    private Integer pageNo = 1;

    @Schema(description = "条数")
    private Integer pageSize = 10;

    @Schema(description = "排序")
    private String orderBy;

    @Schema(description = "项目编号列表")
    private List<String> projectNoList;
}