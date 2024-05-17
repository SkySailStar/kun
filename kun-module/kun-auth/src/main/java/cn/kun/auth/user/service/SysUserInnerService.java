package cn.kun.auth.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sevnce.auth.menu.entity.dto.UserMenuPermissDTO;
import com.sevnce.auth.user.entity.dto.UserAddDTO;
import com.sevnce.auth.user.entity.dto.UserBlackListDTO;
import com.sevnce.auth.user.entity.dto.UserEditDTO;
import com.sevnce.auth.user.entity.dto.UserPageDTO;
import com.sevnce.auth.user.entity.dto.UserPasswordDTO;
import com.sevnce.auth.user.entity.dto.UserPermissDTO;
import com.sevnce.auth.user.entity.po.SysUserInner;
import com.sevnce.auth.user.entity.po.UserDetailList;
import com.sevnce.auth.user.entity.vo.UserDetailListVO;
import com.sevnce.auth.user.entity.vo.UserJobInfoVO;
import com.sevnce.auth.user.entity.vo.UserMenuPermissVO;
import com.sevnce.auth.user.entity.vo.UserPageVO;
import com.sevnce.auth.user.entity.vo.UserProjectPermissVO;
import com.sevnce.auth.user.entity.vo.UserRolePermissVO;
import com.sevnce.base.api.entity.auth.dto.UserListDTO;
import com.sevnce.base.api.entity.auth.vo.SysUserCacheInfoVO;
import com.sevnce.base.api.entity.auth.vo.UserDetailVO;
import com.sevnce.base.api.entity.auth.vo.UserRedisVO;
import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import java.util.List;


/**
 * <p>
 * 内部用户表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserInnerService extends IService<SysUserInner> {

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
     * @param id 登录名
     * @return 用户详情-返回值
     */
    UserDetailVO detail(String id);

    /**
     * 添加
     * @param dto 用户添加-传入值
     * @return 用户详情对象
     */
    SysUserInner add(UserAddDTO dto);

    /**
     * 修改
     * @param dto 用户详情修改-传入值
     * @return 用户详情对象
     */
    SysUserInner edit(UserEditDTO dto);

    /**
     *  删除用户接口
     * @param id id
     * @return
     */
    void remove(Long id);

    /**
     * 通过内部用户id查询用户缓存信息
     * @param userId
     * @return
     */
    SysUserCacheInfoVO getUserCacheInfoByUserId(Long userId);

    /**
     * 通过内部用户id查询用户缓存信息并保存到缓存中
     * @param userId
     * @return
     */
    SysUserCacheInfoVO selectUserInnerPowerInfo(Long userId);

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param companyIds  公司id
     * @return
     */
    List<UserDetailList> selectInnerPersonnelInfo(List<String> companyIds);


    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param projectNo  项目编号
     * @return
     */
    UserDetailListVO personnelDetail(String projectNo);

    /**
     *  人员配置：获取当前用户所拥有的所有公司id
     * @param loginName  登录名
     * @return
     */
    List<String> selectCompanyInfoLoginName(String loginName);

    /**
     * 用户授权项目、菜单、角色
     * @param dto 用户授权-传入值
     * @return 用户授权-返回值
     */
    Boolean savePermiss(UserPermissDTO dto);

    /**
     * 根据用户id查询用户菜单授权列表信息
     * @param dto
     * @return
     */
    UserMenuPermissVO selectMenuListByUserId(UserMenuPermissDTO dto);


    /**
     * 查询用户-角色权限
     * @param userId
     * @return
     */
    UserRolePermissVO selectRoleListByUserId(Long userId);

    /**
     * 查询用户的项目权限
     * @return
     */
    UserProjectPermissVO selectProjectListByUserId(Long userId);

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
     *  根据用户id获取职位、部门、公司信息
     * @param userId 用户id
     * @return
     */
    UserJobInfoVO getUserJobInfo(Long userId);

    /**
     * 根据项目编号获取项目下的所有内部用户
     *
     * @param projectNo
     * @return
     */
    List<UserRedisVO> selectUserInnerByProjectNo(String projectNo);

    /**
     * 通过内部角色id查询当前角色是否绑定用户
     * @param roleInnerId
     * @return
     */
    List<Long> selectUserIdsByRoleInnerId(Long roleInnerId);

    /**
     *  修改用户密码
     * @param dto
     * @return
     */
    Boolean updateUserPassword(UserPasswordDTO dto);

    /**
     * 加入、移除黑名单
     * @param dto
     * @return
     */
    Boolean edit(UserBlackListDTO dto);

    /**
     * 用户注销
     * @param dto
     * @return
     */
    Boolean logOff(UserBlackListDTO dto);

}
