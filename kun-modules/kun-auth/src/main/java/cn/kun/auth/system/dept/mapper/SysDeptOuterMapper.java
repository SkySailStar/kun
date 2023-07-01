package cn.kun.auth.system.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptOuter;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
import cn.kun.base.api.entity.auth.vo.DeptInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 外部公司部门表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysDeptOuterMapper extends BaseMapper<SysDeptOuter> {
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 部门分页列表-传入值
     * @return 分页列表
     */
    List<DeptPageVO> selectPage( @Param("dto") DeptPageDTO dto);

    /**
     * 通过公司id获取公司部门信息
     *
     * @param companyId 公司id
     * @return
     */
    List<DeptInfoVO> selectDeptCacheInfoByCompanyId(@Param("companyId") Long companyId);


    /**
     *  部门详情
     * @param id 部门id
     * @return
     */
    DeptDetailVO detail(@Param("id")Long id);

    /**
     *  获取所有部门
     * @param companyIds  公司id
     * @return
     */
    List<BaseSelectVO> selectDeptByCompanyIds(@Param("companyIds")List<Long> companyIds);
}
