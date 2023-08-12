package cn.kun.auth.system.role.service;

import cn.kun.auth.system.role.entity.dto.RoleEditDTO;
import cn.kun.auth.system.role.entity.dto.RolePersonnelDTO;
import cn.kun.auth.system.role.entity.dto.RoleUsersDTO;
import cn.kun.auth.system.role.entity.dto.RoleAddDTO;
import cn.kun.auth.system.role.entity.dto.RolePageDTO;
import cn.kun.auth.system.role.entity.dto.RolePermissDTO;
import cn.kun.auth.system.role.entity.vo.PersonnelRoleVO;
import cn.kun.auth.system.role.entity.vo.RoleDetailVO;
import cn.kun.auth.system.role.entity.vo.RoleMenuPermissVO;
import cn.kun.auth.system.role.entity.vo.RolePageVO;
import cn.kun.auth.system.role.entity.vo.SysRoleInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.system.role.entity.po.SysRoleOuter;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.RoleProjectPermissVO;
import cn.kun.auth.system.user.entity.vo.UserPersonnelPageVO;
import cn.kun.base.api.entity.auth.vo.RoleInfoByUserIdVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 外部角色表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysRoleOuterService extends IService<SysRoleOuter> {

    /**
     * 分页
     * @param dto 角色分页列表-传入值
     * @return 角色分页列表-返回值
     */
    Page<RolePageVO> page(RolePageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 角色详情-返回值
     */
    RoleDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 角色添加-传入值
     * @return 角色对象-返回值
     */
    SysRoleOuter add(RoleAddDTO dto);

    /**
     * 修改
     * @param dto 角色修改-传入值
     * @return 角色对象-返回值
     */
    SysRoleOuter edit(RoleEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 通过外部角色id查询角色缓存信息
     * @param roleOuterId
     * @return
     */
    SysRoleInfoVO getRoleOuterInfoByRoleOuterId(Long roleOuterId);


    List<RoleInfoByUserIdVO> getRoleOuterByRoleOuterId(Long roleOuterId);

    List<RoleInfoByUserIdVO> getRoleInfoByUserId(Long userId);

    /**
     *  获取外部角色权限信息
     * @param roleOuterId 角色id
     * @return
     */
    public SysRoleInfoVO selectRoleOuterPowerInfo(Long roleOuterId);

    /**
     *  根据 角色id、公司id信息 获取当前角色有多少外部用户
     * @param roleId 角色id
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectOuterPersonnelInfoRoleId(Long roleId, List<String> companyIds);

    /**
     *  人员配置：获取当前角色所拥有的所有公司信息、部门信息、职位信息的用户
     * @param roleId  角色id
     * @return
     */
    PersonnelRoleVO personnelRoleInfo(Long roleId);

    /**
     *  角色-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> personnelRoleInfoPage(RolePersonnelDTO dto);

    /**
     * 角色授权项目、菜单、用户
     * @param dto 角色授权-传入值
     * @return 角色授权-返回值
     */
    Boolean savePermiss(RolePermissDTO dto);

    /**
     *  内部角色-人员配置-分页
     * @param
     * @return
     */
    boolean personnelSave(RolePersonnelDTO dto);

    /**
     * 查询用户-角色权限
     * @param userId
     * @return
     */
    List<BaseSelectVO> selectListByUserId(Long userId);
    /**
     *  角色-人员配置-删除功能
     * @param
     * @return
     */
    boolean personnelRoleInfoDelete(RoleUsersDTO dto);

    /**
     * 根据角色id查询菜单权限信息
     * @param dto
     * @return
     */
    RoleMenuPermissVO selectMenuListByRoleId(UserMenuPermissDTO dto);

    /**
     * 查询角色-项目权限
     * @param roleId
     * @return
     */
    RoleProjectPermissVO selectProjectListByRoleId(Long roleId);

    /**
     *  根据公司获取角色信息
     * @param companyIds 公司数组
     * @return
     */
    List<BaseSelectVO> selectRoleInfoByCompanyIds(List<Long> companyIds);

    /**
     *  角色左侧树状图
     * @return
     */
    List<BaseSelectVO> tree();
}
