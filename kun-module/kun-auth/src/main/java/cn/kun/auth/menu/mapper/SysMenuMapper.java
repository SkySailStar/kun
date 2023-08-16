package cn.kun.auth.menu.mapper;

import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.menu.entity.vo.MenuRedisVo;
import cn.kun.auth.menu.entity.po.SysMenu;
import cn.kun.auth.menu.entity.vo.MenuPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.menu.entity.dto.MenuPageDTO;
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
     * 查询分页列表
     * @param dto 菜单分页列表-传入值
     * @return 分页列表-返回值
     */
    List<MenuPageVO> selectInnerPage(@Param("dto") MenuPageDTO dto);
    /**
     * 查询分页列表
     * @param dto 菜单分页列表-传入值
     * @return 分页列表-返回值
     */
    List<MenuPageVO> selectOuterPage(@Param("dto") MenuPageDTO dto);


    /**
     * 根据内部职位id查询该职位授予的所有菜单信息
     * @param dto
     * @return
     */
    public List<SysMenu> selectListByJobInnerId(@Param("dto") UserMenuPermissDTO dto);

    /**
     * 外部职位id查询该职位授予的所有菜单信息
     * @param dto
     * @return
     */
    public List<SysMenu> selectListByJobOuterId(@Param("dto")UserMenuPermissDTO dto);

    /**
     * 根据内部用户ID查询权限列表
     * @param userId 内部用户ID
     * @return 权限列表
     */
    List<String> selectPermsInner(@Param("userId") Long userId);

    /**
     * 根据外部用户ID查询权限列表
     * @param userId 外部用户ID
     * @return 权限列表
     */
    List<String> selectPermsOuter(@Param("userId") Long userId);



    /**
     * 通过内部角色id查询内部菜单权限信息
     * @param roleInnerId
     * @return
     */
    List<MenuRedisVo> getRoleMenuInnerAuth(@Param("roleInnerId") Long roleInnerId);

    /**
     * 通过外部角色id查询菜单权限信息
     * @param roleOuterId
     * @return
     */
    List<MenuRedisVo> getRoleMenuOuterAuth(@Param("roleOuterId") Long roleOuterId);


    /**
     *  查询内部角色-项目菜单权限
     * @param dto
     * @return
     */
    List<SysMenu> selectListByRoleInnerId(@Param("dto") UserMenuPermissDTO dto);

    /**
     *  查询外部角色-项目菜单权限
     * @param dto
     * @return
     */
    List<SysMenu> selectListByRoleOuterId(UserMenuPermissDTO dto);



    /**
     *  添加菜单-查询上级菜单
     * @param userId
     * @return
     */
    List<SysMenu> menuList(@Param("userId")Long userId,@Param("serviceCode")String serviceCode);

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */
    List<SysMenu> innerMenuListInfo(@Param("userId")Long userId);


    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */
    List<SysMenu> outerMenuListInfo(@Param("userId")Long userId);
    /**
     * 根据外部用户id查询菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> menuListByUserOuterId(@Param("userId")Long userId,@Param("serviceCode")String serviceCode);

    /**
     * 根据当前登录人id 获取系统名称
     * @param userId
     * @return
     */
    List<String> serviceCode(@Param("userId")Long userId);

}
