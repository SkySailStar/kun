package cn.kun.auth.system.company.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.company.entity.AuthCompanyInfo;
import cn.kun.auth.system.company.entity.dto.CompanyAddDTO;
import cn.kun.auth.system.company.entity.dto.CompanyEditDTO;
import cn.kun.auth.system.company.entity.dto.CompanyPageDTO;
import cn.kun.auth.system.company.entity.po.SysCompanyDetailOuter;
import cn.kun.auth.system.company.entity.po.SysCompanyOuter;
import cn.kun.auth.system.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.system.company.entity.vo.CompanyInfoVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import cn.kun.auth.system.company.mapper.SysCompanyOuterMapper;
import cn.kun.auth.system.company.service.SysCompanyDetailOuterService;
import cn.kun.auth.system.company.service.SysCompanyOuterService;
import cn.kun.auth.system.role.service.SysRoleOuterService;
import cn.kun.auth.system.user.entity.po.SysUserRoleOuter;
import cn.kun.auth.system.user.service.SysUserRoleOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.dept.entity.po.SysDeptOuter;
import cn.kun.auth.system.dept.service.SysDeptOuterService;
import cn.kun.auth.system.job.service.SysJobOuterService;
import cn.kun.auth.system.role.entity.po.SysRoleOuter;
import cn.kun.base.api.entity.auth.vo.DeptInfoVO;
import cn.kun.base.api.entity.auth.vo.JobInfoVO;
import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.api.service.system.RemoteAreaService;
import cn.kun.base.api.service.system.RemoteFileService;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.AuthDictTypeConstants;
import cn.kun.base.core.global.entity.BaseTreeBuild;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.check.ParentHelp;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 外部公司表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysCompanyOuterServiceImpl extends ServiceImpl<SysCompanyOuterMapper, SysCompanyOuter> implements SysCompanyOuterService {

    @Autowired
    private SysDeptOuterService sysDeptOuterService;
    @Autowired
    private SysCompanyDetailOuterService sysCompanyDetailOuterService;
    @Autowired
    private SysJobOuterService sysJobOuterService;
    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private RemoteAreaService remoteAreaService;
    @Autowired
    private SysRoleOuterService sysRoleOuterService;
    @Autowired
    private SysUserRoleOuterService sysUserRoleOuterService;

    @Override
    public Page<CompanyPageVO> page(CompanyPageDTO dto) {
        log.info("进入外部公司分页");
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            dto.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        Page<CompanyPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        List<CompanyPageVO> voList = baseMapper.selectPage( dto);
        for (CompanyPageVO vo : voList) {
            vo.setType(baseDictService.getLabel(AuthDictTypeConstants.COMPANY_TYPE,vo.getType()));
            vo.setIndustry(baseDictService.getLabel(AuthDictTypeConstants.INDUSTRY,vo.getIndustry()));
            vo.setAreaName(remoteAreaService.getNameById(vo.getAreaId()).getData());
            for (CompanyPageVO companyPageVO : voList) {
                if (ObjUtil.equals(vo.getParentId(), companyPageVO.getId())) {
                    vo.setFlag(true);
                }
            }
            if (ObjUtil.isNull(vo.getFlag()) || !vo.getFlag()) {
                vo.setParentValue("0");
            }
        }
        // 分页查询
        List<CompanyPageVO> list = new BaseTreeBuild(voList).buildTree();
        page.setTotal(list.size());
        page.setRecords(list.stream().skip((long) (dto.getPageNo() - 1) * dto.getPageSize()).limit(dto.getPageSize()).collect(Collectors.toList()));
        return page;
    }

    @Override
    public CompanyDetailVO detail(Long id) {
        log.info("进入外部公司详情");
        if(ObjUtil.isEmpty(id)){
            log.warn("公司id不能为空#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"公司id不能为空");
        }
        // 查询公司信息
        SysCompanyOuter sysCompanyOuter = getById(id);
        if(ObjUtil.isEmpty(sysCompanyOuter)){
            log.warn("找不到该公司信息#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该公司信息");
        }
        // 查询公司详情信息
        QueryWrapper<SysCompanyDetailOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(SysCompanyDetailOuter::getCompanyOuterId,id);
        SysCompanyDetailOuter sysCompanyDetailOuter = sysCompanyDetailOuterService.getOne(qw);
        // 复制对象
        CompanyDetailVO vo = new CompanyDetailVO();
        BeanUtil.copyProperties(sysCompanyDetailOuter,vo);
        BeanUtil.copyProperties(sysCompanyOuter, vo);
        if(ObjUtil.isNotNull(vo.getLogo())){
            vo.setPath(remoteFileService.getPathById(vo.getLogo()).getData());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysCompanyOuter add(CompanyAddDTO dto) {

        log.info("外部公司-开始添加：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        // 校验公司信用代码
        QueryWrapper<SysCompanyDetailOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysCompanyDetailOuter::getCreditCode, dto.getCreditCode());

        List<SysCompanyDetailOuter> list = sysCompanyDetailOuterService.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            log.warn("外部公司：信用代码不允许重复！");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "信用代码重复！");
        }

        /* 传入值复制到数据库对象 */
        SysCompanyOuter sysCompanyOuter = new SysCompanyOuter();
        BeanUtil.copyProperties(dto, sysCompanyOuter);
        // 添加对象
        boolean add = save(sysCompanyOuter);
        // 添加成功后添加缓存
        if (!add) {
            log.warn("外部公司详情-添加失败！#{}",sysCompanyOuter);
            throw  new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("外部公司-成功添加");

        /* 传入值复制到数据库对象 */
        SysCompanyDetailOuter sysCompanyDetailOuter = new SysCompanyDetailOuter();
        BeanUtil.copyProperties(dto, sysCompanyDetailOuter);
        sysCompanyDetailOuter.setCompanyOuterId(sysCompanyOuter.getId());
        boolean addDetail = sysCompanyDetailOuterService.save(sysCompanyDetailOuter);
        if (!addDetail) {
            log.warn("外部公司详情-添加失败！#{}",sysCompanyDetailOuter);
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("外部公司详情-成功添加");

        // 添加角色绑定公司
        SysRoleOuter sysRoleOuter = new SysRoleOuter();
        sysRoleOuter.setCompanyOuterId(sysCompanyOuter.getId());
        sysRoleOuter.setType("supermanage");
        sysRoleOuter.setName(sysCompanyOuter.getName() + "管理员");

        sysRoleOuterService.save(sysRoleOuter);
        log.info("添加角色信息!");

        if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
            // 添加用户角色信息
            SysUserRoleOuter sysUserRoleOuter = new SysUserRoleOuter();
            sysUserRoleOuter.setRoleOuterId(sysRoleOuter.getId());
            sysUserRoleOuter.setUserOuterId(ConvertHelp.toLong(AuthHelp.getUserId()));
            sysUserRoleOuterService.save(sysUserRoleOuter);
            log.info("保存用户角色信息");
        }

        log.info("外部公司-添加缓存");
        getCompanyCacheInfoByCompanyId(sysCompanyOuter.getId());
        return sysCompanyOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysCompanyOuter edit(CompanyEditDTO dto) {

        log.info("外部公司-开始修改：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        // 校验公司信用代码
        QueryWrapper<SysCompanyDetailOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysCompanyDetailOuter::getCreditCode, dto.getCreditCode());

        List<SysCompanyDetailOuter> list = sysCompanyDetailOuterService.list(queryWrapper);
        if (CollUtil.isNotEmpty(list) && !ObjUtil.equals(dto.getId(),list.get(0).getCompanyOuterId())) {
            log.warn("外部公司：信用代码不允许重复！");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "信用代码重复！");
        }

        /* 获取数据库对象 */
        SysCompanyOuter sysCompanyOuter = getById(dto.getId());
        if (ObjUtil.isNull(sysCompanyOuter)) {
            log.warn("外部公司-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"修改的数据不存在，无法修改");
        }
        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysCompanyOuter);
        // 修改对象
        boolean edit = updateById(sysCompanyOuter);
        if (!edit) {
            log.warn("外部公司-修改失败！#{}",sysCompanyOuter);
            throw  new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败");
        }
        log.info("外部公司-成功修改");

        // 修改公司详情
        UpdateWrapper<SysCompanyDetailOuter> uw = new UpdateWrapper<>();
        uw.lambda()
                .eq(SysCompanyDetailOuter::getCompanyOuterId, dto.getId());
        SysCompanyDetailOuter sysCompanyDetailOuter = new SysCompanyDetailOuter();
        BeanUtil.copyProperties(dto,sysCompanyDetailOuter);

        boolean editDetail = sysCompanyDetailOuterService.update(sysCompanyDetailOuter, uw);
        if (!editDetail) {
            log.warn("外部公司-修改失败！#{}",sysCompanyDetailOuter);
            throw  new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("外部公司详情-成功修改");

        // 复制公司信息到公司缓存实体类
        AuthCompanyInfo authCompanyInfo = new AuthCompanyInfo();
        BeanUtil.copyProperties(sysCompanyOuter,authCompanyInfo);
        /* 通过公司id查询部门信息 */
        QueryWrapper<SysDeptOuter> qw = new QueryWrapper<SysDeptOuter>();
        qw.lambda().eq(SysDeptOuter::getCompanyOuterId,sysCompanyOuter.getId());
        List<SysDeptOuter> deptOuterList = sysDeptOuterService.list(qw);
        authCompanyInfo.setDeptOuterList(deptOuterList);
        // 修改公司缓存信息
//        RedisUtils.setHash(AuthCacheConstants.COMPANY_OUTER_HASH, Convert.toStr(sysCompanyOuter.getId()), authCompanyInfo);
        selectCompanyOuterPowerInfo(sysCompanyOuter.getId());
        log.info("外部公司-成功修改缓存");
        return sysCompanyOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("外部公司-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }

        // 删除公司前需要进行校验。1、判断该公司是否有部门，有则不能删除，反之即可；2、判断该公司是否有下级公司，有则不能删除，反之即可。
        QueryWrapper<SysDeptOuter> queryDept = new QueryWrapper<>();
        queryDept.lambda().eq(SysDeptOuter::getCompanyOuterId,id);
        List<SysDeptOuter> deptList = sysDeptOuterService.list(queryDept);

        /* 部门列表为空，判断是否有下级公司 */
        if (CollUtil.isEmpty(deptList)) {
            QueryWrapper<SysCompanyOuter> queryCompany = new QueryWrapper<>();
            queryCompany.lambda().eq(SysCompanyOuter::getParentId, id);
            List<SysCompanyOuter> companyList = list(queryCompany);

            if (CollUtil.isEmpty(companyList)) {
                // 删除外部公司信息
                boolean remove = removeById(id);
                // 删除后删除缓存
                if (remove) {
                    log.info("外部公司-成功删除");
                }
                // 删除外部公司详情信息
                QueryWrapper<SysCompanyDetailOuter> qw = new QueryWrapper<>();
                qw.lambda().eq(SysCompanyDetailOuter::getCompanyOuterId,id);
                boolean removeDetail = sysCompanyDetailOuterService.remove(qw);
                if (removeDetail) {
                    log.info("外部公司详情-成功删除");
                }
                RedisHelp.delHash(AuthCacheConstants.COMPANY_OUTER_HASH, Convert.toStr(id));
                log.info("外部公司-成功删除缓存");
            }else {
                log.warn("外部公司-删除失败");
                throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前公司有下级公司，不能删除当前公司信息");
            }
        }else {
            log.warn("外部公司-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前公司有部门信息，不能删除当前公司信息");
        }

    }

    /**
     * 通过外部公司id获取外部公司缓存信息
     * @param companyId
     * @return
     */
    @Override
    public SysCompanyInfoVO getCompanyCacheInfoByCompanyId(Long companyId) {
        // 去redis缓存查询项目信息
        SysCompanyInfoVO companyOuterCacheInfo = (SysCompanyInfoVO) RedisHelp.getHash(AuthCacheConstants.COMPANY_OUTER_HASH, AuthCacheConstants.COMPANY_OUTER_ID + companyId);
        if (ObjUtil.isEmpty(companyOuterCacheInfo)){
            return selectCompanyOuterPowerInfo(companyId);
        }
        return companyOuterCacheInfo;
    }


    @Override
    public SysCompanyInfoVO selectCompanyOuterPowerInfo(Long companyId){
        // 查询公司信息
        SysCompanyInfoVO companyOuterInfo = baseMapper.getCompanyInfoByCompanyId(companyId);
        if(ObjUtil.isNotEmpty(companyOuterInfo)){
            // 查询部门名称列表
            List<DeptInfoVO> deptInfoVoList = sysDeptOuterService.selectDeptCacheInfoByCompanyId(companyId);
            companyOuterInfo.setDeptInfoVoList(deptInfoVoList);

            // 获取公司下的所有职位
            List<JobInfoVO> jobList = sysJobOuterService.selectJobInfoByCompanyId(companyId);

            deptInfoVoList.stream().forEach(dept -> {
                List<JobInfoVO> jobs = new ArrayList<>();
                jobList.stream().forEach(job -> {
                    if (ObjUtil.equals(job.getDeptId(), dept.getDeptId())) {
                        jobs.add(job);
                    }
                });
                dept.setJobList(jobs);
            });

            // 将公司信息存入redis缓存
            RedisHelp.setHash(AuthCacheConstants.COMPANY_OUTER_HASH, String.valueOf(companyOuterInfo.getCompanyId()),companyOuterInfo);
        }
        return companyOuterInfo;
    }

    /**
     *  查看用户所拥有的公司信息
     * @return
     */
    @Override
    public List<BaseSelectVO> selectOuterParent(){
        log.info("进入查询内部上级公司");
        String loginName = AuthHelp.getLoginName();
        if(StrUtil.isEmpty(loginName)){
            log.warn("登录名不能为空#{}",loginName);
            throw new BusinessException(ErrorCodeConstants.NULL,"登录名不能为空");
        }
        List<CompanyInfoVO> list = null;
        // 校验是否是超级管理员
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            list = baseMapper.selectOuterParent(loginName);
            return list.stream().map(this::cast).toList();
        }
        list = baseMapper.selectOuterParent(null);
        return list.stream().map(this::cast).toList();
    }


    /**
     *  人员配置：获取当前用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    @Override
    public List<BaseSelectVO> selectCompanyInfoByLoginName(String loginName){
        return baseMapper.selectCompanyInfoByLoginName(loginName);
    }
    /**
     *  根据id获取公司信息
     * @param companyId
     * @return
     */
    @Override
    public BaseSelectVO getCompanyInfoTree(Long companyId){
        return baseMapper.getCompanyInfoTree(companyId);
    }
    /**
     *  根据用户获取 有角色的公司信息
     * @param loginName 登录名
     * @return
     */
    @Override
    public List<BaseSelectVO> selectRoleInfoByLoginName(String loginName){
        return baseMapper.selectRoleInfoByLoginName(loginName);
    }
    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        if (obj instanceof CompanyInfoVO companyInfoVO) {
            vo.setValue(Convert.toStr(companyInfoVO.getCompanyId()));
            vo.setLabel(companyInfoVO.getCompanyName());
            vo.setParentValue(Convert.toStr(companyInfoVO.getParentId()));
            vo.setParentValues(companyInfoVO.getParentIds());
        }
        return vo;
    }
}
