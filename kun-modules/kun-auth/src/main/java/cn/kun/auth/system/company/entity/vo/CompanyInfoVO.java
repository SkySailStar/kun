package cn.kun.auth.system.company.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/13 9:45
 */
@Schema(description = "公司基本信息-返回值")
@Data
public class CompanyInfoVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "上级id")
    private Long parentId;

    @Schema(description = "所有上级")
    private String parentIds;
}