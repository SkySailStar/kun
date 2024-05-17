package cn.kun.auth.menu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.menu.entity.dto.MenuAddDTO;
import com.sevnce.auth.menu.entity.dto.MenuEditDTO;
import com.sevnce.auth.menu.entity.dto.MenuPageDTO;
import com.sevnce.auth.menu.entity.dto.UserMenuPermissDTO;
import com.sevnce.auth.menu.entity.po.SysMenu;
import com.sevnce.auth.menu.entity.vo.MenuDetailVO;
import com.sevnce.auth.menu.entity.vo.MenuPageVO;
import com.sevnce.auth.menu.entity.vo.MenuRedisVo;
import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 分页列表
     * @param dto 菜单分页列表-传入值
     * @return 菜单分页列表-返回值
     */
    Page<MenuPageVO> page(MenuPageDTO dto);


    /**
     * 详情
     * @param id 主键
     * @return 菜单详情-返回值
     */
    MenuDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 菜单添加-传入值
     * @return 菜单对象-返回值
     */
    SysMenu add(MenuAddDTO dto);

    /**
     * 修改
     * @param dto 菜单修改-传入值
     * @return 菜单对象-返回值
     */
    SysMenu edit(MenuEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息
     * @return
     */
    List<BaseSelectVO> menuList(UserMenuPermissDTO dto);

    /**
     *  根据获取当前用户所拥有的系统名称
     * @return
     */
    List<BaseSelectVO> serviceCode();

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息
     * @return
     */

    List<BaseSelectVO> menuListByUserInnerId(UserMenuPermissDTO dto);

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */

    List<BaseSelectVO> innerMenuListInfo(Long userId);

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */

    List<BaseSelectVO> outerMenuListInfo(Long userId);

    /**
     *  根据外部用户获取当前用户所拥有的菜单信息
     * @return
     */
    List<BaseSelectVO> menuListByUserOuterId(UserMenuPermissDTO dto);


    /**
     * 根据内部用户ID查询权限列表
     * @param userId 内部用户ID
     * @return 权限列表
     */
    List<String> selectPermsInner(Long userId);

    /**
     * 根据外部用户ID查询权限列表
     * @param userId 外部用户ID
     * @return 权限列表
     */
    List<String> selectPermsOuter(Long userId);


    /**
     * 通过内部角色id查询内部菜单权限信息
     * @param roleInnerId
     * @return
     */
    List<MenuRedisVo> getRoleMenuInnerAuth(Long roleInnerId);

    /**
     * 通过外部角色id查询菜单权限信息
     * @param roleOuterId
     * @return
     */
    List<MenuRedisVo> getRoleMenuOuterAuth(Long roleOuterId);




    /**
     *  获取系统编码
     * @return
     */
    List<SysMenu> menuServiceCode();

    /**
     * 根据职位id查询菜单权限信息
     * @return
     */
    List<BaseSelectVO> selectListByJobInnerId(UserMenuPermissDTO dto);

    /**
     * 根据外部公司职位查看该职位的所有菜单
     * @param dto
     * @return
     */
    List<BaseSelectVO> selectListByJobOuterId(UserMenuPermissDTO dto);

    /**
     *  查询内部角色-项目菜单权限
     * @param dto
     * @return
     */
    List<SysMenu> selectListByRoleInnerId( UserMenuPermissDTO dto);

    /**
     *  查询外部角色-项目菜单权限
     * @param dto
     * @return
     */
    List<SysMenu> selectListByRoleOuterId(UserMenuPermissDTO dto);


    /**
     * 菜单左侧树状图
     *
     * @return
     */
    List<BaseSelectVO> tree();
}
