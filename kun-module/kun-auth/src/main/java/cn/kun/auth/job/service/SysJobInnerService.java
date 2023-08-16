package cn.kun.auth.job.service;

import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.job.entity.po.SysJobInner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.job.entity.dto.JobAddDTO;
import cn.kun.auth.job.entity.dto.JobEditDTO;
import cn.kun.auth.job.entity.dto.JobPageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.auth.job.entity.vo.JobDetailVO;
import cn.kun.auth.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.job.entity.vo.JobMenuPermissVO;
import cn.kun.auth.job.entity.vo.JobPageVO;
import cn.kun.base.api.entity.auth.vo.JobInfoVO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * <p>
 * 内部部门职位表 服务类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
public interface SysJobInnerService extends IService<SysJobInner> {
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
    SysJobInner add(JobAddDTO dto);

    /**
     * 修改
     * @param dto 职位修改-传入值
     * @return 职位对象-返回值
     */
    SysJobInner edit(JobEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 通过内部用户id查询职位、部门、公司信息
     * @param userInnerId
     * @return
     */
    List<JobInfoByUserIdVO> getJobInnerInfoByUserInnerId(Long userInnerId);

    /**
     *  添加用户-选择职位
     * @return
     */
    List<SysJobInner> jobs(Long deptId);

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
