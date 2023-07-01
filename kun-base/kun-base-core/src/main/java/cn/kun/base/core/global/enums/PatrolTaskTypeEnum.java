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
 * @description 巡检任务类型
 * @date 2023/4/20 15:30
 */
@SwaggerDisplayEnum
@Getter
@Schema(description = "巡检任务类型枚举")
@Valid
public enum PatrolTaskTypeEnum implements Serializable {

    CYCLE("cycle", "周期任务"),
    TIMING("timing", "定时任务"),
    IMMEDIATELY("immediately", "立即执行任务");

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String desc;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(code) + ":" + desc;
    }

    PatrolTaskTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
