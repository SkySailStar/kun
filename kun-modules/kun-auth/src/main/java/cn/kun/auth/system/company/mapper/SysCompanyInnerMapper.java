package cn.kun.auth.system.company.mapper;

import cn.kun.auth.system.company.entity.dto.CompanyPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.system.company.entity.po.SysCompanyInner;
import cn.kun.auth.system.company.entity.vo.CompanyInfoVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * <p>
 * 内部公司表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysCompanyInnerMapper extends BaseMapper<SysCompanyInner> {
    /**
     * 查询分页列表
     * @param dto 公司分页列表-传入值
     * @return 分页列表
     */
    List<CompanyPageVO> selectPage(@Param("dto") CompanyPageDTO dto);

    /**
     * 通过内部公司id获取内部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO getCompanyInfoByCompanyId(@Param("companyId") Long companyId);

    /**
     *  查看内部上级公司信息
     * @param loginName 登录名
     * @return
     */
    List<CompanyInfoVO> selectInnerParent(@Param("loginName") String loginName);

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    List<BaseSelectVO> selectCompanyInfoByLoginName(@Param("loginName") String loginName);


    /**
     *  根据id获取公司信息
     * @param companyId
     * @return
     */
    BaseSelectVO getCompanyInfoTree(@Param("companyId") Long companyId);

    /**
     *  根据用户获取 有角色的公司信息
     * @param loginName 登录名
     * @return
     */
    List<BaseSelectVO> selectRoleInfoByLoginName(@Param("loginName") String loginName);
}
