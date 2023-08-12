package cn.kun.auth.system.user.mapper;

import cn.kun.auth.system.user.entity.po.SysUserRoleInner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 内部用户角色表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserRoleInnerMapper extends BaseMapper<SysUserRoleInner> {

    /**
     * 通过用户id获取用户下所有角色id
     *
     * @param userInnerId 用户id
     * @return string
     */
    public String getRoleInnerByUserInnerId(@Param("userInnerId") String userInnerId);
}
