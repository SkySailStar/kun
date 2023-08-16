package cn.kun.auth.job.service;

import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.job.entity.dto.JobAddDTO;
import cn.kun.auth.job.entity.dto.JobEditDTO;
import cn.kun.auth.job.entity.dto.JobPageDTO;
import cn.kun.auth.job.entity.po.SysJobOuter;
import cn.kun.auth.job.entity.vo.JobDetailVO;
import cn.kun.auth.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.job.entity.vo.JobMenuPermissVO;
import cn.kun.auth.job.entity.vo.JobPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.api.entity.auth.vo.JobInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 外部部门职位表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysJobOuterService extends IService<SysJobOuter> {
    /**
     * 分页列表
     * @param dto 职位分页列表-传入值
     * @return 职位分页列表-返回值
     */
    Page<JobPageVO> page(JobPageDTO dto);

    JobDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 职位添加-传入值
     * @return 职位对象-返回值
     */
    SysJobOuter add(JobAddDTO dto);

    /**
     * 修改
     * @param dto 职位修改-传入值
     * @return 职位对象-返回值
     */
    SysJobOuter edit(JobEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);
    /**
     * 通过外部用户id查询职位、部门、公司信息
     * @param userId
     * @return
     */
    List<JobInfoByUserIdVO> getJobOuterInfoByUserOuterId(Long userId);

    /**
     *  添加用户-选择职位
     * @return
     */
    List<SysJobOuter> jobs(Long id);


    /**
     *  通过公司、部门查询所有职位
     * @param companyIds
     * @return
     */
    List<BaseSelectVO> selectJobs(List<Long> companyIds);

    /**
     * 查询职位授权的菜单权限
     * @return
     */
    JobMenuPermissVO selectMenuListByJobId(UserMenuPermissDTO dto);

    /**
     * 根据公司id获取所有职位信息
     *
     * @param companyId
     * @return
     */
    List<JobInfoVO> selectJobInfoByCompanyId(Long companyId);

    /**
     *  职位左侧树状图
     * @return
     */
    List<BaseSelectVO> tree();
}
