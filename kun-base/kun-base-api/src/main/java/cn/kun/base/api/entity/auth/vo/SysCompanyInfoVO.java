package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Schema(description = "公司缓存信息-返回值")
@Data
public class SysCompanyInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "公司id")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "上级公司id")
    private String parentId;

    @Schema(description = "归属区域")
    private String areaId;

    @Schema(description = "机构类型")
    private String type;

    @Schema(description = "企业logo")
    private String logo;

    @Schema(description = "部门列表")
    private List<DeptInfoVO> deptInfoVoList;
}
