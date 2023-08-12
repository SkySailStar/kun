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
 * @description 巡检点类型
 * @date 2023/4/20 15:30
 */
@SwaggerDisplayEnum
@Getter
@Schema(description = "巡检点类型枚举")
@Valid
public enum PatrolPointTypeEnum implements Serializable {

    INIT_POINT("0", "初始点"),
    CHARGING_POINT("1", "充电点"),
    NAVIGATION_POINT("2", "导航点");

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String desc;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(code) + ":" + desc;
    }

    PatrolPointTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
