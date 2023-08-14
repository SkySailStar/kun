package cn.kun.auth.system.job.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.auth.system.job.entity.dto.JobAddDTO;
import cn.kun.auth.system.job.entity.dto.JobEditDTO;
import cn.kun.auth.system.job.entity.dto.JobPageDTO;
import cn.kun.auth.system.job.entity.po.SysJobInner;
import cn.kun.auth.system.job.entity.po.SysJobMenuInner;
import cn.kun.auth.system.job.entity.vo.JobDetailVO;
import cn.kun.auth.system.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.system.job.entity.vo.JobMenuPermissVO;
import cn.kun.auth.system.job.entity.vo.JobPageVO;
import cn.kun.auth.system.job.mapper.SysJobInnerMapper;
import cn.kun.auth.system.job.service.SysJobInnerService;
import cn.kun.auth.system.job.service.SysJobMenuInnerService;
import cn.kun.auth.system.user.entity.po.SysUserJobInner;
import cn.kun.auth.system.user.service.SysUserInnerService;
import cn.kun.auth.system.user.service.SysUserJobInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.dept.service.SysDeptInnerService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.system.menu.service.SysMenuService;
import cn.kun.base.api.entity.auth.vo.JobInfoVO;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 内部部门职位表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysJobInnerServiceImpl extends ServiceImpl<SysJobInnerMapper, SysJobInner> implements SysJobInnerService {
    @Resource
    private SysMenuService sysMenuService;
    
    @Resource
    private SysCompanyInnerService sysCompanyInnerService;
    @Resource
    private SysDeptInnerService sysDeptInnerService;
    @Resource
    private SysUserInnerService sysUserInnerService;
    @Resource
    private SysUserJobInnerService sysUserJobInnerService;
    @Resource
    private SysJobMenuInnerService sysJobMenuInnerService;
    @Override
    public Page<JobPageVO> page(JobPageDTO dto) {

        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            dto.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        Page<JobPageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        return baseMapper.selectPage(page, dto);
    }

    @Override
    public JobDetailVO detail(Long id) {
        log.info("进入内部职位详情");
        if(ObjUtil.isEmpty(id)){
            log.warn("内部职位详情id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"职位详情id不能为空");
        }
        // 查询详情
        return baseMapper.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysJobInner add(JobAddDTO dto) {

        log.info("内部职位-开始添加：{}", dto);
        /* 传入值复制到数据库对象 */
        SysJobInner sysJobInner = new SysJobInner();
        BeanUtil.copyProperties(dto,sysJobInner);
        sysJobInner.setCompanyInnerId(dto.getCompanyId());
        sysJobInner.setDeptInnerId(dto.getDeptId());
        // 添加对象
        boolean add = save(sysJobInner);
        if (add) {
            log.info("内部职位-成功添加");
        }
        return sysJobInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysJobInner edit(JobEditDTO dto) {

        log.info("内部职位-开始修改：{}", dto);
        /* 获取数据库对象 */
        SysJobInner sysJobInner = getById(dto.getId());
        if (ObjUtil.isNull(sysJobInner)) {
            log.warn("内部职位-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"职位-修改的数据不存在，无法修改");
        }
        BeanUtil.copyProperties(dto, sysJobInner);
        sysJobInner.setCompanyInnerId(dto.getCompanyId());
        sysJobInner.setDeptInnerId(dto.getDeptId());
        // 修改对象
        boolean edit = updateById(sysJobInner);
        if (edit) {
            log.info("内部职位-成功修改");
        }
        return sysJobInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        log.info("内部职位-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("职位id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "职位id不能为空");
        }
        // 删除内部职位信息
        boolean remove = removeById(id);
        if (remove) {
            log.info("内部职位-成功删除");
        }

        QueryWrapper<SysUserJobInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysUserJobInner::getJobInnerId,id);

        sysUserJobInnerService.remove(queryWrapper);
        log.info("删除职位用户关联关系");

        QueryWrapper<SysJobMenuInner> menuInnerQueryWrapper = new QueryWrapper<>();
        menuInnerQueryWrapper.lambda()
                .eq(SysJobMenuInner::getJobInnerId,id);
        sysJobMenuInnerService.remove(menuInnerQueryWrapper);
        log.info("删除职位菜单关联关系");
    }

    /**
     * 通过内部用户id查询职位、部门、公司信息
     * @param userInnerId
     * @return
     */
    @Override
    public List<JobInfoByUserIdVO> getJobInnerInfoByUserInnerId(Long userInnerId) {
        return baseMapper.getJobInnerInfoByUserInnerId(userInnerId);
    }

    /**
     * 添加用户-选择职位
     * @return
     */
    @Override
    public List<SysJobInner> jobs(Long deptId) {
        log.info("进入添加用户-选择职位");
        String loginName = AuthHelp.getLoginName();
        if(StrUtil.isEmpty(loginName)){
            log.warn("未找到该用户！#{}",loginName);
            throw new BusinessException(ErrorCodeConstants.NULL, "未找到该用户");
        }
        if(ObjUtil.isEmpty(deptId)){
            log.warn("未找到部门id！#{}",deptId);
            throw new BusinessException(ErrorCodeConstants.NULL, "未找到部门id");
        }
//        List<String> companyInfos = new ArrayList<>();
        QueryWrapper<SysJobInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysJobInner::getDeptInnerId,deptId);
//        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
//            // 获取用户所有公司id
//            companyInfos = sysUserInnerService.selectCompanyInfoLoginName(loginName);
//            if(CollectionUtil.isEmpty(companyInfos)){
//                log.warn("该用户没有公司信息！#{}",companyInfos);
//                throw new BusinessException(ErrorCodeConstants.NULL, "该用户没有公司信息");
//            }

//            List<SysJobInner> list = list(queryWrapper);

//            return list(queryWrapper);
//        }
//        return baseMapper.jobs(companyInfos);
        return list(queryWrapper);
    }

    /**
     *  通过公司、部门查询所有职位
     * @param companyIds
     * @return
     */
    @Override
    public List<BaseSelectVO> selectJobs(List<Long> companyIds){
        return baseMapper.selectJobs(companyIds);
    }

    /**
     * 查询职位授权的菜单权限
     * @param dto
     * @return
     */
    @Override
    public JobMenuPermissVO selectMenuListByJobId(UserMenuPermissDTO dto) {

        log.info("进入获取菜单列表信息");
        JobMenuPermissVO vo = new JobMenuPermissVO();
        List<BaseSelectVO> loginUserVo = null;
        // 当前登录用户传入值
        UserMenuPermissDTO userMenuPermissDTO = new UserMenuPermissDTO();
        userMenuPermissDTO.setUserId(null);
        userMenuPermissDTO.setServiceCode(dto.getServiceCode());

        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }
        else{
            userMenuPermissDTO.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }

        // 当前授权角色-菜单查询
        List<BaseSelectVO> jobVo = sysMenuService.selectListByJobInnerId(dto);

        vo.setLoginUserVo(loginUserVo);
        vo.setJobVo(jobVo);
        return vo;
    }

    /**
     * 根据公司id获取所有职位信息
     *
     * @param companyId
     * @return
     */
    @Override
    public List<JobInfoVO> selectJobInfoByCompanyId(Long companyId) {
        return baseMapper.selectJobInfoByCompanyId(companyId);
    }

    /**
     *  部门左侧树状图
     * @return
     */
    @Override
    public List<BaseSelectVO> tree() {
        log.info("进入内部部门树状图");
        List<Long> companyIds = new ArrayList<>();

        List<BaseSelectVO> selectDeptByCompanyIds = null; // 部门返回值
        List<BaseSelectVO> companyList = null; // 公司返回值
        // 如果是超级管理员走单独逻辑
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            companyList = sysCompanyInnerService.selectCompanyInfoByLoginName(null);
            // 循环添加公司id
            for (BaseSelectVO personnelVO : companyList) {
                companyIds.add(Long.parseLong(personnelVO.getValue()));
            }
            selectDeptByCompanyIds = sysDeptInnerService.selectDeptByCompanyIds(null);
            selectDeptByCompanyIds.addAll(companyList);
        } else {
            // 获取当前登陆人用户的所有公司信息
            companyList = sysCompanyInnerService.selectInnerParent();
            selectDeptByCompanyIds = sysDeptInnerService.selectDeptByCompanyIds(companyIds);
            selectDeptByCompanyIds.addAll(companyList);
        }
        List<BaseSelectVO> selectJobs = selectJobs(companyIds);
        selectDeptByCompanyIds.addAll(selectJobs);
        return selectDeptByCompanyIds;
    }
}
