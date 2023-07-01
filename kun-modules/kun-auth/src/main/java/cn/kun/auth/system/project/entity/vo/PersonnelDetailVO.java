package cn.kun.auth.system.project.entity.vo;

import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author eric
 * @date 2023/3/28 9:45
 */
@Schema(description = "项目-人员配置-返回值")
@Data
public class PersonnelDetailVO {

    @Schema(description = "用户所拥有的公司下所有内部用户信息")
    private List<BaseSelectVO> allUserInner;

    @Schema(description = "用户所拥有的公司下所有内部用户已授权项目")
    private List<UserDetailList> usersInner;

    @Schema(description = "用户所拥有的公司下所有内部用户信息")
    private List<BaseSelectVO> allUserOuter;

    @Schema(description = "用户所拥有的公司下所有内部用户已授权项目")
    private List<UserDetailList> usersOuter;

}