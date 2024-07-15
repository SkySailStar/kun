package cn.kun.base.api.entity.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 挂载装置定义属性配置模板-查询-返回值
 *
 * @author 天航星
 * @date 2023-03-14 16:31
 */
@Schema(description = "挂载装置定义属性配置模板-查询-返回值")
@Data
public class DeviceDefinePropertiesTemplateQueryVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "挂载装置类型")
    private String deviceTypeName;

    @Schema(description = "数据字段")
    private String field;

    @Schema(description = "字段名称")
    private String fieldName;

    @Schema(description = "字段默认值")
    private String fieldDefaultValue;

    @Schema(description = "字段说明")
    private String fieldExplain;

    @Schema(description = "是否必填;1:是,0:否")
    private String fieldRequire;

    @Schema(description = "是否监控项;1是,0否")
    private String monitorFlag;

    @Schema(description = "是否可配置;1,是,0否")
    private String configFlag;
}