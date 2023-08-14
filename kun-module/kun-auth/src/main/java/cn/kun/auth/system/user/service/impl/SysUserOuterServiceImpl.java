package cn.kun.auth.system.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.role.service.SysRoleOuterService;
import cn.kun.auth.system.user.entity.dto.UserAddDTO;
import cn.kun.auth.system.user.entity.dto.UserEditDTO;
import cn.kun.auth.system.user.entity.dto.UserPageDTO;
import cn.kun.auth.system.user.entity.dto.UserPasswordDTO;
import cn.kun.auth.system.user.entity.dto.UserPermissDTO;
import cn.kun.auth.system.user.entity.po.SysUserDetailOuter;
import cn.kun.auth.system.user.entity.po.SysUserInner;
import cn.kun.auth.system.user.entity.po.SysUserJobOuter;
import cn.kun.auth.system.user.entity.po.SysUserMenuOuter;
import cn.kun.auth.system.user.entity.po.SysUserOuter;
import cn.kun.auth.system.user.entity.po.SysUserProjectOuter;
import cn.kun.auth.system.user.entity.po.SysUserRoleOuter;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.UserDetailListVO;
import cn.kun.auth.system.user.entity.vo.UserJobInfoVO;
import cn.kun.auth.system.user.entity.vo.UserMenuPermissVO;
import cn.kun.auth.system.user.entity.vo.UserPageVO;
import cn.kun.auth.system.user.entity.vo.UserProjectPermissVO;
import cn.kun.auth.system.user.entity.vo.UserRolePermissVO;
import cn.kun.auth.system.user.mapper.SysUserOuterMapper;
import cn.kun.auth.system.user.service.SysUserDetailOuterService;
import cn.kun.auth.system.user.service.SysUserOuterService;
import cn.kun.auth.system.user.service.SysUserRoleOuterService;
import cn.kun.auth.system.company.service.SysCompanyOuterService;
import cn.kun.auth.system.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.system.job.service.SysJobOuterService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.system.menu.service.SysMenuService;
import cn.kun.auth.system.project.service.SysProjectService;
import cn.kun.auth.system.user.service.SysUserInnerService;
import cn.kun.auth.system.user.service.SysUserJobOuterService;
import cn.kun.auth.system.user.service.SysUserMenuOuterService;
import cn.kun.auth.system.user.service.SysUserProjectOuterService;
import cn.kun.base.api.entity.auth.dto.UserListDTO;
import cn.kun.base.api.entity.auth.vo.CompanyRedisVO;
import cn.kun.base.api.entity.auth.vo.DeptRedisVO;
import cn.kun.base.api.entity.auth.vo.JobRedisVO;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.RoleInfoByUserIdVO;
import cn.kun.base.api.entity.auth.vo.SysUserCacheInfoVO;
import cn.kun.base.api.entity.auth.vo.SysUserInfoVO;
import cn.kun.base.api.entity.auth.vo.UserDetailVO;
import cn.kun.base.api.entity.auth.vo.UserRedisVO;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.api.service.system.RemoteFileService;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.AuthDictTypeConstants;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.security.util.AuthHelp;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 外部用户表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysUserOuterServiceImpl extends ServiceImpl<SysUserOuterMapper, SysUserOuter> implements SysUserOuterService {

    @Resource
    private SysJobOuterService sysJobOuterService;
    @Resource
    private SysRoleOuterService sysRoleOuterService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysProjectService sysProjectService;
    @Resource
    private SysUserDetailOuterService sysUserDetailOuterService;
    @Resource
    private SysUserJobOuterService sysUserJobOuterService;
    @Resource
    private SysUserProjectOuterService sysUserProjectOuterService;
    @Resource
    private SysUserMenuOuterService sysUserMenuOuterService;
    @Resource
    private SysUserRoleOuterService sysUserRoleOuterService;
    @Resource
    private BaseDictService baseDictService;
    @Resource
    private SysCompanyOuterService sysCompanyOuterService;
    @Resource
    private RemoteFileService remoteFileService;
    @Resource
    private SysUserInnerService sysUserInnerService;



    @Override
    public Page<UserPageVO> page(UserPageDTO dto) {

        log.info("进入外部用户分页");
        if(ObjUtil.isEmpty(dto.getCompanyId())){
            // 判断是超级用户还是普通用户
            String loginName = AuthHelp.getLoginName();
            if (StrUtil.equals(AuthConstants.SYS_ADMIN,loginName) && ObjUtil.isEmpty(dto.getCompanyId())){
                dto.setCompanyId(null);
            }
        }
        // 获取当前登陆人的公司信息
        List<BaseSelectVO> companyList = sysCompanyOuterService.selectOuterParent();
        List<String> companyIds = companyList.stream().map(BaseSelectVO::getValue).collect(Collectors.toList());
        // 取出id 放入条件
        if(CollUtil.isNotEmpty(companyIds)){
            dto.setCompanyList(companyIds);
        }

        Page<UserPageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        Page<UserPageVO> voList = baseMapper.selectPage(page, dto);
        for (UserPageVO vo : voList.getRecords()) {
            vo.setStatus(baseDictService.getLabel(AuthDictTypeConstants.USER_STATUS,vo.getStatus()));
        }
        return voList;
    }

    /**
     * 根据用户id集合获取用户信息
     * @param dto 用户id集合
     * @return
     */
    @Override
    public List<UserDetailVO> list(UserListDTO dto) {
        log.info("根据用户id集合获取用户信息");
        // 分页查询
        return baseMapper.list(dto);
    }

    @Override
    public UserDetailVO detail(String id) {

        log.info("进入外部用户详情");
        if(ObjUtil.isEmpty(id)){
            log.warn("用户id不能为空#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"用户编号不能为空");
        }
        SysUserOuter sysUserOuter = getById(id);

        if (ObjUtil.isEmpty(sysUserOuter)) {
            log.warn("找不到该用户数据#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户数据");
        }
        // 复制对象
        UserDetailVO vo = new UserDetailVO();
        BeanUtil.copyProperties(sysUserOuter, vo);

        QueryWrapper<SysUserDetailOuter> qw = new QueryWrapper<>();
        qw.lambda()
                .eq(SysUserDetailOuter::getUserOuterId,sysUserOuter.getId());
        SysUserDetailOuter sysUserDetailOuter = sysUserDetailOuterService.getOne(qw);
        if (ObjUtil.isEmpty(sysUserDetailOuter)) {
            log.warn("找不到该用户详情数据#{}",sysUserDetailOuter);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户详情数据");
        }
        BeanUtil.copyProperties(sysUserDetailOuter, vo);
        vo.setId(sysUserOuter.getId());
        UserJobInfoVO userJobInfoVO = getUserJobInfo(Long.parseLong(id));
        // 组装公司、部门、职位信息数据
        if(ObjUtil.isNotEmpty(userJobInfoVO)){
            vo.setDeptId(userJobInfoVO.getDeptId());
            vo.setJobInnerId(userJobInfoVO.getJobId());
            String parents = null;
            if (ObjUtil.equals(Long.parseLong("0"), userJobInfoVO.getCompanyParentId())) {
                parents = userJobInfoVO.getCompanyId().toString() + ",";
            } else if (StrUtil.isNotBlank(userJobInfoVO.getCompanyParentIds())) {
                if(StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
                    parents = userJobInfoVO.getCompanyParentIds().substring(2) + userJobInfoVO.getCompanyId() + ",";
                }else {
                    parents = userJobInfoVO.getCompanyId() + ",";
                }
            }

            if (ObjUtil.equals(Long.parseLong("0"), userJobInfoVO.getDeptParentId())) {
                parents += userJobInfoVO.getDeptId().toString() + ",";
            } else if (StrUtil.isNotBlank(userJobInfoVO.getDeptParentIds())) {
                parents += userJobInfoVO.getDeptParentIds().substring(2) + userJobInfoVO.getDeptId();
            }
            parents += userJobInfoVO.getJobId();
            vo.setUserParentIds(parents);
        }
        if(ObjUtil.isNotNull(vo.getPhoto())){
            vo.setPath(remoteFileService.getPathById(vo.getPhoto()).getData());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserOuter add(UserAddDTO dto) {

        log.info("外部用户-进入添加方法");
        QueryWrapper<SysUserOuter> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserOuter::getLoginName, dto.getLoginName());
        List<SysUserOuter> list = list(wrapper);
        if (!CollUtil.isEmpty(list)) {
            log.warn("登录名已存在!登录名为：{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.REPEAT, "登录名已存在！");
        }else {
            QueryWrapper<SysUserInner> queryUserInner = new QueryWrapper<>();
            queryUserInner.lambda().eq(SysUserInner::getLoginName, dto.getLoginName());
            List<SysUserInner> userInnerList = sysUserInnerService.list(queryUserInner);
            if (CollUtil.isNotEmpty(userInnerList)) {
                log.warn("登录名已存在!登录名为：{}", dto.getLoginName());
                throw new BusinessException(ErrorCodeConstants.REPEAT, "登录名已存在！");
            }
        }

        // 复制对象
        SysUserOuter sysUserOuter = new SysUserOuter();
        BeanUtil.copyProperties(dto, sysUserOuter);
        // 加密密码
        sysUserOuter.setPassword(new BCryptPasswordEncoder().encode(sysUserOuter.getPassword()));
        // 头像
        if (ObjUtil.isNotNull(dto.getPhoto())) {
            sysUserOuter.setPath(remoteFileService.getPathById(dto.getPhoto()).getData());
        }
        // 添加对象
        boolean flag = save(sysUserOuter);
        if (!flag) {
            log.warn("添加外部用户失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("外部用户-添加成功");

        // 复制对象
        SysUserDetailOuter sysUserDetailOuter = new SysUserDetailOuter();
        BeanUtil.copyProperties(dto, sysUserDetailOuter);
        sysUserDetailOuter.setUserOuterId(sysUserOuter.getId());
        // 添加用户详情对象
        boolean flagDetail = sysUserDetailOuterService.save(sysUserDetailOuter);
        if (!flagDetail) {
            log.info("添加外部用户详情失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("外部用户-添加详情成功");

        SysUserJobOuter sysUserJobOuter = new SysUserJobOuter();
        sysUserJobOuter.setUserOuterId(sysUserOuter.getId());
        sysUserJobOuter.setJobOuterId(dto.getJobId());
        boolean flagJob = sysUserJobOuterService.save(sysUserJobOuter);
        if (!flagJob) {
            log.info("添加外部用户职位信息失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("外部用户-添加成功");
        return sysUserOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserOuter edit(UserEditDTO dto) {

        log.info("外部用户-修改方法进入");
        // 复制对象
        SysUserOuter sysUserOuter = getById(dto.getId());
        if (ObjUtil.isEmpty(sysUserOuter)) {
            log.warn("用户信息不存在！：{}", sysUserOuter);
            throw new BusinessException(ErrorCodeConstants.NULL, "用户信息不存在！");
        }
        BeanUtil.copyProperties(dto, sysUserOuter);
        // 头像
        if (ObjUtil.isNotNull(dto.getPhoto())) {
            sysUserOuter.setPath(remoteFileService.getPathById(dto.getPhoto()).getData());
        }
        // 修改对象
        boolean flag = updateById(sysUserOuter);
        if (!flag) {
            log.warn("外部用户-修改失败：#{}",sysUserOuter);
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败！");
        }
        log.info("外部用户-修改成功");
        // 复制对象
        QueryWrapper<SysUserDetailOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserDetailOuter::getUserOuterId, dto.getId());
        SysUserDetailOuter userDetailInfo = new SysUserDetailOuter();
        BeanUtil.copyProperties(dto, userDetailInfo);
        // 修改用户详情
        sysUserDetailOuterService.update(userDetailInfo,queryWrapper);
        // 删除缓存
//        if (!userDetail) {
//            log.warn("外部用户详情-修改失败");
//            throw  new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败！");
//        }

        // 删除之前管理的职位信息
        QueryWrapper<SysUserJobOuter> sysUserJobOuterQueryWrapper = new QueryWrapper<>();
        sysUserJobOuterQueryWrapper.lambda()
                .eq(SysUserJobOuter::getUserOuterId,dto.getId());
        sysUserJobOuterService.remove(sysUserJobOuterQueryWrapper);
        // 保存最新添加的用户职位
        SysUserJobOuter saveUserJob = new SysUserJobOuter();
        saveUserJob.setJobOuterId(dto.getJobId());
        saveUserJob.setUserOuterId(dto.getId());
        sysUserJobOuterService.save(saveUserJob);
        log.info("内部用户-修改成功-更新缓存");
        // 更新缓存信息
        selectUserOuterPowerInfo(sysUserOuter.getId());
        return sysUserOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        log.info("外部用户-删除用户方法进入");
        // 复制对象
        SysUserOuter sysUserOuter = getById(id);
        if (ObjUtil.isEmpty(sysUserOuter)) {
            log.warn("用户信息不存在！：{}", sysUserOuter);
            throw new BusinessException(ErrorCodeConstants.NULL, "用户信息不存在！");
        }
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, sysUserOuter.getLoginName())) {
            log.warn("超级管理员不允许删除！：{}", sysUserOuter);
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "超级管理员不允许删除！");
        }
        boolean flag = removeById(id);
        if (!flag) {
            log.warn("外部用户-删除失败：#{}",sysUserOuter);
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"删除失败！");
        }
        log.info("外部用户-删除成功");
        // 复制对象
        QueryWrapper<SysUserDetailOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserDetailOuter::getUserOuterId, id);
        // 修改用户详情
        sysUserDetailOuterService.remove(queryWrapper);
        // 更新缓存信息
        log.info("外部用户-删除成功-删除缓存");
        RedisHelp.delHash(AuthCacheConstants.USER_OUTER_HASH, Convert.toStr(id));

    }

    /**
     * 通过用户id查询用户缓存信息
     *
     * @param userId
     * @return
     */
    @Override
    public SysUserCacheInfoVO getUserCacheInfoByUserId(Long userId) {

        // 去redis获取用户权限信息
        Object obj = RedisHelp.getHash(AuthCacheConstants.USER_OUTER_HASH);
        SysUserCacheInfoVO sysUserCacheInfoVo = null;
        if (obj instanceof SysUserCacheInfoVO) {
            sysUserCacheInfoVo = (SysUserCacheInfoVO) obj;
        }
        if (ObjUtil.isNull(sysUserCacheInfoVo)) {
            return selectUserOuterPowerInfo(userId);
        }
        return sysUserCacheInfoVo;
    }

    /**
     * 获取内部用户所有权限信息并存储缓存
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public SysUserCacheInfoVO selectUserOuterPowerInfo(Long userId) {
        List<ProjectParentVO> sysUserProjectAuth = null;
        List<String> sysUserMenuAuth = null;
        SysUserCacheInfoVO sysUserCacheInfoVo = new SysUserCacheInfoVO();
        // 通过内部用户id查询内部用户信息
        SysUserInfoVO userOuterInfo = baseMapper.getUserInfoByUserId(userId);
        if(ObjUtil.isEmpty(userOuterInfo)){
            log.warn("找不到该用户信息");
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户信息");
        }
        // 通过内部用户id查询职位、部门、公司信息
        List<JobInfoByUserIdVO> jobOuterInfoList = sysJobOuterService.getJobOuterInfoByUserOuterId(userId);
        List<JobRedisVO> jobRedisVOList = new ArrayList<>();
        // 组装公司、部门、职位参数
        DeptRedisVO deptRedisVO = null;
        CompanyRedisVO companyRedisVO = null;
        JobRedisVO jobRedisVO = null;
        for (JobInfoByUserIdVO jobInfoByUserIdVO : jobOuterInfoList) {
            jobRedisVO = new JobRedisVO();
            deptRedisVO = new DeptRedisVO();
            companyRedisVO = new CompanyRedisVO();
            deptRedisVO.setDeptId(jobInfoByUserIdVO.getDeptId());
            deptRedisVO.setDeptName(jobInfoByUserIdVO.getDeptName());
            companyRedisVO.setCompanyId(jobInfoByUserIdVO.getCompanyId());
            companyRedisVO.setCompanyName(jobInfoByUserIdVO.getCompanyName());
            jobRedisVO.setJobId(jobInfoByUserIdVO.getJobId());
            jobRedisVO.setJobName(jobInfoByUserIdVO.getJobName());
            jobRedisVO.setDeptRedisVO(deptRedisVO);
            jobRedisVO.setCompanyRedisVO(companyRedisVO);
            jobRedisVOList.add(jobRedisVO);
        }
        // 通过内部用户id查询角色信息
        List<RoleInfoByUserIdVO> roleOuterInfoList = sysRoleOuterService.getRoleOuterByRoleOuterId(userId);// 判断是否为超级用户
        if (ObjUtil.isNotEmpty(userOuterInfo)) {
            if (ObjUtil.equals(AuthConstants.SYS_ADMIN, userId)) {
                // 如果是超级用户，查询所有的项目权限信息
                sysUserProjectAuth = sysProjectService.selectProjectNoByUserOuterId(null);
                // 如果是超级用户，查询所有的菜单信息
                sysUserMenuAuth = sysMenuService.selectPermsOuter(null);
            } else {
                // 如果是普通用户，查询当前用户拥有的项目
                sysUserProjectAuth = sysProjectService.selectProjectNoByUserOuterId(userId);
                // 如果是普通用户，查询当前用户拥有的菜单权限
                sysUserMenuAuth = sysMenuService.selectPermsOuter(userId);
            }
            if (ObjUtil.isEmpty(sysUserCacheInfoVo)) {
                sysUserCacheInfoVo = new SysUserCacheInfoVO();
            }
            // 赋值用户及权限信息
            sysUserCacheInfoVo.setUserInfo(userOuterInfo);
            sysUserCacheInfoVo.setJobInfo(jobRedisVOList);
            sysUserCacheInfoVo.setRoleInfo(roleOuterInfoList);
            sysUserCacheInfoVo.setSysUserProjectAuth(sysUserProjectAuth);
            sysUserCacheInfoVo.setSysUserMenuAuth(sysUserMenuAuth);

            RedisHelp.setHash(AuthCacheConstants.USER_OUTER_HASH, String.valueOf(userOuterInfo.getUserId()), sysUserCacheInfoVo);
        }
        return sysUserCacheInfoVo;
    }

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息、部门信息、职位信息的外部用户
     * @param projectNo  项目编号
     * @return
     */
    @Override
    public UserDetailListVO personnelDetail(String projectNo) {
        log.info("进入外部用户-获取人员配置方法");

        String loginName = AuthHelp.getLoginName();
        if(ObjUtil.isEmpty(loginName)){
            log.warn("登录名不允许为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"登录名不允许为空");
        }
        QueryWrapper<SysUserOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(SysUserOuter::getLoginName,loginName);
        SysUserOuter sysUserOuter = getOne(queryWrapper);
        if(ObjUtil.isEmpty(sysUserOuter)){
            log.warn("找不到此用户！");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"找不到此用户");
        }
        // 获取用户所有公司id
        List<String> companyInfos = null;
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            // 获取用户所有公司id
            companyInfos = selectOuterCompanyInfoLoginName(loginName);
            if(CollectionUtil.isEmpty(companyInfos)){
                log.warn("该用户没有公司信息！#{}",companyInfos);
                throw new BusinessException(ErrorCodeConstants.NULL, "该用户没有公司信息");
            }
        }
        // 获取该用户所拥有公司的所有用户
        List<UserDetailList> userDetails = selectOuterPersonnelInfo(companyInfos);
        // 获取以授权该项目的用户
        List<UserDetailList> userProjects = sysProjectService.selectOuterPersonnelInfoProjectNo(projectNo,companyInfos);

        // 取差集
//        List<UserDetailList> userLists = userDetailListVos.stream().filter(item -> !userProjects.contains(item)).collect(Collectors.toList());
        // 组装用户参数----用户项目关联关系

        UserDetailListVO userDetailListVo = new UserDetailListVO();
//        List<JSONObject> userJsonList = new ArrayList<>();
//        JSONObject userJson = new JSONObject();
//        userJson.put("userJson",userDetailListVos);
//        userJsonList.add(userJson);
        // 组装用户参数----角色项目关联关系
//        List<JSONObject> projectJsonList = new ArrayList<>();
//        JSONObject projectJson = new JSONObject();
//        projectJson.put("userProjects",userProjects);
//        projectJsonList.add(projectJson);
        userDetailListVo.setUsers(userDetails);
        userDetailListVo.setUserProjects(userProjects);
        return userDetailListVo;
    }

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    @Override
    public List<String> selectOuterCompanyInfoLoginName(String loginName) {
        return baseMapper.selectOuterCompanyInfoLoginName(loginName);
    }

    /**
     *  人员配置：获取当前外部用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param companyIds  公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectOuterPersonnelInfo(List<String> companyIds) {
        return baseMapper.selectOuterPersonnelInfo(companyIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean savePermiss(UserPermissDTO dto) {

        log.info("用户开始授权-项目、菜单、角色");
        // 判断用户是否有授权
        Long userId = dto.getUserId();
        List<String> projectNoList = dto.getProjectNoList();
        List<Long> menuIdList = dto.getMenuIdList();
        List<Long> roleIdList = dto.getRoleIdList();

        QueryWrapper<SysUserProjectOuter> userProjectQuery = new QueryWrapper<>();
        userProjectQuery.lambda().eq(SysUserProjectOuter::getUserOuterId,userId);
        QueryWrapper<SysUserMenuOuter> userMenuQuery  = new QueryWrapper<>();
        userMenuQuery.lambda().eq(SysUserMenuOuter::getUserOuterId,userId);
        QueryWrapper<SysUserRoleOuter> userRoleQuery  = new QueryWrapper<>();
        userRoleQuery.lambda().eq(SysUserRoleOuter::getUserOuterId,userId);

        // 判断是否取消所有授权
//        if (CollUtil.isEmpty(projectNoList) && CollUtil.isEmpty(menuIdList) && CollUtil.isEmpty(roleIdList)){
//            log.warn("用户取消了所有授权");
//            // 删除数据表
//            sysUserProjectOuterService.remove(userProjectQuery);
//            sysUserMenuOuterService.remove(userMenuQuery);
//            sysUserRoleOuterService.remove(userRoleQuery);
//            // 获取用户缓存信息
//            selectUserOuterPowerInfo(userId);
//            return true;
//        }

        /* 先删除用户项目表数据，再新增用户项目 */
        if (CollUtil.isNotEmpty(projectNoList)) {
            // 删除用户项目
            sysUserProjectOuterService.remove(userProjectQuery);
            List<SysUserProjectOuter> list = new ArrayList<>();
            for (String projectNo : projectNoList) {
                SysUserProjectOuter sysUserProjectOuter = new SysUserProjectOuter();
                sysUserProjectOuter.setUserOuterId(userId);
                sysUserProjectOuter.setProjectNo(projectNo);
                list.add(sysUserProjectOuter);
            }
            // 保存用户项目
            boolean addBatch = sysUserProjectOuterService.saveBatch(list);
            if (addBatch) {
                log.info("用户项目-成功保存用户项目");
            }
        }else if (projectNoList != null && projectNoList.size() < 1) {
            // 删除用户角色
            sysUserProjectOuterService.remove(userProjectQuery);
            log.info("用户项目-成功保存用户项目");
        }

        /* 先删除用户菜单表数据，再新增用户菜单 */
        if (CollUtil.isNotEmpty(menuIdList)) {
            // 删除用户菜单
            sysUserMenuOuterService.remove(userMenuQuery);
            List<SysUserMenuOuter> list = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysUserMenuOuter sysUserMenuOuter = new SysUserMenuOuter();
                sysUserMenuOuter.setUserOuterId(userId);
                sysUserMenuOuter.setMenuId(menuId);
                list.add(sysUserMenuOuter);
            }
            // 保存用户菜单
            boolean addBatch = sysUserMenuOuterService.saveBatch(list);
            if (addBatch) {
                log.info("用户菜单-成功保存用户菜单");
            }
        }else if (menuIdList != null && menuIdList.size() < 1) {
            // 删除用户角色
            sysUserMenuOuterService.remove(userMenuQuery);
            log.info("用户菜单-成功保存用户菜单");
        }

        /* 先删除用户角色表数据，再新增用户角色 */
        if (CollUtil.isNotEmpty(roleIdList)) {
            // 删除用户角色
            sysUserRoleOuterService.remove(userRoleQuery);
            List<SysUserRoleOuter> list = new ArrayList<>();
            for (Long roleId : roleIdList) {
                SysUserRoleOuter sysUserRoleOuter = new SysUserRoleOuter();
                sysUserRoleOuter.setUserOuterId(userId);
                sysUserRoleOuter.setRoleOuterId(roleId);
                list.add(sysUserRoleOuter);
            }
            // 保存用户角色
            boolean addBatch = sysUserRoleOuterService.saveBatch(list);
            if (addBatch) {
                log.info("用户角色-成功保存用户角色");
            }
        }else if (roleIdList != null && roleIdList.size() < 1) {
            // 删除用户角色
            sysUserRoleOuterService.remove(userRoleQuery);
            log.info("用户角色-成功保存用户角色");
        }

        // 获取用户缓存信息
        selectUserOuterPowerInfo(userId);
        return true;
    }

    /**
     *  根据公司id获取用户信息
     * @param companyIds
     * @return
     */
    @Override
    public List<BaseSelectVO> selectUserInfoByCompanyId(List<Long> companyIds){
        return baseMapper.selectUserInfoByCompanyId(companyIds);
    }

    /**
     *  根据公司id获取用户信息-角色树状图
     * @param companyIds
     * @return
     */
    @Override
    public List<BaseSelectVO> selectUserRoleByCompanyIds(List<Long> companyIds){
        return baseMapper.selectUserRoleByCompanyIds(companyIds);
    }

    /**
     *  根据用户id获取职位、部门、公司信息
     * @param userId 用户id
     * @return
     */
    @Override
    public UserJobInfoVO getUserJobInfo(Long userId){
        return baseMapper.getUserJobInfo(userId);
    }


    /**
     * 根据项目编号获取项目下的所有外部用户
     *
     * @param projectNo
     * @return
     */
    @Override
    public List<UserRedisVO> selectUserOuterByProjectNo(String projectNo){
        return baseMapper.selectUserOuterByProjectNo(projectNo);
    }

    /**
     * 通过外部角色id查询当前角色是否绑定用户
     * @param roleOuterId
     * @return
     */
    @Override
    public List<Long> selectUserIdsByRoleOuterId(Long roleOuterId) {
        return baseMapper.selectUserIdsByRoleOuterId(roleOuterId);
    }

    /**
     * 查询用户-菜单权限列表
     * @param dto
     * @return
     */
    @Override
    public UserMenuPermissVO selectMenuListByUserId(UserMenuPermissDTO dto) {

        log.info("进入获取菜单列表信息");
        UserMenuPermissVO vo = new UserMenuPermissVO();
        List<BaseSelectVO> loginUserVo = null;

        // 外部用户-菜单授权查询
        UserMenuPermissDTO userMenuPermissDTO = new UserMenuPermissDTO();
        userMenuPermissDTO.setServiceCode(dto.getServiceCode());
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            userMenuPermissDTO.setUserId(null);
            loginUserVo  = sysMenuService.menuListByUserOuterId(userMenuPermissDTO);
        }
        else{
            userMenuPermissDTO.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            loginUserVo = sysMenuService.menuListByUserOuterId(userMenuPermissDTO);
        }
        // 查询当前用户-菜单权限列表
        List<BaseSelectVO> userVo = sysMenuService.outerMenuListInfo(dto.getUserId());

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
    }

    @Override
    public UserProjectPermissVO selectProjectListByUserId(Long userId) {

        log.info("开始查询用户-项目权限");
        UserProjectPermissVO vo = new UserProjectPermissVO();
        List<ProjectParentVO> loginVoList = null;
        // 判断是否为管理员
        if(StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            loginVoList = sysProjectService.selectProjectNoByUserOuterId(null);
        }else {
            loginVoList = sysProjectService.selectProjectNoByUserOuterId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        List<BaseSelectVO> loginUserVo = loginVoList.stream().map(this::cast).toList();

        // 查询当前用户的项目权限
        List<ProjectParentVO> userVoList = sysProjectService.selectProjectNoByUserOuterId(userId);
        List<BaseSelectVO> userVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
    }

    /**
     * 查询用户-角色权限列表
     * @param userId
     * @return
     */
    @Override
    public UserRolePermissVO selectRoleListByUserId(Long userId) {

        log.info("开始查询用户-角色权限");
        String loginName = AuthHelp.getLoginName();
        UserRolePermissVO vo = new UserRolePermissVO();
        List<BaseSelectVO> loginUserVo = null;
        List<BaseSelectVO> personnelVOList = null; // 获取当前登陆人的所有公司信息
        // 判断是否是管理员用户
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            loginUserVo = sysRoleOuterService.selectListByUserId(null);
            personnelVOList = sysCompanyOuterService.selectCompanyInfoByLoginName(null);
            loginUserVo.addAll(personnelVOList);

        } else {
            loginUserVo = sysRoleOuterService.selectListByUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            List<BaseSelectVO> companyInfo = sysCompanyOuterService.selectOuterParent();
            loginUserVo.addAll(companyInfo);
        }
        // 获取当前登陆人的所有角色
        List<BaseSelectVO> userVo = sysRoleOuterService.selectListByUserId(userId);

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
    }

    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        JSONObject expand = null;
        if (obj instanceof ProjectParentVO projectParentVO) {
            expand = new JSONObject();
            vo.setValue(projectParentVO.getProjectNo());
            vo.setLabel(projectParentVO.getName());
            vo.setParentValue(Convert.toStr(projectParentVO.getParentId()));
            vo.setParentValues(projectParentVO.getParentIds());
            expand.put("projectNo",projectParentVO.getProjectNo());
            vo.setExpand(expand);
        }
        return vo;
    }

    /**
     *  修改用户密码
     * @param dto
     * @return
     */
    @Override
    public Boolean updateUserPassword(UserPasswordDTO dto) {
        log.info("进入修改用户密码");
        SysUserOuter sysUserOuter = null;
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            sysUserOuter = getById(AuthHelp.getUserId());
            Boolean flag = new BCryptPasswordEncoder().matches(dto.getPassword(), sysUserOuter.getPassword());
            if (!flag) {
                log.warn("旧密码输入错误！");
                throw new BusinessException(ErrorCodeConstants.PASSWORD_FAIL, "旧密码输入错误！");
            }
            // 对密码进行加密
            sysUserOuter.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
            // 更新缓存信息
            log.info("外部用户-修改成功-更新缓存");
            selectUserOuterPowerInfo(sysUserOuter.getId());
            return updateById(sysUserOuter);
        }
        sysUserOuter = getById(dto.getId());
        // 对密码进行加密
        sysUserOuter.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        // 更新缓存信息
        log.info("外部用户-修改成功-更新缓存");
        selectUserOuterPowerInfo(sysUserOuter.getId());
        return updateById(sysUserOuter);
    }
}
