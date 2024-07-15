package cn.kun.base.core.mq.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 错误信息-消息队列-传入值
 *
 * @author 天航星
 * @date 2023-04-11 17:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "错误信息-消息队列-传入值")
@Data
public class ErrorInfoMqDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编码")
    @NotBlank(message = "编码：不能为空")
    @Size(max = 100, message = "编码：长度不能大于100")
    private String code;

    @Schema(description = "名称")
    @NotBlank(message = "名称：不能为空")
    @Size(max = 255, message = "名称：长度不能大于255")
    private String name;

    @Schema(description = "英文名称")
    @Size(max = 255, message = "英文名称：长度不能大于255")
    private String enName;

    @Schema(description = "扩展信息")
    @Size(max = 2048, message = "扩展信息：长度不能大于2048")
    private String extendInfo;

    @Schema(description = "类型")
    @Size(max = 64, message = "类型：长度不能大于64")
    private String type;

    /**
     * 初始化
     * @param code 编码
     * @param name 名称
     * @return 错误信息-添加-传入值
     */
    public static ErrorInfoMqDTO of(String code, String name) {
        return of(code, name, null, null, null);
    }
    
    /**
     * 初始化
     * @param code 编码
     * @param name 名称
     * @param type 类型
     * @return 错误信息-添加-传入值
     */
    public static ErrorInfoMqDTO of(String code, String name, String type) {
        return of(code, name, null, null, type);
    }
    
    /**
     * 初始化
     * @param code 编码
     * @param name 名称
     * @param extendInfo 扩展信息
     * @param type 类型
     * @return 错误信息-添加-传入值
     */
    public static ErrorInfoMqDTO of(String code, String name, String extendInfo, String type) {
        return of(code, name, null, extendInfo, type);
    }

    /**
     * 初始化
     * @param code 编码
     * @param name 名称
     * @param enName 英文名称
     * @param extendInfo 扩展信息
     * @param type 类型
     * @return 错误信息-添加-传入值
     */
    public static ErrorInfoMqDTO of(String code, String name, String enName, String extendInfo, String type) {
        return new ErrorInfoMqDTO(code, name, enName, extendInfo, type);
    }
    
}
