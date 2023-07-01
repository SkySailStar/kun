package cn.kun.auth.system.project.mapper;

import cn.kun.auth.system.project.entity.dto.ProjectPageDTO;
import cn.kun.auth.system.project.entity.po.SysProject;
import cn.kun.auth.system.project.entity.vo.ProjectPageVO;
import cn.kun.auth.system.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.system.user.entity.dto.UserPersonnelDTO;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.UserPersonnelPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysProjectMapper extends BaseMapper<SysProject> {
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 项目分页列表-传入值
     * @return 分页列表
     */
    List<ProjectPageVO> selectInnerPage(@Param("dto") ProjectPageDTO dto);

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 项目分页列表-传入值
     * @return 分页列表
     */
    List<ProjectPageVO> selectOuterPage( @Param("dto") ProjectPageDTO dto);

    /**
     * 通过项目id查询项目详细信息
     * @param projectNo
     * @return
     */
    public SysProjectInfoVO getProjectInfoByProjectNo(@Param("projectNo") String projectNo);

    /**
     * 通过内部角色id查询项目权限
     * @param roleInnerId
     * @return
     */
    List<ProjectRedisVO> getRoleProjectInnerAuth(@Param("roleInnerId") Long roleInnerId);

    /**
     * 通过外部角色id查询项目权限
     * @param roleOuterId
     * @return
     */
    List<ProjectRedisVO> getRoleProjectOuterAuth(@Param("roleOuterId") Long roleOuterId);


    /**
     *  根据内部用户获取当前用户所有项目信息
     * @param userInnerId  内部用户id
     * @return 数组
     */
    List<ProjectParentVO> selectProjectNoByUserInnerId(@Param("userInnerId")Long userInnerId);

    /**
     *  根据外部用户获取当前用户所有项目信息
     * @param userOuterId  外部用户id
     * @return 数组
     */
    List<ProjectParentVO> selectProjectNoByUserOuterId(@Param("userOuterId")Long userOuterId);


    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectInnerPersonnelInfoProjectNo(@Param("projectNo")String projectNo,
                                                           @Param("companyIds")List<String> companyIds);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectOuterPersonnelInfoProjectNo(@Param("projectNo")String projectNo,
                                                           @Param("companyIds")List<String> companyIds);

    /**
     *  项目-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> selectInnerPersonnelInfoPage(Page<UserPersonnelPageVO> page,
                                                           @Param("dto") UserPersonnelDTO dto,
                                                           @Param("loginName")String loginName);

    /**
     *  项目-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> outerPersonnelPage(Page<UserPersonnelPageVO> page,
                                                            @Param("dto") UserPersonnelDTO dto,
                                                            @Param("loginName")String loginName);


    /**
     * 查询内部角色-项目权限
     * @param roleInnerId
     * @return
     */
    List<ProjectParentVO> selectListByRoleInnerId(@Param("roleInnerId")Long roleInnerId);

    /**
     * 查询外部角色-项目权限
     * @param roleOuterId
     * @return
     */
    List<ProjectParentVO> selectListByRoleOuterId(@Param("roleOuterId")Long roleOuterId);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    List<UserDetailList> innerPersonnelProject(@Param("projectNo")String projectNo, @Param("companyId")Long companyId);

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    List<UserDetailList> outerPersonnelProject(@Param("projectNo")String projectNo, @Param("companyId")Long companyId);

    /**
     * 通过项目编号列表查询项目信息
     * @param projectNoList
     * @return
     */
    List<SysProjectInfoVO>selectInfoByProjectNoList(@Param("projectNoList") List<String> projectNoList);
}
