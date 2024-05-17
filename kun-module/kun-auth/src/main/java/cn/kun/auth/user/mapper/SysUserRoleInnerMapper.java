package cn.kun.auth.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sevnce.auth.user.entity.po.SysUserRoleInner;
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
