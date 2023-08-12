package cn.kun.base.core.global.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import cn.kun.base.core.global.annotation.SwaggerDisplayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import jakarta.validation.Valid;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author kuangjc
 * @description 产品建设状态
 * @date 2023/3/15 15:30
 */
@SwaggerDisplayEnum
@Getter
@Schema(description = "任务执行状态枚举")
@Valid
public enum PatrolTaskExcutionStatusEnum implements Serializable {

    STATUS_UNEXECUTED("1", "未执行"),
    STATUS_EXECUTING("2", "正在执行"),
    STATUS_PAUSED("3", "暂停"),
    STATUS_STOPPED("4", "终止"),
    STATUS_FINISHED("5", "完成");

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String desc;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(code) + ":" + desc;
    }

//    @JsonValue
//    public String getCode() {
//        return code;
//    }

    PatrolTaskExcutionStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static PatrolTaskExcutionStatusEnum getByName(String name) {
        for (PatrolTaskExcutionStatusEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }

    @JsonValue
    public static PatrolTaskExcutionStatusEnum getByCode(String code) {
        for (PatrolTaskExcutionStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
