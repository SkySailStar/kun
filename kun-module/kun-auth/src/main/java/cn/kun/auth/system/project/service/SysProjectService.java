package cn.kun.auth.system.project.service;

import cn.kun.auth.system.project.entity.dto.DeletePersonnelDTO;
import cn.kun.auth.system.project.entity.dto.ProjectAddDTO;
import cn.kun.auth.system.project.entity.dto.ProjectEditDTO;
import cn.kun.auth.system.project.entity.dto.ProjectOaSaveDTO;
import cn.kun.auth.system.project.entity.dto.ProjectPageDTO;
import cn.kun.auth.system.project.entity.dto.SavePersonnelDTO;
import cn.kun.auth.system.project.entity.po.SysProject;
import cn.kun.auth.system.project.entity.vo.PersonnelDetailVO;
import cn.kun.auth.system.project.entity.vo.ProjectPageVO;
import cn.kun.auth.system.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.system.user.entity.dto.UserPersonnelDTO;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.UserPersonnelPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.api.entity.auth.dto.ProjectNoListDTO;
import cn.kun.base.api.entity.auth.dto.UserDTO;
import cn.kun.base.api.entity.auth.vo.ProjectDetailVO;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysProjectService extends IService<SysProject> {
    /**
     * 分页列表
     * @param dto 项目分页列表-传入值
     * @return 项目分页列表-返回值
     */
    Page<ProjectPageVO> page(ProjectPageDTO dto);

    /**
     * 详情
     * @param projectNo 项目编号
     * @return 项目详情-返回值
     */
    ProjectDetailVO detail(String projectNo);

    /**
     * 添加
     * @param dto 项目添加-传入值
     * @return 项目对象-返回值
     */
    SysProject add(ProjectAddDTO dto);

    /**
     * 修改
     * @param dto 项目修改-传入值
     * @return 项目对象-返回值
     */
    SysProject edit(ProjectEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 内部用户id查询项目id
     * @param userInnerId
     * @return
     */
    List<ProjectParentVO> selectProjectNoByUserInnerId(Long userInnerId);

    /**
     * 外部用户id查询项目id
     * @param userOuterId
     * @return
     */
    List<ProjectParentVO> selectProjectNoByUserOuterId(Long userOuterId);


    /**
     * 通过项目id获取项目信息
     * @param projectNo
     * @return
     */
    SysProjectInfoVO getProjectInfoByProjectNo(String projectNo);



    /**
     * 通过内部角色id查询项目权限
     * @param roleInnerId
     * @return
     */
    List<ProjectRedisVO> getRoleProjectInnerAuth(Long roleInnerId);

    /**
     * 通过外部角色id查询项目权限
     * @param roleOuterId
     * @return
     */
    List<ProjectRedisVO> getRoleProjectOuterAuth(Long roleOuterId);


    /**
     * 保存项目OA
     * @param dto
     * @return
     */
    SysProject save(ProjectOaSaveDTO dto);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectInnerPersonnelInfoProjectNo(String projectNo, List<String> companyIds);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectOuterPersonnelInfoProjectNo(String projectNo, List<String> companyIds);

    /**
     *  项目-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> personnelPage(UserPersonnelDTO dto);


    /**
     *  获取项目人员配置树状图
     * @param projectNo
     * @return
     */
    PersonnelDetailVO personnel(String projectNo);

    /**
     *  获取人员配置树状图
     * @return
     */
    PersonnelDetailVO personnelTree();

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    List<UserDetailList> innerPersonnelProject(String projectNo,Long companyId);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    List<UserDetailList> outerPersonnelProject(String projectNo,Long companyId);

    /**
     *  项目-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> personnelOuterPage(UserPersonnelDTO dto);

    /**
     *  项目-人员配置保存
     * @param
     * @return
     */
    boolean personnelSave(SavePersonnelDTO dto);

    /**
     *  项目-内部人员配置删除
     * @param
     * @return
     */
    boolean personnelInnerDelete(DeletePersonnelDTO dto);

    /**
     *  项目-外部人员配置删除
     * @param
     * @return
     */
    boolean personnelOuterDelete(DeletePersonnelDTO dto);

    /**
     *  添加项目-获取当前登陆人用户所有项目信息
     * @return
     */
    List<BaseSelectVO> parentList();

    /**
     * 获取当前登陆用户所有项目编号
     * @param characteristic 内外部标识
     * @param userId 用户ID
     * @param loginName 登录名
     * @return 项目编码列表
     */
    List<String> selectProjectNo(Boolean characteristic, Long userId, String loginName);

    /**
     *  添加项目-获取用户所有项目信息
     * @return
     */
    List<ProjectParentVO> projectInfo(UserDTO dto);


    /**
     * 查询内部角色-项目权限
     * @param roleInnerId
     * @return
     */
    List<ProjectParentVO> selectListByRoleInnerId(Long roleInnerId);


    /**
     * 查询外部角色-项目权限
     * @param roleOuterId
     * @return
     */
    List<ProjectParentVO> selectListByRoleOuterId(Long roleOuterId);

    /**
     * 通过项目编号列表查询项目信息
     * @param dto
     * @return
     */
    List<SysProjectInfoVO> queryListByProjectNos(ProjectNoListDTO dto);


}
