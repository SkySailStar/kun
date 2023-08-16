package cn.kun.auth.company.mapper;

import cn.kun.auth.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.company.entity.vo.CompanyPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.company.entity.po.SysCompanyOuter;
import cn.kun.auth.company.entity.vo.CompanyInfoVO;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 外部公司表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysCompanyOuterMapper extends BaseMapper<SysCompanyOuter> {

    /**
     * 查询分页列表
     * @param dto 公司分页列表-传入值
     * @return 分页列表
     */
    List<CompanyPageVO> selectPage(@Param("dto") CompanyPageDTO dto);

    /**
     * 通过外部公司id获取外部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO getCompanyInfoByCompanyId(@Param("companyId") Long companyId);

    /**
     *  查看外部上级公司信息
     * @param loginName 登录名
     * @return
     */
    List<CompanyInfoVO> selectOuterParent(@Param("loginName") String loginName);

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    List<BaseSelectVO> selectCompanyInfoByLoginName(@Param("loginName") String loginName);

    /**
     *  根据用户获取 有角色的公司信息
     * @param loginName 登录名
     * @return
     */
    List<BaseSelectVO> selectRoleInfoByLoginName(@Param("loginName") String loginName);

    /**
     *  根据id获取公司信息
     * @param companyId
     * @return
     */
    BaseSelectVO getCompanyInfoTree(@Param("companyId") Long companyId);

}
