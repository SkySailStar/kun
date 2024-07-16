package cn.kun.auth.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.kun.auth.security.entity.po.SysMenu;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    
    /**
     * 根据内部用户ID查询权限列表
     * @param userId 内部用户ID
     * @return 权限列表
     */
    List<String> selectPermissionList(@Param("userId") String userId);
}
