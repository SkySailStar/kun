package cn.kun.base.api.entity.auth.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author eric
 * @date 2023/3/29 15:59
 */
@Schema(description = "职位缓存信息-返回值")
@Data
public class JobInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "部门id")
    private Long deptId;

    @Schema(description = "职位id")
    private Long id;

    @Schema(description = "职位名称")
    private String name;
}