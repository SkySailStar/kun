package cn.kun.auth.system.job.mapper;

import cn.kun.auth.system.job.entity.po.SysJobOuter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.job.entity.dto.JobPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.system.job.entity.vo.JobDetailVO;
import cn.kun.auth.system.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.system.job.entity.vo.JobPageVO;
import cn.kun.base.api.entity.auth.vo.JobInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 外部部门职位表 Mapper 接口
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysJobOuterMapper extends BaseMapper<SysJobOuter> {
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 职位分页列表-传入值
     * @return 分页列表
     */
    Page<JobPageVO> selectPage(Page<JobPageDTO> page, @Param("dto") JobPageDTO dto);

    /**
     * 通过用户id查询职位、部门、公司信息
     * @param userId
     * @return
     */
    List<JobInfoByUserIdVO> getJobOuterInfoByUserOuterId(@Param("userId") Long userId);

    /**
     *  详情
     * @param id 职位id
     * @return
     */
    JobDetailVO detail(@Param("id") Long id);

    /**
     *  添加用户-选择职位
     * @return
     */
    List<JobDetailVO> jobs(@Param("companyIds") List<String> companyIds);

    /**
     *  通过公司、部门查询所有职位
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectJobs(@Param("companyIds") List<Long> companyIds);

    /**
     *  根据公司id获取所有职位信息
     * @param companyId
     * @return
     */
    List<JobInfoVO> selectJobInfoByCompanyId(@Param("companyId") Long companyId);
}
