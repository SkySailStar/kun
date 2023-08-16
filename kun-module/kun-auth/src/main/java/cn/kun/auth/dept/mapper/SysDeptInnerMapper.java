package cn.kun.auth.dept.mapper;

import cn.kun.auth.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.dept.entity.po.SysDeptInner;
import cn.kun.auth.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.dept.entity.vo.DeptPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.base.api.entity.auth.vo.DeptInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 内部公司部门表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysDeptInnerMapper extends BaseMapper<SysDeptInner> {
    /**
     * 查询分页列表
     * @param dto 部门分页列表-传入值
     * @return 分页列表
     */
    List<DeptPageVO> selectPage(@Param("dto") DeptPageDTO dto);

    /**
     * 通过公司id查询部门信息
     * @param companyId 公司id
     * @return
     */
    List<DeptInfoVO> selectDeptCacheInfoByCompanyId(@Param("companyId")Long companyId);

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
