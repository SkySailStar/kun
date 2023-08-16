package cn.kun.auth.role.mapper;

import cn.kun.auth.user.entity.po.UserDetailList;
import cn.kun.auth.user.entity.vo.UserPersonnelPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.role.entity.dto.RolePageDTO;
import cn.kun.auth.role.entity.dto.RolePersonnelDTO;
import cn.kun.auth.role.entity.po.SysRoleInner;
import cn.kun.auth.role.entity.vo.RoleDetailVO;
import cn.kun.auth.role.entity.vo.RolePageVO;
import cn.kun.base.api.entity.auth.vo.RoleInfoByUserIdVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 内部角色表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysRoleInnerMapper extends BaseMapper<SysRoleInner> {

    RoleDetailVO detail(@Param("id")Long id);
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 角色分页列表-传入值
     * @return 分页列表
     */
    Page<RolePageVO> selectPage(Page<RolePageDTO> page, @Param("dto") RolePageDTO dto);

    /**
     * 通过内部用户id查询角色信息
     * @param userInnerId
     * @return
     */
    List<RoleInfoByUserIdVO> getRoleInnerInfoByUserInnerId(@Param("userInnerId") Long userInnerId);

    /**
     *  根据 角色id、公司id信息 获取当前角色有多少内部用户
     * @param roleId 角色id
     * @param companyIds 公司id
     * @return
     */
    List<UserDetailList> selectInnerPersonnelInfoRoleId(@Param("roleId")Long roleId, @Param("companyIds")List<String> companyIds);

    /**
     *  项目-人员配置-分页功能
     * @param dto
     * @return
     */
    Page<UserPersonnelPageVO> personnelRoleInfoPage(Page<UserPersonnelPageVO> page,
                                                    @Param("dto") RolePersonnelDTO dto,
                                                    @Param("loginName")String loginName);

    /**
     * 查询用户-角色权限
     * @param userId
     * @return
     */
    List<BaseSelectVO> selectListByUserId(@Param("userId") Long userId);


    /**
     *  根据公司获取角色信息
     * @param companyIds 公司数组
     * @return
     */
    List<BaseSelectVO> selectRoleInfoByCompanyIds(@Param("companyIds") List<Long> companyIds);
}
