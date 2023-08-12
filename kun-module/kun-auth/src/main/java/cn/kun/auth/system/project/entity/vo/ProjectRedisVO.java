package cn.kun.auth.system.project.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author eric
 * @date 2023/3/7 9:33
 */
@Schema(description = "项目信息-返回值")
@Data
public class ProjectRedisVO {

    @Schema(description = "项目编号")
    private String projectNo;

    @Schema(description = "项目名称")
    private String projectName;
}