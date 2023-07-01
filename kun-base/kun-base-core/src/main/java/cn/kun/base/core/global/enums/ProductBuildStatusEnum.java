package cn.kun.base.core.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import cn.kun.base.core.global.annotation.SwaggerDisplayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author kuangjc
 * @description 产品建设状态
 * @date 2023/3/15 15:30
 */
@SwaggerDisplayEnum
@Getter
@Schema(description = "产品建设状态枚举")
@Valid
public enum ProductBuildStatusEnum implements Serializable {

    STATUS_ASSEMBLING("status_assembling", "装机"),
    STATUS_DISMANTLE("status_dismantle", "拆机");

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

    ProductBuildStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static ProductBuildStatusEnum getByName(String name) {
        for (ProductBuildStatusEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }

    @JsonValue
    public static ProductBuildStatusEnum getByCode(String code) {
        for (ProductBuildStatusEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    @JsonCreator
    public static ProductBuildStatusEnum create(String code) {
        if (StringUtils.isEmpty(code)) {//为空跳过
            return null;
        }
        try {
            return ProductBuildStatusEnum.getByCode(code);
        } catch (IllegalArgumentException e) {
            for (ProductBuildStatusEnum productBuildStatusEnum : ProductBuildStatusEnum.values()) {
                try {
                    if (productBuildStatusEnum.code.equals(code)) {
                        return productBuildStatusEnum;
                    }
                } catch (NumberFormatException n) {
                    if (productBuildStatusEnum.desc.equals(code)) {
                        return productBuildStatusEnum;
                    }
                }
            }
            throw new IllegalArgumentException("No element matches " + code);
        }
    }
}
