package cn.kun.auth.system.company.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import cn.kun.auth.system.dept.entity.po.SysDeptInner;
import cn.kun.auth.system.dept.entity.po.SysDeptOuter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Schema(name = "AuthCompanyInfo", description = "公司缓存信息")
public class AuthCompanyInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "公司id")
    private Long id;

    @Schema(description = "公司名称")
    private String name;

    @Schema(description = "父级编号")
    private Long parentId;

    @Schema(description = "机构类型")
    @JSONField(serialize = false)
    private String type;

    @Schema(description = "归属区域")
    @JSONField(serialize = false)
    private Long areaId;

    @Schema(description = "信用代码")
    @JSONField(serialize = false)
    private String creditCode;

    @Schema(description = "部门列表")
    private List<SysDeptInner> deptList;

    @Schema(description = "部门列表")
    private List<SysDeptOuter> deptOuterList;
}
