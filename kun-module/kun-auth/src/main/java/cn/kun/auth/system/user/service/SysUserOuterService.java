package cn.kun.auth.system.user.service;

import cn.kun.auth.system.user.entity.dto.UserAddDTO;
import cn.kun.auth.system.user.entity.dto.UserEditDTO;
import cn.kun.auth.system.user.entity.dto.UserPageDTO;
import cn.kun.auth.system.user.entity.dto.UserPasswordDTO;
import cn.kun.auth.system.user.entity.dto.UserPermissDTO;
import cn.kun.auth.system.user.entity.po.SysUserOuter;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.UserDetailListVO;
import cn.kun.auth.system.user.entity.vo.UserJobInfoVO;
import cn.kun.auth.system.user.entity.vo.UserMenuPermissVO;
import cn.kun.auth.system.user.entity.vo.UserPageVO;
import cn.kun.auth.system.user.entity.vo.UserProjectPermissVO;
import cn.kun.auth.system.user.entity.vo.UserRolePermissVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.base.api.entity.auth.dto.UserListDTO;
import cn.kun.base.api.entity.auth.vo.SysUserCacheInfoVO;
import cn.kun.base.api.entity.auth.vo.UserDetailVO;
import cn.kun.base.api.entity.auth.vo.UserRedisVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 外部用户表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserOuterService extends IService<SysUserOuter> {
    /**
     * 分页列表
     * @param dto 用户分页列表-传入值
     * @return 用户分页列表-返回值
     */
    Page<UserPageVO> page(UserPageDTO dto);

    /**
     * 根据用户id集合获取用户信息
     * @param dto 用户id集合
     * @return
     */
    List<UserDetailVO> list(UserListDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 用户详情-返回值
     */
    UserDetailVO detail(String id);

    /**
     * 添加
     * @param dto 用户添加-传入值
     * @return 用户详情对象
     */
    SysUserOuter add(UserAddDTO dto);

    /**
     * 修改
     * @param dto 用户详情修改-传入值
     * @return 用户详情对象
     */
    SysUserOuter edit(UserEditDTO dto);

    /**
     *  删除用户接口
     * @param id id
     * @return
     */
    void remove(Long id);

    /**
     * 通过用户id查询用户缓存信息
     * @param userId
     * @return
     */
    SysUserCacheInfoVO getUserCacheInfoByUserId(Long userId);

    /**
     * 获取内部用户所有权限信息并存储缓存
     *
     * @param userId 用户id
     * @return
     */
    SysUserCacheInfoVO selectUserOuterPowerInfo(Long userId);

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param companyIds  公司id
     * @return
     */
    List<UserDetailList> selectOuterPersonnelInfo(List<String> companyIds);


    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息、部门信息、职位信息的外部用户
     * @param projectNo  项目编号
     * @return
     */
    UserDetailListVO personnelDetail(String projectNo);

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    List<String> selectOuterCompanyInfoLoginName(String loginName);

    /**
     * 用户授权项目、菜单、角色
     * @param dto 用户授权-传入值
     * @return 用户授权-返回值
     */
    Boolean savePermiss(UserPermissDTO dto);

    /**
     *  根据公司id获取用户信息
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectUserInfoByCompanyId(List<Long> companyIds);

    /**
     *  根据公司id获取用户信息-角色树状图
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectUserRoleByCompanyIds(List<Long> companyIds);

    /**
     * 查询用户-菜单权限列表
     * @param dto
     * @return
     */
    UserMenuPermissVO selectMenuListByUserId(UserMenuPermissDTO dto);

    /**
     * 查询当前用户的项目权限
     * @return
     */
    UserProjectPermissVO selectProjectListByUserId(Long userId);

    /**
     * 查询用户-角色权限
     * @param userId
     * @return
     */
    UserRolePermissVO selectRoleListByUserId(Long userId);



    /**
     *  根据用户id获取职位、部门、公司信息
     * @param userId 用户id
     * @return
     */
    UserJobInfoVO getUserJobInfo(Long userId);


    /**
     * 根据项目编号获取项目下的所有外部用户
     *
     * @param projectNo
     * @return
     */
    List<UserRedisVO> selectUserOuterByProjectNo(String projectNo);

    /**
     * 通过外部角色id查询当前角色是否绑定用户
     * @param roleOuterId
     * @return
     */
    List<Long> selectUserIdsByRoleOuterId(Long roleOuterId);

    /**
     *  修改用户密码
     * @param dto
     * @return
     */
    Boolean updateUserPassword(UserPasswordDTO dto);
}
