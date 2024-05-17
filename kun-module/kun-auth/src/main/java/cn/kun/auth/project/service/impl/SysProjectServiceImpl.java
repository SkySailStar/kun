package cn.kun.auth.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sevnce.auth.company.entity.dto.CompanyAddDTO;
import com.sevnce.auth.company.entity.po.SysCompanyInner;
import com.sevnce.auth.company.service.SysCompanyInnerService;
import com.sevnce.auth.company.service.SysCompanyOuterService;
import com.sevnce.auth.dept.service.SysDeptInnerService;
import com.sevnce.auth.dept.service.SysDeptOuterService;
import com.sevnce.auth.job.service.SysJobInnerService;
import com.sevnce.auth.job.service.SysJobOuterService;
import com.sevnce.auth.project.entity.dto.DeletePersonnelDTO;
import com.sevnce.auth.project.entity.dto.ProjectAddDTO;
import com.sevnce.auth.project.entity.dto.ProjectEditDTO;
import com.sevnce.auth.project.entity.dto.ProjectOaSaveDTO;
import com.sevnce.auth.project.entity.dto.ProjectPageDTO;
import com.sevnce.auth.project.entity.dto.SavePersonnelDTO;
import com.sevnce.auth.project.entity.po.SysProject;
import com.sevnce.auth.project.entity.po.SysProjectDetail;
import com.sevnce.auth.project.entity.vo.PersonnelDetailVO;
import com.sevnce.auth.project.entity.vo.ProjectPageVO;
import com.sevnce.auth.project.entity.vo.ProjectRedisVO;
import com.sevnce.auth.project.mapper.SysProjectMapper;
import com.sevnce.auth.project.service.SysProjectDetailService;
import com.sevnce.auth.project.service.SysProjectService;
import com.sevnce.auth.role.entity.dto.RoleAddDTO;
import com.sevnce.auth.role.entity.dto.RoleProjectDTO;
import com.sevnce.auth.role.entity.po.SysRoleInner;
import com.sevnce.auth.role.entity.po.SysRoleProjectInner;
import com.sevnce.auth.role.entity.po.SysRoleProjectOuter;
import com.sevnce.auth.role.service.SysRoleInnerService;
import com.sevnce.auth.role.service.SysRoleProjectInnerService;
import com.sevnce.auth.role.service.SysRoleProjectOuterService;
import com.sevnce.auth.user.entity.dto.UserPersonnelDTO;
import com.sevnce.auth.user.entity.po.SysUserInner;
import com.sevnce.auth.user.entity.po.SysUserProjectInner;
import com.sevnce.auth.user.entity.po.SysUserProjectOuter;
import com.sevnce.auth.user.entity.po.UserDetailList;
import com.sevnce.auth.user.entity.vo.UserPersonnelPageVO;
import com.sevnce.auth.user.service.SysUserInnerService;
import com.sevnce.auth.user.service.SysUserOuterService;
import com.sevnce.auth.user.service.SysUserProjectInnerService;
import com.sevnce.auth.user.service.SysUserProjectOuterService;
import com.sevnce.base.api.entity.auth.dto.ProjectNoListDTO;
import com.sevnce.base.api.entity.auth.dto.UserDTO;
import com.sevnce.base.api.entity.auth.vo.ProjectDetailVO;
import com.sevnce.base.api.entity.auth.vo.ProjectParentVO;
import com.sevnce.base.api.entity.auth.vo.SysProjectInfoVO;
import com.sevnce.base.api.entity.auth.vo.UserRedisVO;
import com.sevnce.base.api.service.system.BaseDictService;
import com.sevnce.base.api.service.system.RemoteAreaService;
import com.sevnce.base.api.service.system.RemoteFileService;
import com.sevnce.base.core.cache.constant.AuthCacheConstants;
import com.sevnce.base.core.cache.util.RedisHelp;
import com.sevnce.base.core.global.constant.ErrorCodeConstants;
import com.sevnce.base.core.global.constant.auth.AuthConstants;
import com.sevnce.base.core.global.constant.system.dict.type.AuthDictTypeConstants;
import com.sevnce.base.core.global.entity.BaseTreeBuild;
import com.sevnce.base.core.global.entity.vo.BaseSelectVO;
import com.sevnce.base.core.global.exception.BusinessException;
import com.sevnce.base.core.global.util.cast.CastHelp;
import com.sevnce.base.core.global.util.check.ParentHelp;
import com.sevnce.base.core.global.util.str.StrHelp;
import com.sevnce.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysProjectServiceImpl extends ServiceImpl<SysProjectMapper, SysProject> implements SysProjectService {
    @Autowired
    private SysProjectDetailService sysProjectDetailService;
    @Autowired
    private SysCompanyInnerService sysCompanyInnerService;
    @Autowired
    private SysCompanyOuterService sysCompanyOuterService;
    @Autowired
    private SysRoleInnerService sysRoleInnerService;
    @Autowired
    private SysRoleProjectInnerService sysRoleProjectInnerService;
    @Autowired
    private SysRoleProjectOuterService sysRoleProjectOuterService;
    @Autowired
    private SysUserProjectInnerService sysUserProjectInnerService;
    @Autowired
    private SysUserProjectOuterService sysUserProjectOuterService;
    @Autowired
    private SysUserInnerService sysUserInnerService;
    @Autowired
    private SysUserOuterService sysUserOuterService;
    @Autowired
    private SysDeptInnerService sysDeptInnerService;
    @Autowired
    private SysDeptOuterService sysDeptOuterService;
    @Autowired
    private SysJobInnerService sysJobInnerService;
    @Autowired
    private SysJobOuterService sysJobOuterService;
    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private RemoteAreaService remoteAreaService;

    @Override
    public Page<ProjectPageVO> page(ProjectPageDTO dto) {

        log.info("进入项目分页");
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            dto.setUserId(CastHelp.toLong(AuthHelp.getUserId()));
        }
        Page<ProjectPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());

        List<ProjectPageVO> voList = null;
        // 查询内部项目信息
        if(AuthHelp.getCharacteristic()){
            voList = baseMapper.selectInnerPage(dto);
        }else {
            // 查询外部项目信息
            voList = baseMapper.selectOuterPage(dto);
        }
        // 外层循环
        for (ProjectPageVO vo : voList) {
            vo.setIndustry(baseDictService.getLabel(AuthDictTypeConstants.INDUSTRY,vo.getIndustry()));
            vo.setSaleStatus(baseDictService.getLabel(AuthDictTypeConstants.PROJECT_STATUS,vo.getSaleStatus()));
            vo.setAreaName(remoteAreaService.getNameById(vo.getAreaId()).getData());

            // 内层循环遍历是否是子集，是子集则给予标记
            for (ProjectPageVO projectPageVO : voList) {
                if (ObjUtil.equals(vo.getParentId(), projectPageVO.getId())) {
                    vo.setFlag(true);
                }
            }
            if (ObjUtil.isNull(vo.getFlag()) || !vo.getFlag()) {
                vo.setParentValue("0");
            }
        }
        // 分页查询
        List<ProjectPageVO> list = new BaseTreeBuild(voList).buildTree();
        page.setTotal(list.size());
        page.setRecords(list.stream().skip((long) (dto.getPageNo() - 1) * dto.getPageSize()).limit(dto.getPageSize()).collect(Collectors.toList()));
        return page;
    }


    @Override
    public ProjectDetailVO detail(String projectNo) {
        log.info("进入项目详情");
        // 查询详情
        QueryWrapper<SysProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysProject::getProjectNo,projectNo);
        SysProject sysProject = getOne(queryWrapper);
        if(ObjUtil.isEmpty(sysProject)){
            log.warn("找不到该项目数据#{}",projectNo);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该项目数据");
        }
        // 复制对象
        ProjectDetailVO vo = new ProjectDetailVO();
        BeanUtil.copyProperties(sysProject, vo);
        QueryWrapper<SysProjectDetail> qw = new QueryWrapper<>();
        qw.lambda().eq(SysProjectDetail::getProjectNo,sysProject.getProjectNo());
        SysProjectDetail sysProjectDetail = sysProjectDetailService.getOne(qw);
        BeanUtil.copyProperties(sysProjectDetail, vo);
        vo.setId(sysProject.getId());
        if(ObjUtil.isNotEmpty(sysProjectDetail)){
            vo.setProjectDetailId(sysProjectDetail.getId());
        }
        if(ObjUtil.isNotNull(vo.getLogo())){
            vo.setPath(remoteFileService.getPathById(vo.getLogo()).getData());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysProject add(ProjectAddDTO dto) {

        log.info("项目-开始添加：{}", dto);

        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        String regex = "^[a-zA-Z0-9]+$";
        if (!dto.getProjectNo().matches(regex)) {
            log.warn("项目编号格式错误!");
            throw new BusinessException(ErrorCodeConstants.FORMAT_ERROR, "项目编号格式错误!");
        }

        // 校验是否有重复项目编号
        QueryWrapper<SysProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysProject::getProjectNo,dto.getProjectNo());
        List<SysProject> list = list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            log.warn("项目编号重复#{}", dto.getProjectNo());
            throw new BusinessException(ErrorCodeConstants.REPEAT, "项目编号重复");
        }

        // 校验是否有重复项目名称
        QueryWrapper<SysProject> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.lambda()
                .eq(SysProject::getName,dto.getName());
        List<SysProject> listName = list(queryWrapperName);
        if (CollUtil.isNotEmpty(listName)) {
            log.warn("项目名称重复#{}", dto.getName());
            throw new BusinessException(ErrorCodeConstants.REPEAT, "项目名称重复");
        }

        /* 传入值复制到数据库对象 */
        SysProject sysProject = new SysProject();
        BeanUtil.copyProperties(dto,sysProject);
        // 添加对象
        boolean add = save(sysProject);
        // 添加成功后添加缓存
        if (!add) {
            log.warn("项目-添加失败{}",sysProject);
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("项目-成功添加");
        // 复制对象
        SysProjectDetail sysProjectDetail = new SysProjectDetail();
        BeanUtil.copyProperties(dto,sysProjectDetail);
        // 赋值项目id
        sysProjectDetail.setProjectNo(sysProject.getProjectNo());
        // 添加对象
        boolean addDetail = sysProjectDetailService.save(sysProjectDetail);
        if (!addDetail) {
            log.warn("项目详情-成功失败#{}",sysProjectDetail);
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("项目详情-成功添加");

        // 添加项目缓存
        RedisHelp.setHash(AuthCacheConstants.PROJECT_HASH, Convert.toStr(sysProject.getId()), sysProject);
        log.info("项目-成功添加缓存");

        // 保存用户项目信息
        Long userId = CastHelp.toLong(AuthHelp.getUserId());
        if(AuthHelp.getCharacteristic()){
            if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
                SysUserProjectInner sysUserProjectInner = new SysUserProjectInner();
                sysUserProjectInner.setProjectNo(sysProject.getProjectNo());
                sysUserProjectInner.setUserInnerId(userId);
                sysUserProjectInnerService.save(sysUserProjectInner);
                log.info("保存内部用户项目信息");
                // 从新获取用户缓存
                sysUserInnerService.selectUserInnerPowerInfo(userId);
            }
            return sysProject;
        }
        SysUserProjectOuter sysUserProjectOuter = new SysUserProjectOuter();
        sysUserProjectOuter.setProjectNo(sysProject.getProjectNo());
        sysUserProjectOuter.setUserOuterId(userId);
        sysUserProjectOuterService.save(sysUserProjectOuter);
        log.info("保存外部用户项目信息");
        // 从新获取用户缓存
        sysUserOuterService.selectUserOuterPowerInfo(userId);
        return sysProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysProject edit(ProjectEditDTO dto) {

        log.info("项目-开始修改：{}", dto);

        // 校验是否有重复项目名称
        QueryWrapper<SysProject> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.lambda()
                .eq(SysProject::getName, dto.getName());
        List<SysProject> listName = list(queryWrapperName);
        if (CollUtil.isNotEmpty(listName) && !ObjUtil.equals(dto.getId(),listName.get(0).getId())) {
            log.warn("项目名称重复#{}", dto.getName());
            throw new BusinessException(ErrorCodeConstants.REPEAT, "项目名称重复");
        }

        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 获取数据库对象 */
        SysProject sysProject = getById(dto.getId());
        String projectNo = sysProject.getProjectNo();
        BeanUtil.copyProperties(dto, sysProject);
        // 修改对象
        boolean edit = updateById(sysProject);
        if (!edit) {
            log.warn("项目-修改失败#{}",sysProject);
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL, "修改失败");
        }
        log.info("项目-成功修改");
        // 修改项目详情
        UpdateWrapper<SysProjectDetail> uw = new UpdateWrapper<>();
        uw.lambda().eq(SysProjectDetail::getProjectNo,projectNo);
        SysProjectDetail sysProjectDetail = new SysProjectDetail();
        // 赋值项目id
        sysProjectDetail.setProjectNo(sysProject.getProjectNo());
        sysProjectDetail.setSaleStatus(dto.getSaleStatus());
        sysProjectDetail.setScale(dto.getScale());
        sysProjectDetail.setWebsite(dto.getWebsite());
        sysProjectDetail.setSummary(dto.getSummary());
        sysProjectDetail.setPlaneArea(dto.getPlaneArea());
        // 修改项目详情信息
        boolean editDetail = sysProjectDetailService.update(sysProjectDetail,uw);
        if (editDetail) {
            log.warn("项目详情-修改成功#{}",sysProjectDetail);
        }
        // 修改项目缓存信息
        RedisHelp.setHash(AuthCacheConstants.PROJECT_HASH, Convert.toStr(sysProject.getId()), sysProject);
        log.info("项目-成功添加缓存");
        log.info("项目-修改成功-清除缓存");
        // 清除所有用户、角色缓存
        RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
        RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
        RedisHelp.del(AuthCacheConstants.ROLE_INNER_HASH);
        RedisHelp.del(AuthCacheConstants.ROLE_OUTER_HASH);
        return sysProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("项目-开始删除：{}", id);
        // 判空
        if (ObjUtil.isEmpty(id)) {
            log.warn("项目id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "项目id不能为空");
        }

        // 删除项目前，需要校验当前菜单是否有下级项目，如果有，则不能删除，反之即可。
        QueryWrapper<SysProject> queryProject = new QueryWrapper<>();
        queryProject.lambda().eq(SysProject::getParentId,id);
        List<SysProject> projectList = list(queryProject);
        /* 判断是否存在下级项目 */
        if (CollUtil.isEmpty(projectList)){
            SysProject sysProject = getById(id);
            // 删除项目信息
            boolean remove = removeById(id);
            if (!remove) {
                log.warn("项目-删除失败！#{}",id);
                throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "删除失败！");
            }
            log.info("项目-成功删除");
            // 删除项目详情信息
            QueryWrapper<SysProjectDetail> qw = new QueryWrapper<>();
            String projectNo = sysProject.getProjectNo();
            if (StrUtil.isNotBlank(projectNo)){
                qw.lambda().eq(SysProjectDetail::getProjectNo,projectNo);
                sysProjectDetailService.remove(qw);
                log.info("项目详情-成功删除");
            }

            if(AuthHelp.getCharacteristic()){
                // 删除内部用户项目信息
                QueryWrapper<SysUserProjectInner> userProjectInnerQueryWrapper = new QueryWrapper<>();
                userProjectInnerQueryWrapper.lambda()
                        .eq(SysUserProjectInner::getProjectNo,projectNo);
                sysUserProjectInnerService.remove(userProjectInnerQueryWrapper);
                log.info("删除内部用户项目信息");

                // 删除内部用户项目信息
                QueryWrapper<SysRoleProjectInner> roleProjectInnerQueryWrapper = new QueryWrapper<>();
                roleProjectInnerQueryWrapper.lambda()
                        .eq(SysRoleProjectInner::getProjectNo,projectNo);
                sysRoleProjectInnerService.remove(roleProjectInnerQueryWrapper);
                log.info("删除内部角色项目信息");
            }else{
                // 删除外部用户项目信息
                QueryWrapper<SysUserProjectOuter> userProjectOuterQueryWrapper = new QueryWrapper<>();
                userProjectOuterQueryWrapper.lambda()
                        .eq(SysUserProjectOuter::getProjectNo,projectNo);
                sysUserProjectOuterService.remove(userProjectOuterQueryWrapper);
                log.info("删除外部用户项目信息");

                // 删除外部用户项目信息
                QueryWrapper<SysRoleProjectOuter> roleProjectOuterQueryWrapper = new QueryWrapper<>();
                roleProjectOuterQueryWrapper.lambda()
                        .eq(SysRoleProjectOuter::getProjectNo,projectNo);
                sysRoleProjectOuterService.remove(roleProjectOuterQueryWrapper);
                log.info("删除外部角色项目信息");
            }

            log.info("项目-删除成功-清除缓存");
            // 删除项目缓存
            RedisHelp.delHash(AuthCacheConstants.PROJECT_HASH, Convert.toStr(id));
            // 清除所有用户、角色缓存
            RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_OUTER_HASH);
        }
        else {
            log.warn("项目-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前项目有下级项目信息，不能删除当前项目信息");
        }
    }

    /**
     *  根据内部用户获取当前用户所有项目信息
     * @param userInnerId  内部用户id
     * @return 数组
     */
    @Override
    public List<ProjectParentVO> selectProjectNoByUserInnerId(Long userInnerId) {
        return baseMapper.selectProjectNoByUserInnerId(userInnerId);
    }





    /**
     * 查询外部角色-项目权限
     * @param roleInnerId
     * @return
     */
    @Override
    public List<ProjectParentVO> selectListByRoleInnerId(Long roleInnerId) {
        return baseMapper.selectListByRoleInnerId(roleInnerId);
    }

    /**
     * 查询外部角色-项目权限
     * @param roleOuterId
     * @return
     */
    @Override
    public List<ProjectParentVO> selectListByRoleOuterId(Long roleOuterId) {
        return baseMapper.selectListByRoleOuterId(roleOuterId);
    }

    /**
     * 通过项目编号列表查询项目信息
     * @param dto
     * @return
     */
    @Override
    public List<SysProjectInfoVO> queryListByProjectNos(ProjectNoListDTO dto) {

        log.info("进入查询项目列表信息");
        List<String> projectNoList = dto.getProjectNoList();
        if (CollUtil.isEmpty(projectNoList)) {
            log.warn("项目编号不允许为空!");
            throw new BusinessException(ErrorCodeConstants.NULL, "项目编号不允许为空!");
        }

        // 批量查询项目信息列表信息
        List<SysProjectInfoVO> projectListInfo = baseMapper.selectInfoByProjectNoList(projectNoList);
        return projectListInfo;
    }

    /**
     * 通过项目名称查询项目信息
     * @param name
     * @return
     */
    @Override
    public List<ProjectRedisVO> selectInfoByProjectName(String name) {
        return baseMapper.selectInfoByProjectName(name);
    }

    /**
     *  根据外部用户获取当前用户所有项目信息
     * @param userOuterId  外部用户id
     * @return 数组
     */
    @Override
    public List<ProjectParentVO> selectProjectNoByUserOuterId(Long userOuterId) {
        return baseMapper.selectProjectNoByUserOuterId(userOuterId);
    }

    /**
     * 通过项目id获取项目信息
     *
     * @param projectNo
     * @return
     */
    @Override
    public SysProjectInfoVO getProjectInfoByProjectNo(String projectNo) {
        if(StrUtil.isEmpty(projectNo)){
            log.warn("项目编号不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"项目编号不能为空");
        }
        // 去redis缓存查询项目信息
        SysProjectInfoVO sysProjectInfoVo = RedisHelp.getHash(AuthCacheConstants.PROJECT_HASH, projectNo, SysProjectInfoVO.class);
        if (ObjUtil.isEmpty(sysProjectInfoVo)) {
            // 查询项目信息
            sysProjectInfoVo = baseMapper.getProjectInfoByProjectNo(projectNo);

            List<UserRedisVO> userInnerList = null;
            if (AuthHelp.getCharacteristic()) {
                userInnerList = sysUserInnerService.selectUserInnerByProjectNo(projectNo);
            }
            List<UserRedisVO> userOuterList = sysUserOuterService.selectUserOuterByProjectNo(projectNo);
            sysProjectInfoVo.setUserInnerList(userInnerList);
            sysProjectInfoVo.setUserOuterList(userOuterList);
            // 将项目信息存入redis缓存
//            RedisUtils.setHash(AuthCacheConstants.PROJECT_HASH, projectNo, sysProjectInfoVo);
        }
        return sysProjectInfoVo;
    }


    /**
     * 通过内部角色id查询项目权限
     * @param roleInnerId
     * @return
     */
    @Override
    public List<ProjectRedisVO> getRoleProjectInnerAuth(Long roleInnerId) {
        return baseMapper.getRoleProjectInnerAuth(roleInnerId);
    }

    /**
     * 通过外部角色id查询项目权限
     * @param roleOuterId
     * @return
     */
    @Override
    public List<ProjectRedisVO> getRoleProjectOuterAuth(Long roleOuterId) {
        return baseMapper.getRoleProjectOuterAuth(roleOuterId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysProject save(ProjectOaSaveDTO dto) {

        log.info("开始保存项目OA：{}",dto);
        ProjectAddDTO projectAddDto = dto.getProjectAddd_dto();
        CompanyAddDTO companyAddDto = dto.getCompanyAdd_dto();
        // 判断是否存在项目相关参数
        if (ObjUtil.isEmpty(projectAddDto)){
            log.warn("未提供项目相关参数");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"未提供项目相关参数");
        }
        // 判断是否存在公司相关参数
        if (ObjUtil.isEmpty(companyAddDto)){
            log.warn("未提供公司相关参数");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"未提供公司相关参数");
        }

        // 保存项目信息
        SysProject projectAdd = add(projectAddDto);
        // 保存公司信息
        SysCompanyInner companyAdd = sysCompanyInnerService.add(companyAddDto);
        /* 保存角色信息 */
        RoleAddDTO roleAddDto = new RoleAddDTO();
        roleAddDto.setName("sysadmin");
        roleAddDto.setType("SUPER_MANAGE");
        roleAddDto.setCompanyId(companyAdd.getId());
        /* 保存角色项目信息 */
        SysRoleInner roleAdd = sysRoleInnerService.add(roleAddDto);
        RoleProjectDTO roleProjectDto = new RoleProjectDTO();
        roleProjectDto.setRoleId(roleAdd.getId());
        roleProjectDto.setProjectNoList(Collections.singletonList(projectAdd.getProjectNo()));
        sysRoleProjectInnerService.save(roleProjectDto);
        return projectAdd;
    }


    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户  -- 旧版设计，目前保留，确定使用新版后删除
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectInnerPersonnelInfoProjectNo(String projectNo, List<String> companyIds){
        return baseMapper.selectInnerPersonnelInfoProjectNo(projectNo,companyIds);
    }

    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户  -- 旧版设计，目前保留，确定使用新版后删除
     * @param projectNo 项目编号
     * @param companyIds 公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectOuterPersonnelInfoProjectNo(String projectNo, List<String> companyIds){
        return baseMapper.selectOuterPersonnelInfoProjectNo(projectNo,companyIds);
    }

    /**
     *  项目-人员配置-分页-内部
     * @param
     * @return
     */
    @Override
    public Page<UserPersonnelPageVO> personnelPage(UserPersonnelDTO dto){
        log.info("进入项目-获取人员配置方法");

        String loginName = AuthHelp.getLoginName();
        QueryWrapper<SysUserInner> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(SysUserInner::getLoginName,loginName);
        SysUserInner sysUserInner =sysUserInnerService.getOne(queryWrapper);
        if(ObjUtil.isEmpty(sysUserInner)){
            log.warn("找不到此用户！");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"找不到此用户");
        }
        // 获取用户所有公司id
        List<String> companyInfos = null;
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            // 获取用户所有公司id
            companyInfos = sysUserInnerService.selectCompanyInfoLoginName(loginName);
            if(CollectionUtil.isEmpty(companyInfos)){
                log.warn("该用户没有公司信息！#{}",companyInfos);
                throw new BusinessException(ErrorCodeConstants.NULL, "该用户没有公司信息");
            }
        }
        dto.setCompanyIds(companyInfos);
        Page<UserPersonnelPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        return baseMapper.selectInnerPersonnelInfoPage(page,dto,loginName);
    }

    /**
     *  项目-人员配置接口调用
     * @param projectNo  项目编号
     * @return
     */
    @Override
    public PersonnelDetailVO personnel(String projectNo){
        log.info("进入项目-获取外部人员配置");
        if(ObjUtil.isEmpty(projectNo)){
            log.warn("项目编号不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"项目编号不能为空");
        }
        Long companyId = CastHelp.toLong(AuthHelp.getCompanyId());
        String loginName = AuthHelp.getLoginName();
        // 调用获取树结构
        PersonnelDetailVO personnelDetailVO = personnelTree();
        if (AuthHelp.getCharacteristic()) {
            if(StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
                personnelDetailVO.setUsersInner(innerPersonnelProject(projectNo,null));
                personnelDetailVO.setUsersOuter(outerPersonnelProject(projectNo,null));
            }else {
                personnelDetailVO.setUsersInner(innerPersonnelProject(projectNo,companyId));
            }
            return personnelDetailVO;
        }
        personnelDetailVO.setUsersOuter(outerPersonnelProject(projectNo,companyId));
        return personnelDetailVO;
    }


    /**
     *  获取人员配置树状图
     * @return
     */
    @Override
    public PersonnelDetailVO personnelTree(){
        PersonnelDetailVO personnelDetailVO = new PersonnelDetailVO();
        String loginName = AuthHelp.getLoginName();
        if (AuthHelp.getCharacteristic()) {
            log.info("进入项目-人员配置-获取内部人员信息");
            List<BaseSelectVO> companyList;
            List<BaseSelectVO> companyOuterList = null;
            List<Long> companyIds = new ArrayList<>();
            if(StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
                companyList = sysCompanyInnerService.selectCompanyInfoByLoginName(null);

                companyOuterList = sysCompanyInnerService.selectCompanyInfoByLoginName(null);
                List<BaseSelectVO> deptOuterList = sysDeptOuterService.selectDeptByCompanyIds(null);
                List<BaseSelectVO> jobOuterList = sysJobOuterService.selectJobs(null);
                List<BaseSelectVO> userOuterList = sysUserOuterService.selectUserInfoByCompanyId(null);

                companyOuterList.addAll(deptOuterList);
                companyOuterList.addAll(jobOuterList);
                companyOuterList.addAll(userOuterList);
            }else {
                // 这里查询只是为了获取拥有公司的id
                companyList = sysCompanyInnerService.selectInnerParent();
            }

            if(CollUtil.isEmpty(companyList)){
                log.warn("该用户没有公司信息");
                return null;
            }
            for(BaseSelectVO personnelVO : companyList){
                companyIds.add(Long.parseLong(personnelVO.getValue()));
            }
            // 这里查询时为了获取整体的树结构
//            companyList = sysCompanyInnerService.selectCompanyInfoByLoginName(loginName);
            List<BaseSelectVO> deptList = sysDeptInnerService.selectDeptByCompanyIds(companyIds);
            List<BaseSelectVO> jobList = sysJobInnerService.selectJobs(companyIds);
            List<BaseSelectVO> userList = sysUserInnerService.selectUserInfoByCompanyId(companyIds);

            deptList.addAll(companyList);
            deptList.addAll(jobList);
            deptList.addAll(userList);

            personnelDetailVO.setAllUserInner(deptList);
            personnelDetailVO.setAllUserOuter(companyOuterList);
            return personnelDetailVO;
        }

        log.info("进入项目-人员配置-获取外部人员信息");
        List<Long> companyOuterIds = new ArrayList<>();
        List<BaseSelectVO> companyOuterList =  sysCompanyOuterService.selectOuterParent();

        if(CollUtil.isEmpty(companyOuterList)){
            log.warn("该用户没有公司信息");
            return null;
        }
        for(BaseSelectVO personnelVO:companyOuterList){
            companyOuterIds.add(Long.parseLong(personnelVO.getValue()));
        }
        // 这里查询时为了获取整体的树结构
//        companyOuterList = sysCompanyInnerService.selectCompanyInfoByLoginName(loginName);
        List<BaseSelectVO> deptOuterList = sysDeptOuterService.selectDeptByCompanyIds(companyOuterIds);
        List<BaseSelectVO> jobOuterList = sysJobOuterService.selectJobs(companyOuterIds);
        List<BaseSelectVO> userOuterList = sysUserOuterService.selectUserInfoByCompanyId(companyOuterIds);

        deptOuterList.addAll(companyOuterList);
        deptOuterList.addAll(jobOuterList);
        deptOuterList.addAll(userOuterList);
        personnelDetailVO.setAllUserOuter(deptOuterList);
        return personnelDetailVO;
    }
    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少内部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    @Override
    public List<UserDetailList> innerPersonnelProject(String projectNo,Long companyId){
        return baseMapper.innerPersonnelProject(projectNo,companyId);
    }
    /**
     *  根据 项目编号、公司id信息 获取当前项目有多少外部用户
     * @param projectNo 项目编号
     * @param companyId 公司id
     * @return
     */
    @Override
    public List<UserDetailList> outerPersonnelProject(String projectNo,Long companyId){
        return baseMapper.outerPersonnelProject(projectNo,companyId);
    }

    /**
     *  项目-人员配置-分页-外部
     * @param
     * @return
     */
    @Override
    public Page<UserPersonnelPageVO> personnelOuterPage(UserPersonnelDTO dto){
        log.info("进入项目-获取人员配置方法");

        String loginName = AuthHelp.getLoginName();
        // 获取用户所有公司id
        List<String> companyInfos = null;
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            // 获取用户所有公司id
            companyInfos = sysUserOuterService.selectOuterCompanyInfoLoginName(loginName);
            if(CollectionUtil.isEmpty(companyInfos)){
                log.warn("该用户没有公司信息！#{}",companyInfos);
                throw new BusinessException(ErrorCodeConstants.NULL, "该用户没有公司信息");
            }
        }
        dto.setCompanyIds(companyInfos);
        Page<UserPersonnelPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        return baseMapper.outerPersonnelPage(page,dto,loginName);
    }

    /**
     *  项目-人员配置保存
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelSave(SavePersonnelDTO dto){
        log.info("进入项目-获取人员配置方法");

        if(AuthHelp.getCharacteristic()){
            List<SysUserProjectInner> inners = new ArrayList<>();
            SysUserProjectInner sysUserProjectInner = null;

            QueryWrapper<SysUserProjectInner> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(SysUserProjectInner::getProjectNo,dto.getProjectNo());

            // 判断用户id 为空数组 则全部清除
            if(dto.getUserIds() != null && dto.getUserIds().size() == 0){
                sysUserProjectInnerService.remove(queryWrapper);
                RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
                return true;
            }

            for(long userId:dto.getUserIds()){
                sysUserProjectInner = new SysUserProjectInner();
                sysUserProjectInner.setUserInnerId(userId);
                sysUserProjectInner.setProjectNo(dto.getProjectNo());
                inners.add(sysUserProjectInner);
                // 清除内部用户缓存
                RedisHelp.del(StrHelp.format("{}_{}", AuthCacheConstants.USER_INNER_HASH, Convert.toStr(userId)));
            }

            sysUserProjectInnerService.remove(queryWrapper);
            log.warn("批量删除内部用户项目信息成功!#{}",sysUserProjectInner);

            sysUserProjectInnerService.saveBatch(inners);
            log.warn("批量添加内部用户项目信息失败！#{}",inners);
            return true;
        }
        List<SysUserProjectOuter> outers = new ArrayList<>();
        SysUserProjectOuter sysUserProjectOuter = null;

        QueryWrapper<SysUserProjectOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysUserProjectOuter::getProjectNo,dto.getProjectNo());

        // 判断用户id 为空数组 则全部清除
        if(dto.getUserIds() != null && dto.getUserIds().size() == 0){
            sysUserProjectOuterService.remove(queryWrapper);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            return true;
        }

        for(long userId:dto.getUserIds()){
            sysUserProjectOuter = new SysUserProjectOuter();
            sysUserProjectOuter.setUserOuterId(userId);
            sysUserProjectOuter.setProjectNo(dto.getProjectNo());
            outers.add(sysUserProjectOuter);
            // 清除内部用户缓存
            RedisHelp.del(StrHelp.format("{}_{}", AuthCacheConstants.USER_INNER_HASH, Convert.toStr(userId)));
        }


        sysUserProjectOuterService.remove(queryWrapper);
        log.warn("批量删除外部用户项目信息成功！#{}",outers);


        sysUserProjectOuterService.saveBatch(outers);
        log.warn("批量添加外部用户项目信息成功！#{}",outers);
        return true;
    }


    /**
     *  项目-内部人员配置删除
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelInnerDelete(DeletePersonnelDTO dto){
        QueryWrapper<SysUserProjectInner> sysUserProjectInner = new QueryWrapper<>();
        sysUserProjectInner.lambda()
                .eq(SysUserProjectInner::getUserInnerId, dto.getUserId())
                .eq(SysUserProjectInner::getProjectNo, dto.getProjectNo());
        sysUserProjectInnerService.remove(sysUserProjectInner);
        return true;
    }

    /**
     *  项目-外部人员配置删除
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelOuterDelete(DeletePersonnelDTO dto){
        QueryWrapper<SysUserProjectInner> sysUserProjectInner = new QueryWrapper<>();
        sysUserProjectInner.lambda()
                .eq(SysUserProjectInner::getUserInnerId, dto.getUserId())
                .eq(SysUserProjectInner::getProjectNo, dto.getProjectNo());
        sysUserProjectInnerService.remove(sysUserProjectInner);
        return true;
    }
    /**
     *  添加项目-获取当前登录用户所有项目信息
     * @return
     */
    @Override
    public List<BaseSelectVO> parentList(){
        log.info("进入添加项目-获取用户所有项目信息");
        Long userId = CastHelp.toLong(AuthHelp.getUserId());
        List<ProjectParentVO> list = null;
        if(AuthHelp.getCharacteristic()){
            if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
                list = selectProjectNoByUserInnerId(userId);
            }else {
                list = selectProjectNoByUserInnerId(null);
            }
            return list.stream().map(this::cast).toList();
        }
        list = selectProjectNoByUserOuterId(userId);
        return list.stream().map(this::cast).toList();
    }


    /**
     * 获取当前登陆用户所有项目编号
     * @param characteristic 内外部标识
     * @param userId 用户ID
     * @param loginName 登录名
     * @return 项目编码列表
     */
    @Override
    public List<String> selectProjectNo(Boolean characteristic, Long userId, String loginName) {
        
        log.info("进入添加项目-获取用户所有项目信息");
        List<ProjectParentVO> list;
        if (characteristic) {
            if (!StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
                list = selectProjectNoByUserInnerId(userId);
            } else {
                list = selectProjectNoByUserInnerId(null);
            }
        } else {
            list = selectProjectNoByUserOuterId(userId);
        }
        return list.stream().map(ProjectParentVO::getProjectNo).toList();
    }

    /**
     *  添加项目-获取用户所有项目信息
     * @return
     */
    @Override
    public List<ProjectParentVO> projectInfo(UserDTO dto) {
        log.info("进入添加项目-获取用户所有项目信息");
        if (ObjUtil.isEmpty(dto.getUserId())) {
            log.warn("用户id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "用户id不能为空");
        }
        if (AuthHelp.getCharacteristic()) {
            return selectProjectNoByUserInnerId(dto.getUserId());
        }
        return selectProjectNoByUserOuterId(dto.getUserId());
    }


    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        if (obj instanceof ProjectParentVO projectParentVO) {
            vo.setValue(Convert.toStr(projectParentVO.getId()));
            vo.setLabel(projectParentVO.getName());
            vo.setParentValue(Convert.toStr(projectParentVO.getParentId()));
            vo.setParentValues(projectParentVO.getParentIds());
        }
        return vo;
    }

}
