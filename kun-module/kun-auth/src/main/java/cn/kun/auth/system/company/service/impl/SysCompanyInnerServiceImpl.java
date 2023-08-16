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
import cn.kun.auth.system.company.entity.po.SysCompanyDetailInner;
import cn.kun.auth.system.company.entity.po.SysCompanyInner;
import cn.kun.auth.system.company.entity.vo.CompanyDetailVO;
import cn.kun.auth.system.company.entity.vo.CompanyInfoVO;
import cn.kun.auth.system.company.entity.vo.CompanyPageVO;
import cn.kun.auth.system.company.mapper.SysCompanyInnerMapper;
import cn.kun.auth.system.company.service.SysCompanyDetailInnerService;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.auth.system.role.service.SysRoleInnerService;
import cn.kun.auth.system.user.entity.po.SysUserRoleInner;
import cn.kun.auth.system.user.service.SysUserRoleInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.dept.entity.po.SysDeptInner;
import cn.kun.auth.system.dept.service.SysDeptInnerService;
import cn.kun.auth.system.job.service.SysJobInnerService;
import cn.kun.auth.system.role.entity.po.SysRoleInner;
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
 * 内部公司表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysCompanyInnerServiceImpl extends ServiceImpl<SysCompanyInnerMapper, SysCompanyInner> implements SysCompanyInnerService {
    @Autowired
    private SysDeptInnerService sysDeptInnerService;
    @Autowired
    private SysCompanyDetailInnerService sysCompanyDetailInnerService;
    @Autowired
    private SysJobInnerService sysJobInnerService;
    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private RemoteAreaService remoteAreaService;
    @Autowired
    private SysRoleInnerService sysRoleInnerService;
    @Autowired
    private SysUserRoleInnerService sysUserRoleInnerService;
    @Override
    public Page<CompanyPageVO> page(CompanyPageDTO dto) {

        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            dto.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        Page<CompanyPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        List<CompanyPageVO> voList = baseMapper.selectPage(dto);
        for (CompanyPageVO vo : voList) {
            vo.setType(baseDictService.getLabel(AuthDictTypeConstants.COMPANY_TYPE,vo.getType()));
            vo.setIndustry(baseDictService.getLabel(AuthDictTypeConstants.INDUSTRY,vo.getIndustry()));
            vo.setAreaName(remoteAreaService.getNameById(vo.getAreaId()).getData());
            // 内层循环遍历是否是子集，是子集则给予标记
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
        log.info("进入内部公司详情");
        if(ObjUtil.isEmpty(id)){
            log.warn("公司id不能为空#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"公司id不能为空");
        }
        // 查询公司信息
        SysCompanyInner sysCompanyInner = getById(id);
        if(ObjUtil.isEmpty(sysCompanyInner)){
            log.warn("找不到该公司信息#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该公司信息");
        }
        // 查询公司详情信息
        QueryWrapper<SysCompanyDetailInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysCompanyDetailInner::getCompanyInnerId,id);
        SysCompanyDetailInner sysCompanyDetailInner = sysCompanyDetailInnerService.getOne(qw);
        // 复制对象
        CompanyDetailVO vo = new CompanyDetailVO();
        BeanUtil.copyProperties(sysCompanyDetailInner,vo);
        BeanUtil.copyProperties(sysCompanyInner, vo);
        if(ObjUtil.isNotNull(vo.getLogo())){
            vo.setPath(remoteFileService.getPathById(vo.getLogo()).getData());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysCompanyInner add(CompanyAddDTO dto) {

        log.info("内部公司-开始添加：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        // 校验公司信用代码
        QueryWrapper<SysCompanyDetailInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysCompanyDetailInner::getCreditCode, dto.getCreditCode());

        List<SysCompanyDetailInner> list = sysCompanyDetailInnerService.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            log.warn("内部公司：信用代码不允许重复！");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "信用代码重复！");
        }

        /* 传入值复制到数据库对象 */
        SysCompanyInner sysCompanyInner = new SysCompanyInner();
        BeanUtil.copyProperties(dto, sysCompanyInner);
        // 添加对象
        boolean add = save(sysCompanyInner);
        // 添加成功后添加缓存
        if (!add) {
            log.warn("内部公司-添加失败！#{}",sysCompanyInner);
            throw  new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("内部公司-成功添加");

        /* 传入值复制到数据库对象 */
        SysCompanyDetailInner sysCompanyDetailInner = new SysCompanyDetailInner();
        BeanUtil.copyProperties(dto, sysCompanyDetailInner);
        sysCompanyDetailInner.setCompanyInnerId(sysCompanyInner.getId());
        boolean addDetail = sysCompanyDetailInnerService.save(sysCompanyDetailInner);
        if (!addDetail) {
            log.warn("内部公司详情-成功失败！#{}",sysCompanyDetailInner);
            throw  new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("内部公司详情-成功添加");

        // 添加角色绑定公司
        SysRoleInner sysRoleInner = new SysRoleInner();
        sysRoleInner.setCompanyInnerId(sysCompanyInner.getId());
        sysRoleInner.setType("supermanage");
        sysRoleInner.setName(sysCompanyInner.getName() + "管理员");

        sysRoleInnerService.save(sysRoleInner);
        log.info("添加角色信息!");

        if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
            // 添加用户角色信息
            SysUserRoleInner sysUserRoleInner = new SysUserRoleInner();
            sysUserRoleInner.setRoleInnerId(sysRoleInner.getId());
            sysUserRoleInner.setUserInnerId(ConvertHelp.toLong(AuthHelp.getUserId()));
            sysUserRoleInnerService.save(sysUserRoleInner);
            log.info("保存用户角色信息");
        }

        log.info("内部公司-添加缓存");
        getCompanyCacheInfoByCompanyId(sysCompanyInner.getId());
        return sysCompanyInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysCompanyInner edit(CompanyEditDTO dto) {

        log.info("内部公司-开始修改：{}", dto);

        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        // 校验公司信用代码
        QueryWrapper<SysCompanyDetailInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysCompanyDetailInner::getCreditCode, dto.getCreditCode());

        List<SysCompanyDetailInner> list = sysCompanyDetailInnerService.list(queryWrapper);
        if (CollUtil.isNotEmpty(list) && !ObjUtil.equals(dto.getId(), list.get(0).getCompanyInnerId())) {
            log.warn("内部公司：信用代码不允许重复！");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "信用代码重复！");
        }

        /* 获取数据库对象 */
        SysCompanyInner sysCompanyInner = getById(dto.getId());
        if (ObjUtil.isNull(sysCompanyInner)) {
            log.warn("内部公司-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"修改的数据不存在，无法修改");
        }
        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysCompanyInner);
        // 修改对象
        boolean edit = updateById(sysCompanyInner);
        if (!edit) {
            log.warn("内部公司-修改失败！#{}",sysCompanyInner);
            throw  new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败");
        }
        log.info("内部公司-成功修改");
        // 修改公司详情
        UpdateWrapper<SysCompanyDetailInner> uw = new UpdateWrapper<>();
        uw.lambda()
                .eq(SysCompanyDetailInner::getCompanyInnerId, dto.getId());
        SysCompanyDetailInner sysCompanyDetailInner = new SysCompanyDetailInner();
        BeanUtil.copyProperties(dto,sysCompanyDetailInner);

        sysCompanyDetailInnerService.update(sysCompanyDetailInner, uw);

        log.info("内部公司详情-成功修改");

        // 复制公司信息到公司缓存实体类
        AuthCompanyInfo authCompanyInfo = new AuthCompanyInfo();
        BeanUtil.copyProperties(sysCompanyInner,authCompanyInfo);
        /* 通过公司id查询部门信息 */
        QueryWrapper<SysDeptInner> qw = new QueryWrapper<SysDeptInner>();
        qw.lambda().eq(SysDeptInner::getCompanyInnerId,sysCompanyInner.getId());
        List<SysDeptInner> deptList = sysDeptInnerService.list(qw);
        authCompanyInfo.setDeptList(deptList);
        // 修改公司缓存信息
        selectCompanyPowerInfo(sysCompanyInner.getId());
        log.info("内部公司-成功修改缓存");
        return sysCompanyInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("内部公司-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }

        // 删除公司前需要进行校验。1、判断该公司是否有部门，有则不能删除，反之即可；2、判断该公司是否有下级公司，有则不能删除，反之即可。
        QueryWrapper<SysDeptInner> queryDept = new QueryWrapper<>();
        queryDept.lambda().eq(SysDeptInner::getCompanyInnerId,id);
        List<SysDeptInner> deptList = sysDeptInnerService.list(queryDept);

        /* 部门列表为空，判断是否有下级公司 */
        if (CollUtil.isEmpty(deptList)){
            QueryWrapper<SysCompanyInner> queryCompany = new QueryWrapper<>();
            queryCompany.lambda().eq(SysCompanyInner::getParentId,id);
            List<SysCompanyInner> companyList = list(queryCompany);
            if (CollUtil.isEmpty(companyList)){
                // 删除内部公司信息
                boolean remove = removeById(id);
                // 删除后删除缓存
                if (remove) {
                    log.info("内部公司-成功删除");
                }
                // 删除内部公司详情信息
                QueryWrapper<SysCompanyDetailInner> qw = new QueryWrapper<>();
                qw.lambda().eq(SysCompanyDetailInner::getCompanyInnerId,id);
                boolean removeDetail = sysCompanyDetailInnerService.remove(qw);
                if (removeDetail) {
                    log.info("内部公司详情-成功删除");
                }

                RedisHelp.delHash(AuthCacheConstants.COMPANY_INNER_HASH, Convert.toStr(id));
                log.info("内部公司-成功删除缓存");
            }
            else {
                log.warn("内部公司-删除失败");
                throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前公司有下级公司，不能删除当前公司信息");
            }
        }
        else {
            log.warn("内部公司-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前公司有部门信息，不能删除当前公司信息");
        }
    }


    /**
     * 通过公司id获取公司信息
     *
     * @param companyId
     * @return
     */
    @Override
    public SysCompanyInfoVO getCompanyCacheInfoByCompanyId(Long companyId) {
        // 去redis缓存查询项目信息
        SysCompanyInfoVO companyInnerCacheInfo = (SysCompanyInfoVO) RedisHelp.getHash(AuthCacheConstants.COMPANY_INNER_HASH, AuthCacheConstants.COMPANY_INNER_ID + companyId);
        if (ObjUtil.isEmpty(companyInnerCacheInfo)) {
            return selectCompanyPowerInfo(companyId);
        }
        return companyInnerCacheInfo;
    }

    /**
     * 通过内部公司id查询内部公司缓存信息
     * @param companyId
     * @return
     */
    @Override
    public SysCompanyInfoVO selectCompanyPowerInfo(Long companyId){
        // 查询公司信息
        SysCompanyInfoVO companyInnerInfo = baseMapper.getCompanyInfoByCompanyId(companyId);
        if(ObjUtil.isNotEmpty(companyInnerInfo)){
            // 查询部门名称列表
            List<DeptInfoVO> deptInfoVoList = sysDeptInnerService.selectDeptCacheInfoByCompanyId(companyId);

            List<JobInfoVO> jobList = sysJobInnerService.selectJobInfoByCompanyId(companyId);

            deptInfoVoList.stream().forEach(dept -> {
                List<JobInfoVO> jobs = new ArrayList<>();
                jobList.stream().forEach(job -> {
                    if (ObjUtil.equals(job.getDeptId(), dept.getDeptId())) {
                        jobs.add(job);
                    }
                });
                dept.setJobList(jobs);
            });

            companyInnerInfo.setDeptInfoVoList(deptInfoVoList);
            // 将公司信息存入redis缓存
            RedisHelp.setHash(AuthCacheConstants.COMPANY_INNER_HASH, String.valueOf(companyInnerInfo.getCompanyId()), companyInnerInfo);
        }
        return companyInnerInfo;
    }

    /**
     *  查看用户所拥有的公司信息
     * @return
     */
    @Override
    public List<BaseSelectVO> selectInnerParent(){
        log.info("进入查询内部上级公司");
        String loginName = AuthHelp.getLoginName();
        if(StrUtil.isEmpty(loginName)){
            log.warn("登录名不能为空#{}",loginName);
            throw new BusinessException(ErrorCodeConstants.NULL,"登录名不能为空");
        }
        List<CompanyInfoVO> list = null;
        // 校验是否是超级管理员
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            list = baseMapper.selectInnerParent(loginName);
            return list.stream().map(this::cast).toList();
        }
        list = baseMapper.selectInnerParent(null);
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
