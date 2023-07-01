package cn.kun.base.api.entity.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * @author eric
 * @date 2023/3/29 14:24
 */
@Schema(description = "职位信息-返回值")
@Data
public class JobRedisVO {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "职位id")
    private String jobId;

    @Schema(description = "职位名称")
    private String jobName;

    private DeptRedisVO deptRedisVO;

    private CompanyRedisVO companyRedisVO;
}