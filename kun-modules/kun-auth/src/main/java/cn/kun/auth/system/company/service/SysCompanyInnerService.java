package cn.kun.auth.system.company.service;

import cn.kun.auth.system.company.entity.dto.CompanyAddDTO;
import cn.kun.auth.system.company.entity.dto.CompanyEditDTO;
import cn.kun.auth.system.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.system.company.entity.po.SysCompanyInner;
import cn.kun.auth.system.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;


/**
 * <p>
 * 内部公司表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:4
 */
public interface SysCompanyInnerService extends IService<SysCompanyInner> {
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
    SysCompanyInner add(CompanyAddDTO dto);

    /**
     * 修改
     * @param dto 公司修改-传入值
     * @return 公司对象-返回值
     */
    SysCompanyInner edit(CompanyEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 通过内部公司id查询内部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO getCompanyCacheInfoByCompanyId(Long companyId);

    /**
     * 通过内部公司id查询内部公司缓存信息
     * @param companyId
     * @return
     */
    SysCompanyInfoVO selectCompanyPowerInfo(Long companyId);

    /**
     *  查看内部上级公司信息
     * @return
     */
    List<BaseSelectVO> selectInnerParent();

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
