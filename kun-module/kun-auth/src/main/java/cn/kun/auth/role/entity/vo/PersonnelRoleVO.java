package cn.kun.auth.role.entity.vo;

import cn.kun.auth.user.entity.po.UserDetailList;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author eric
 * @date 2023/3/28 14:52
 */
@Schema(description = "角色-人员配置-返回值")
@Data
public class PersonnelRoleVO {
    @Schema(description = "用户所拥有的公司下所有内部用户信息")
    private List<BaseSelectVO> allUserInner;

    @Schema(description = "用户所拥有的公司下所有内部用户已授权项目")
    private List<UserDetailList> usersInner;

    @Schema(description = "用户所拥有的公司下所有内部用户信息")
    private List<BaseSelectVO> allUserOuter;

    @Schema(description = "用户所拥有的公司下所有内部用户已授权项目")
    private List<UserDetailList> usersOuter;
}