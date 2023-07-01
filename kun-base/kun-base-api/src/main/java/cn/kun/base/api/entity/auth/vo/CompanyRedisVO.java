package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/29 14:20
 */
@Schema(description = "公司部分缓存-返回值")
@Data
public class CompanyRedisVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门id")
    private String companyId;

    @Schema(description = "部门名称")
    private String companyName;
}