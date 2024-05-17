package cn.kun.base.core.global.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    protected String id;

    @Schema(description = "创建人")
    @TableField(value = "create_person", fill = FieldFill.INSERT)
    @JsonIgnore
    protected String createPerson;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    protected LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField(value = "update_person", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected String updatePerson;
    
    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected LocalDateTime updateTime;
    
}