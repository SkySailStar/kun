package cn.kun.auth.system.dept.service;

import cn.kun.auth.system.dept.entity.dto.DeptEditDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.auth.system.dept.entity.dto.DeptAddDTO;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptInner;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
import cn.kun.base.api.entity.auth.vo.DeptInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 内部公司部门表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysDeptInnerService extends IService<SysDeptInner> {
    /**
     * 分页列表
     * @param dto 部门分页列表-传入值
     * @return 部门分页列表-返回值
     */
    Page<DeptPageVO> page(DeptPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 部门详情-返回值
     */
    DeptDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 部门添加-传入值
     * @return 部门对象-返回值
     */
    SysDeptInner add(DeptAddDTO dto);

    /**
     * 修改
     * @param dto 部门修改-传入值
     * @return 部门对象-返回值
     */
    SysDeptInner edit(DeptEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);


    /**
     * 通过公司id查询部门信息
     * @param companyId 公司id
     * @return
     */
    List<DeptInfoVO> selectDeptCacheInfoByCompanyId(Long companyId);

    /**
     *  查看内部上级部门信息
     * @param  companyId 公司id
     * @return
     */
    List<BaseSelectVO> selectInnerParent(Long companyId);

    /**
     *  获取所有部门
     * @param companyIds  公司id
     * @return
     */
    List<BaseSelectVO> selectDeptByCompanyIds(List<Long> companyIds);

    /**
     *  部门左侧树状图
     * @return
     */
    List<BaseSelectVO> tree();
}
