package cn.kun.auth.company.service;

import cn.kun.auth.company.entity.dto.CompanyAddDTO;
import cn.kun.auth.company.entity.dto.CompanyEditDTO;
import cn.kun.auth.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.company.entity.vo.CompanyPageVO;
import cn.kun.auth.company.entity.po.SysCompanyOuter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 外部公司表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysCompanyOuterService extends IService<SysCompanyOuter> {
    /**
     * 分页列表
     * @param dto 公司分页-传入值
     * @return 公司分页列表-返回值
     */
    Page<CompanyPageVO> page(CompanyPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 公司详情-返回值
     */
    CompanyDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 公司添加-传入值
     * @return 公司对象-返回值
     */
    SysCompanyOuter add(CompanyAddDTO dto);

    /**
     * 修改
     * @param dto 公司修改-传入值
     * @return 公司对象-返回值
     */
    SysCompanyOuter edit(CompanyEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);


    /**
     * 通过外部公司id查询外部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO getCompanyCacheInfoByCompanyId(Long companyId);

    /**
     * 通过外部公司id查询内部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO selectCompanyOuterPowerInfo(Long companyId);


    /**
     *  查看用户所拥有的公司信息
     * @param
     * @return
     */
    List<BaseSelectVO> selectOuterParent();

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    List<BaseSelectVO> selectCompanyInfoByLoginName(String loginName);

    /**
     *  根据id获取公司信息
     * @param companyId
     * @return
     */
    BaseSelectVO getCompanyInfoTree(Long companyId);

    /**
     *  根据用户获取 有角色的公司信息
     * @param loginName 登录名
     * @return
     */
    List<BaseSelectVO> selectRoleInfoByLoginName(String loginName);
}
