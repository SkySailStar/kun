package cn.kun.base.core.global.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.kun.base.core.global.config.LongJsonDeserializer;
import cn.kun.base.core.global.config.LongJsonSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共实体类
 *
 * @author 廖航
 */
@Schema(description = "公共实体类对象")
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    protected Long id;
    
    @Schema(description = "创建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @JsonIgnore
    protected String createBy;

    @Schema(description = "创建者名称")
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    @JsonIgnore
    protected String createName;

    @Schema(description = "创建日期")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @JsonIgnore
    protected LocalDateTime createDate;

    @Schema(description = "更新者")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected String updateBy;

    @Schema(description = "更新者名称")
    @TableField(value = "update_name", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected String updateName;

    @Schema(description = "更新日期")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected LocalDateTime updateDate;

    @Schema(description = "删除标记;1是，0否")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    protected String delFlag;

    @Schema(description = "备注")
    @TableField("remarks")
    @JsonIgnore
    protected String remarks;

    @Schema(description = "备注1")
    @TableField("remark1")
    @JsonIgnore
    protected String remark1;

    @Schema(description = "备注2")
    @TableField("remark2")
    @JsonIgnore
    protected String remark2;

    @Schema(description = "备注3")
    @TableField("remark3")
    @JsonIgnore
    protected String remark3;

    @Schema(description = "备注4")
    @TableField("remark4")
    @JsonIgnore
    protected String remark4;

    @Schema(description = "备注5")
    @TableField("remark5")
    @JsonIgnore
    protected String remark5;
}