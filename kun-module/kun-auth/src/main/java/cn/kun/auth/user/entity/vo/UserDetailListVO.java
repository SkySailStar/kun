package cn.kun.auth.user.entity.vo;

import cn.kun.auth.user.entity.po.UserDetailList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author eric
 * @date 2023/3/13 9:23
 */
@Schema(description = "人员配置-返回值")
@Data
public class UserDetailListVO {

    @Schema(description = "用户所拥有的公司下所有用户信息")
    private List<UserDetailList>  users;

    @Schema(description = "用户所拥有的公司下所有用户已授权项目")
    private List<UserDetailList> userProjects;
}