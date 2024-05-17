package cn.kun.auth.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sevnce.auth.user.entity.dto.UserPageDTO;
import com.sevnce.auth.user.entity.po.SysUserOuter;
import com.sevnce.auth.user.entity.po.UserDetailList;
import com.sevnce.auth.user.entity.vo.UserJobInfoVO;
import com.sevnce.auth.user.entity.vo.UserPageVO;
import com.sevnce.base.api.entity.auth.dto.UserListDTO;
import com.sevnce.base.api.entity.auth.vo.SysUserInfoVO;
import com.sevnce.base.api.entity.auth.vo.UserDetailVO;
import com.sevnce.base.api.entity.auth.vo.UserRedisVO;
import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 外部用户表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysUserOuterMapper extends BaseMapper<SysUserOuter> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 用户分页列表-传入值
     * @return 分页列表
     */
    Page<UserPageVO> selectPage(Page<UserPageDTO> page, @Param("dto") UserPageDTO dto);

    /**
     * 根据用户id集合获取用户信息
     * @param dto 用户id集合
     * @return
     */
    List<UserDetailVO> list(@Param("dto") UserListDTO dto);

    /**
     * 通过用户id查询用户缓存信息
     * @param userId
     * @return
     */
    SysUserInfoVO getUserInfoByUserId(@Param("userId") Long userId);

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的外部用户
     * @param companyIds  公司id
     * @return
     */
    List<UserDetailList> selectOuterPersonnelInfo(@Param("companyIds") List<String> companyIds);

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    List<String> selectOuterCompanyInfoLoginName(@Param("loginName")String loginName);

    /**
     *  根据公司id获取用户信息
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectUserInfoByCompanyId(@Param("companyIds") List<Long> companyIds);


    /**
     *  根据公司id获取用户信息
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectUserRoleByCompanyIds(@Param("companyIds") List<Long> companyIds);

    /**
     *  根据用户id获取职位、部门、公司信息
     * @param userId 用户id
     * @return
     */
    UserJobInfoVO getUserJobInfo(@Param("userId") Long userId);



    /**
     *  根据项目编号获取项目下的所有外部用户
     * @param projectNo
     * @return
     */
    List<UserRedisVO> selectUserOuterByProjectNo(@Param("projectNo") String projectNo);

    /**
     * 通过外部角色id查询当前角色是否绑定用户
     * @param roleOuterId
     * @return
     */
    List<Long> selectUserIdsByRoleOuterId(@Param("roleOuterId") Long roleOuterId);
}
