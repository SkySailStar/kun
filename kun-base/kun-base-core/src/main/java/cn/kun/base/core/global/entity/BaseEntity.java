package cn.kun.base.core.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.kun.base.core.global.config.LongJsonDeserializer;
import cn.kun.base.core.global.config.LongJsonSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共实体类
 *
 * @author SkySailStar
 */
@Schema(description = "公共实体类对象")
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @Id(keyType = KeyType.Auto)
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    protected Long id;
    
    @Schema(description = "创建者")
    @Column("create_by")
    @JsonIgnore
    protected String createBy;

    @Schema(description = "创建者名称")
    @Column("create_name")
    @JsonIgnore
    protected String createName;

    @Schema(description = "创建日期")
    @Column(value = "create_date", onInsertValue = "now()")
    @JsonIgnore
    protected LocalDateTime createDate;

    @Schema(description = "更新者")
    @Column("update_by")
    @JsonIgnore
    protected String updateBy;

    @Schema(description = "更新者名称")
    @Column("update_name")
    @JsonIgnore
    protected String updateName;

    @Schema(description = "更新日期")
    @Column(value = "update_date", onUpdateValue = "now()")
    @JsonIgnore
    protected LocalDateTime updateDate;

    @Schema(description = "删除标记;1是，0否")
    @Column("del_flag")
    @JsonIgnore
    protected String delFlag;

    @Schema(description = "备注")
    @Column("remarks")
    @JsonIgnore
    protected String remarks;
}