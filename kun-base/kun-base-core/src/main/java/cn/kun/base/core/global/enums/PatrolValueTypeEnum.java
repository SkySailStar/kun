package cn.kun.base.core.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import cn.kun.base.core.global.annotation.SwaggerDisplayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.Valid;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author kuangjc
 * @description 巡检值类型枚举
 * @date 2023/5/11
 */
@SwaggerDisplayEnum
@Getter
@Schema(description = "巡检值类型枚举")
@Valid
public enum PatrolValueTypeEnum implements Serializable {

    NUMERICAL_TYPE("1", "数值型"),
    FIXED_VALUE("2", "固定值");

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String desc;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(code) + ":" + desc;
    }

    PatrolValueTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
