package cn.kun.auth.system.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.role.service.SysRoleInnerService;
import cn.kun.auth.system.user.entity.dto.UserAddDTO;
import cn.kun.auth.system.user.entity.dto.UserEditDTO;
import cn.kun.auth.system.user.entity.dto.UserPageDTO;
import cn.kun.auth.system.user.entity.dto.UserPasswordDTO;
import cn.kun.auth.system.user.entity.dto.UserPermissDTO;
import cn.kun.auth.system.user.entity.po.SysUserDetailInner;
import cn.kun.auth.system.user.entity.po.SysUserInner;
import cn.kun.auth.system.user.entity.po.SysUserJobInner;
import cn.kun.auth.system.user.entity.po.SysUserMenuInner;
import cn.kun.auth.system.user.entity.po.SysUserOuter;
import cn.kun.auth.system.user.entity.po.SysUserProjectInner;
import cn.kun.auth.system.user.entity.po.SysUserRoleInner;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.UserDetailListVO;
import cn.kun.auth.system.user.entity.vo.UserJobInfoVO;
import cn.kun.auth.system.user.entity.vo.UserMenuPermissVO;
import cn.kun.auth.system.user.entity.vo.UserPageVO;
import cn.kun.auth.system.user.entity.vo.UserProjectPermissVO;
import cn.kun.auth.system.user.entity.vo.UserRolePermissVO;
import cn.kun.auth.system.user.mapper.SysUserInnerMapper;
import cn.kun.auth.system.user.service.SysUserMenuInnerService;
import cn.kun.auth.system.user.service.SysUserOuterService;
import cn.kun.auth.system.user.service.SysUserRoleInnerService;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.auth.system.job.entity.vo.JobInfoByUserIdVO;
import cn.kun.auth.system.job.service.SysJobInnerService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.system.menu.service.SysMenuService;
import cn.kun.auth.system.project.service.SysProjectService;
import cn.kun.auth.system.user.service.SysUserDetailInnerService;
import cn.kun.auth.system.user.service.SysUserInnerService;
import cn.kun.auth.system.user.service.SysUserJobInnerService;
import cn.kun.auth.system.user.service.SysUserProjectInnerService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 内部用户表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysUserInnerServiceImpl extends ServiceImpl<SysUserInnerMapper, SysUserInner> implements SysUserInnerService {
    @Autowired
    private SysJobInnerService sysJobInnerService;
    @Autowired
    private SysRoleInnerService sysRoleInnerService;
    @Autowired
    private SysProjectService sysProjectService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserDetailInnerService sysUserDetailInnerService;
    @Autowired
    private SysUserJobInnerService sysUserJobInnerService;
    @Autowired
    private SysUserProjectInnerService sysUserProjectInnerService;
    @Autowired
    private SysUserMenuInnerService sysUserMenuInnerService;
    @Autowired
    private SysUserRoleInnerService sysUserRoleInnerService;
    @Autowired
    private SysCompanyInnerService sysCompanyInnerService;
    @Autowired
    private BaseDictService baseDictService;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private SysUserOuterService sysUserOuterService;

    @Override
    public Page<UserPageVO> page(UserPageDTO dto) {
        // 判断是超级用户还是普通用户
        log.info("进入内部用户分页");
        if(ObjUtil.isEmpty(dto.getCompanyId())){
            // 判断是超级用户还是普通用户
            String loginName = AuthHelp.getLoginName();
            if (StrUtil.equals(AuthConstants.SYS_ADMIN,loginName) && ObjUtil.isEmpty(dto.getCompanyId())){
                dto.setCompanyId(null);
            }
        }
        // 获取当前登陆人的公司信息
        List<BaseSelectVO> companyList = sysCompanyInnerService.selectInnerParent();
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
        log.info("进入内部用户详情");
        if(ObjUtil.isEmpty(id)){
            log.warn("用户id不能为空#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"用户id不能为空");
        }
        SysUserInner sysUserInner = getById(id);

        if (ObjUtil.isEmpty(sysUserInner)) {
            log.warn("找不到该用户数据#{}",id);
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户数据");
        }
        // 复制对象
        UserDetailVO vo = new UserDetailVO();
        BeanUtil.copyProperties(sysUserInner, vo);

        QueryWrapper<SysUserDetailInner> qw = new QueryWrapper<>();
        qw
                .lambda()
                .eq(SysUserDetailInner::getUserInnerId,sysUserInner.getId());
        SysUserDetailInner sysUserDetailInner = sysUserDetailInnerService.getOne(qw);
//        if (ObjUtil.isEmpty(sysUserDetailInner)) {
//            log.warn("找不到该用户详情数据#{}",sysUserDetailInner);
//            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户详情数据");
//        }
        BeanUtil.copyProperties(sysUserDetailInner, vo);
        // 获取用户公司部门岗位信息
        UserJobInfoVO userJobInfoVO = getUserJobInfo(Long.parseLong(id));
        vo.setId(sysUserInner.getId());
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
                parents += userJobInfoVO.getDeptParentIds().substring(2) + userJobInfoVO.getDeptId() + ",";
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
    public SysUserInner add(UserAddDTO dto) {

        log.info("内部用户-进入添加方法");
        QueryWrapper<SysUserInner> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserInner::getLoginName, dto.getLoginName());
        List<SysUserInner> list = list(wrapper);

        /* 校验内、外部登录名是否重复 */
        if (!CollUtil.isEmpty(list)) {
            log.warn("登录名已存在!登录名为：{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.REPEAT, "登录名已存在！");
        }else {
            QueryWrapper<SysUserOuter> queryUserOuter = new QueryWrapper<>();
            queryUserOuter.lambda().eq(SysUserOuter::getLoginName,dto.getLoginName());
            List<SysUserOuter> userOuterList = sysUserOuterService.list(queryUserOuter);
            if (CollUtil.isNotEmpty(userOuterList)){
                log.warn("登录名已存在!登录名为：{}", dto.getLoginName());
                throw new BusinessException(ErrorCodeConstants.REPEAT, "登录名已存在！");
            }
        }
        // 复制对象
        SysUserInner sysUserInner = new SysUserInner();
        BeanUtil.copyProperties(dto, sysUserInner);
        // 对密码进行加密
        sysUserInner.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        // 头像
        if (ObjUtil.isNotNull(dto.getPhoto())) {
            sysUserInner.setPath(remoteFileService.getPathById(dto.getPhoto()).getData());
        }
        // 添加对象
        boolean flag = save(sysUserInner);
        if (!flag) {
            log.warn("添加内部用户失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("内部用户-添加成功");
        // 复制对象
        SysUserDetailInner sysUserDetailInner = new SysUserDetailInner();
        BeanUtil.copyProperties(dto, sysUserDetailInner);
        sysUserDetailInner.setUserInnerId(sysUserInner.getId());
        // 添加用户详情对象
        boolean flagDetail = sysUserDetailInnerService.save(sysUserDetailInner);
        if (!flagDetail) {
            log.info("添加内部用户详情失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("内部用户-添加成功");

        SysUserJobInner sysUserJobInner = new SysUserJobInner();
        sysUserJobInner.setUserInnerId(sysUserInner.getId());
        sysUserJobInner.setJobInnerId(dto.getJobId());
        boolean flagJob = sysUserJobInnerService.save(sysUserJobInner);
        if (!flagJob) {
            log.info("添加用户职位信息失败！登录名为:{}", dto.getLoginName());
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "添加失败！");
        }
        log.info("内部用户-添加成功");
        return sysUserInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserInner edit(UserEditDTO dto) {
        log.info("内部用户-修改方法进入");
        // 复制对象
        SysUserInner sysUserInner = getById(dto.getId());
        if (ObjUtil.isEmpty(sysUserInner)) {
            log.warn("用户信息不存在！：{}", sysUserInner);
            throw new BusinessException(ErrorCodeConstants.NULL, "用户信息不存在！");
        }
        BeanUtil.copyProperties(dto, sysUserInner);
        // 头像
        if (ObjUtil.isNotNull(dto.getPhoto())) {
            sysUserInner.setPath(remoteFileService.getPathById(dto.getPhoto()).getData());
        }
        // 修改对象
        boolean flag = updateById(sysUserInner);
        if (!flag) {
            log.warn("内部用户-修改失败：#{}",sysUserInner);
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败！");
        }
        log.info("内部用户-修改成功");
        // 复制对象
        QueryWrapper<SysUserDetailInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserDetailInner::getUserInnerId, dto.getId());
        SysUserDetailInner userDetailInfo = new SysUserDetailInner();
        BeanUtil.copyProperties(dto, userDetailInfo);
        // 修改用户详情
        sysUserDetailInnerService.update(userDetailInfo,queryWrapper);
        // 删除缓存
//        if (!userDetail) {
//            log.warn("内部用户详情-修改失败");
//            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"修改失败");
//        }
        // 删除之前管理的职位信息
        QueryWrapper<SysUserJobInner> sysUserJobInnerQueryWrapper = new QueryWrapper<>();
        sysUserJobInnerQueryWrapper.lambda()
                .eq(SysUserJobInner::getUserInnerId,dto.getId());
        sysUserJobInnerService.remove(sysUserJobInnerQueryWrapper);
        // 保存最新添加的用户职位
        SysUserJobInner saveUserJob = new SysUserJobInner();
        saveUserJob.setJobInnerId(dto.getJobId());
        saveUserJob.setUserInnerId(dto.getId());
        sysUserJobInnerService.save(saveUserJob);

        // 更新缓存信息
        log.info("内部用户-修改成功-更新缓存");
        selectUserInnerPowerInfo(sysUserInner.getId());
        return sysUserInner;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        log.info("内部用户-删除用户方法进入");
        // 复制对象
        SysUserInner sysUserInner = getById(id);
        if (ObjUtil.isEmpty(sysUserInner)) {
            log.warn("用户信息不存在！：{}", sysUserInner);
            throw new BusinessException(ErrorCodeConstants.NULL, "用户信息不存在！");
        }
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, sysUserInner.getLoginName())) {
            log.warn("超级管理员不允许删除！：{}", sysUserInner);
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "超级管理员不允许删除！");
        }
        boolean flag = removeById(id);
        if (!flag) {
            log.warn("内部用户-删除失败：#{}",sysUserInner);
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"删除失败！");
        }
        log.info("内部用户-删除成功");
        // 复制对象
        QueryWrapper<SysUserDetailInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserDetailInner::getUserInnerId, id);
        // 修改用户详情
        sysUserDetailInnerService.remove(queryWrapper);
        // 删除缓存
        log.warn("内部用户详情-删除详情");
        // 更新缓存信息
        log.info("内部用户-删除成功-删除缓存");
        RedisHelp.delHash(AuthCacheConstants.USER_INNER_HASH, Convert.toStr(id));

    }
    /**
     * 获取内部用户所有权限信息并存储缓存
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public SysUserCacheInfoVO getUserCacheInfoByUserId(Long userId) {
        // 去redis获取用户权限信息
        Object obj = RedisHelp.getHash(AuthCacheConstants.USER_INNER_HASH, userId.toString());
        SysUserCacheInfoVO sysUserCacheInfoVo = null;
        if (obj instanceof SysUserCacheInfoVO) {
            sysUserCacheInfoVo = (SysUserCacheInfoVO) obj;
        }
        if (ObjUtil.isEmpty(sysUserCacheInfoVo)) {
            return selectUserInnerPowerInfo(userId);
        }
        return sysUserCacheInfoVo;
    }

    /**
     * 通过内部用户id查询用户缓存信息并保存到缓存中
     * @param userId
     * @return
     */
    @Override
    public SysUserCacheInfoVO selectUserInnerPowerInfo(Long userId){
        List<ProjectParentVO> sysUserProjectAuth = null;
        List<String> sysUserMenuAuth = null;
        SysUserCacheInfoVO sysUserCacheInfoVo = new SysUserCacheInfoVO();
        // 通过内部用户id查询内部用户信息
        SysUserInfoVO userInnerInfo = baseMapper.getUserInfoByUserId(userId);
        if(ObjUtil.isEmpty(userInnerInfo)){
            log.warn("找不到该用户信息");
            throw new BusinessException(ErrorCodeConstants.NULL,"找不到该用户信息");
        }
        // 通过内部用户id查询职位、部门、公司信息
        List<JobInfoByUserIdVO> jobInnerInfoList = sysJobInnerService.getJobInnerInfoByUserInnerId(userId);

        List<JobRedisVO> jobRedisVOList = new ArrayList<>();
        // 组装公司、部门、职位参数
        DeptRedisVO deptRedisVO = null;
        CompanyRedisVO companyRedisVO = null;
        JobRedisVO jobRedisVO = null;
        for (JobInfoByUserIdVO jobInfoByUserIdVO : jobInnerInfoList) {
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
        List<RoleInfoByUserIdVO> roleInnerInfoList = sysRoleInnerService.getRoleInnerInfoByUserInnerId(userId);
        // 判断是否为超级用户
        if (ObjUtil.isNotEmpty(userInnerInfo)) {
            if (ObjUtil.equals(AuthConstants.SYS_ADMIN, userInnerInfo.getLoginName())) {
                // 如果是超级用户，查询所有的项目权限信息
                sysUserProjectAuth = sysProjectService.selectProjectNoByUserInnerId(null);
                // 如果是超级用户，查询所有的菜单信息
                sysUserMenuAuth = sysMenuService.selectPermsInner(null);
            } else {
                // 如果是普通用户，查询当前用户拥有的项目
                sysUserProjectAuth = sysProjectService.selectProjectNoByUserInnerId(userId);
                // 如果是普通用户，查询当前用户拥有的菜单权限
                sysUserMenuAuth = sysMenuService.selectPermsInner(userId);
            }
            if (ObjUtil.isEmpty(sysUserCacheInfoVo)) {
                sysUserCacheInfoVo = new SysUserCacheInfoVO();
            }
            // 赋值用户及权限信息
            sysUserCacheInfoVo.setUserInfo(userInnerInfo);
            sysUserCacheInfoVo.setJobInfo(jobRedisVOList);
            sysUserCacheInfoVo.setRoleInfo(roleInnerInfoList);
            sysUserCacheInfoVo.setSysUserProjectAuth(sysUserProjectAuth);
            sysUserCacheInfoVo.setSysUserMenuAuth(sysUserMenuAuth);
            RedisHelp.setHash(AuthCacheConstants.USER_INNER_HASH, String.valueOf(userInnerInfo.getUserId()), sysUserCacheInfoVo);
        }
        return sysUserCacheInfoVo;
    }

    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param projectNo  项目编号
     * @return
     */
    @Override
    public UserDetailListVO personnelDetail(String projectNo){
        log.info("进入用户-获取人员配置方法");

        String loginName = AuthHelp.getLoginName();
        if(ObjUtil.isEmpty(loginName)){
            log.warn("登录名不允许为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"登录名不允许为空");
        }
        QueryWrapper<SysUserInner> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(SysUserInner::getLoginName,loginName);
        SysUserInner sysUserInner = getOne(queryWrapper);
        if(ObjUtil.isEmpty(sysUserInner)){
            log.warn("找不到此用户！");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"找不到此用户");
        }
        // 获取用户所有公司id
        List<String> companyInfos = null;
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            // 获取用户所有公司id
            companyInfos = selectCompanyInfoLoginName(loginName);
            if(CollectionUtil.isEmpty(companyInfos)){
                log.warn("该用户没有公司信息！#{}",companyInfos);
                throw new BusinessException(ErrorCodeConstants.NULL, "该用户没有公司信息");
            }
        }
        // 获取该用户所拥有公司的所有用户
        List<UserDetailList> userDetails = selectInnerPersonnelInfo(companyInfos);
        // 获取以授权该项目的用户
        List<UserDetailList> userProjects = sysProjectService.selectInnerPersonnelInfoProjectNo(projectNo,companyInfos);

        // 取差集
//        List<UserDetailList> userLists = userDetailListVos.stream().filter(item -> !userProjects.contains(item)).collect(Collectors.toList());
        // 组装用户参数----用户项目关联关系
        UserDetailListVO userDetailListVo = new UserDetailListVO();
        userDetailListVo.setUsers(userDetails);
        // 组装用户参数----角色项目关联关系
        userDetailListVo.setUserProjects(userProjects);
        return userDetailListVo;
    }

    @Override
    /**
     *  人员配置：获取当前用户所拥有的所有公司信息
     * @param loginName  登录名
     * @return
     */
    public List<String> selectCompanyInfoLoginName(String loginName){
        return baseMapper.selectCompanyInfoLoginName(loginName);
    }


    /**
     *  人员配置：获取当前用户所拥有的所有公司信息、部门信息、职位信息的用户
     * @param companyIds  公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectInnerPersonnelInfo(List<String> companyIds){
        return baseMapper.selectInnerPersonnelInfo(companyIds);
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

        QueryWrapper<SysUserProjectInner> userProjectQuery = new QueryWrapper<>();
        userProjectQuery.lambda().eq(SysUserProjectInner::getUserInnerId,userId);
        QueryWrapper<SysUserMenuInner> userMenuQuery  = new QueryWrapper<>();
        userMenuQuery.lambda().eq(SysUserMenuInner::getUserInnerId,userId);
        QueryWrapper<SysUserRoleInner> userRoleQuery  = new QueryWrapper<>();
        userRoleQuery.lambda().eq(SysUserRoleInner::getUserInnerId,userId);

        // 判断是否取消所有授权
//        if (CollUtil.isEmpty(projectNoList) && CollUtil.isEmpty(menuIdList) && CollUtil.isEmpty(roleIdList)){
//            log.warn("用户取消了所有授权");
//            // 删除数据表
//            sysUserProjectInnerService.remove(userProjectQuery);
//            sysUserMenuInnerService.remove(userMenuQuery);
//            sysUserRoleInnerService.remove(userRoleQuery);
//            // 获取最新用户缓存信息
//            selectUserInnerPowerInfo(userId);
//            return true;
//        }

        /* 先删除用户项目表数据，再新增用户项目 */
        if (CollUtil.isNotEmpty(projectNoList)) {
            // 删除用户项目
            sysUserProjectInnerService.remove(userProjectQuery);
            List<SysUserProjectInner> list = new ArrayList<>();
            for (String projectNo : projectNoList) {
                SysUserProjectInner sysUserProjectInner = new SysUserProjectInner();
                sysUserProjectInner.setUserInnerId(userId);
                sysUserProjectInner.setProjectNo(projectNo);
                list.add(sysUserProjectInner);
            }
            // 保存用户项目
            boolean addBatch = sysUserProjectInnerService.saveBatch(list);
            if (addBatch) {
                log.info("用户项目-成功保存用户项目");
            }

        }else if (projectNoList != null && projectNoList.size() < 1) {
            // 删除用户角色
            sysUserProjectInnerService.remove(userProjectQuery);
            log.info("用户项目-成功保存用户项目");
        }

        /* 先删除用户菜单表数据，再新增用户菜单 */
        if (CollUtil.isNotEmpty(menuIdList)) {
            // 删除用户菜单
            sysUserMenuInnerService.remove(userMenuQuery);
            List<SysUserMenuInner> list = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysUserMenuInner sysUserMenuInner = new SysUserMenuInner();
                sysUserMenuInner.setUserInnerId(userId);
                sysUserMenuInner.setMenuId(menuId);
                list.add(sysUserMenuInner);
            }
            // 保存用户菜单
            boolean addBatch = sysUserMenuInnerService.saveBatch(list);
            if (addBatch) {
                log.info("用户菜单-成功保存用户菜单");
            }
        }else if (menuIdList != null && menuIdList.size() < 1) {
            // 删除用户角色
            sysUserMenuInnerService.remove(userMenuQuery);
            log.info("用户菜单-成功保存用户菜单");
        }

        /* 先删除用户角色表数据，再新增用户角色 */
        if (CollUtil.isNotEmpty(roleIdList)) {
            // 删除用户角色
            sysUserRoleInnerService.remove(userRoleQuery);
            List<SysUserRoleInner> list = new ArrayList<>();
            for (Long roleId : roleIdList) {
                SysUserRoleInner sysUserRoleInner = new SysUserRoleInner();
                sysUserRoleInner.setUserInnerId(userId);
                sysUserRoleInner.setRoleInnerId(roleId);
                list.add(sysUserRoleInner);
            }
            // 保存用户角色
            boolean addBatch = sysUserRoleInnerService.saveBatch(list);
            if (addBatch) {
                log.info("用户角色-成功保存用户角色");
            }
        } else if (roleIdList != null && roleIdList.size() < 1) {
            // 删除用户角色
            sysUserRoleInnerService.remove(userRoleQuery);
            log.info("用户角色-成功保存用户角色");
        }

        // 获取最新用户缓存信息
        selectUserInnerPowerInfo(userId);
        return true;
    }

    /**
     * 根据用户id查询用户菜单授权列表信息
     * @return
     */
    @Override
    public UserMenuPermissVO selectMenuListByUserId(UserMenuPermissDTO dto){

        log.info("进入获取菜单列表信息");
        UserMenuPermissVO vo = new UserMenuPermissVO();
        List<BaseSelectVO> loginUserVo = null;
        // 当前登录用户传入值
        UserMenuPermissDTO userMenuPermissDTO = new UserMenuPermissDTO();
        userMenuPermissDTO.setServiceCode(dto.getServiceCode());
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            userMenuPermissDTO.setUserId(null);
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }
        else{
            userMenuPermissDTO.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }

        // 当前授权用户-菜单查询
        List<BaseSelectVO> userVo = sysMenuService.innerMenuListInfo(dto.getUserId());


        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);

        return vo;
    }

    @Override
    public UserRolePermissVO selectRoleListByUserId(Long userId) {

        log.info("开始查询用户-角色权限");
        String loginName = AuthHelp.getLoginName();
        UserRolePermissVO vo = new UserRolePermissVO();

        List<BaseSelectVO> loginUserVo = null;
        List<BaseSelectVO> personnelVOList = null; // 获取当前登陆人的所有公司信息
        // 判断用户是否是管理员
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            loginUserVo = sysRoleInnerService.selectListByUserId(null);
            personnelVOList = sysCompanyInnerService.selectCompanyInfoByLoginName(null);
            loginUserVo.addAll(personnelVOList);

        } else {
            loginUserVo = sysRoleInnerService.selectListByUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            List<BaseSelectVO> companyInfo = sysCompanyInnerService.selectInnerParent();
            loginUserVo.addAll(companyInfo);
        }
        // 查询当前用户的角色权限
        List<BaseSelectVO> userVo = sysRoleInnerService.selectListByUserId(userId);

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
             loginVoList = sysProjectService.selectProjectNoByUserInnerId(null);
        }else {
            loginVoList = sysProjectService.selectProjectNoByUserInnerId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        List<BaseSelectVO> loginUserVo = loginVoList.stream().map(this::cast).toList();

        // 查询当前用户的项目权限
        List<ProjectParentVO> userVoList = sysProjectService.selectProjectNoByUserInnerId(userId);
        List<BaseSelectVO> userVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
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
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        JSONObject expand = null;
        if (obj instanceof ProjectParentVO projectParentVO) {
            expand = new JSONObject();
            vo.setValue(Convert.toStr(projectParentVO.getId()));
            vo.setLabel(projectParentVO.getName());
            vo.setParentValue(Convert.toStr(projectParentVO.getParentId()));
            vo.setParentValues(projectParentVO.getParentIds());
            expand.put("projectNo",projectParentVO.getProjectNo());
            vo.setExpand(expand);
        }
        return vo;
    }

    /**
     * 根据项目编号获取项目下的所有内部用户
     *
     * @param projectNo
     * @return
     */
    @Override
    public List<UserRedisVO> selectUserInnerByProjectNo(String projectNo){
        return baseMapper.selectUserInnerByProjectNo(projectNo);
    }

    @Override
    public List<Long> selectUserIdsByRoleInnerId(Long roleInnerId) {
        return baseMapper.selectUserIdsByRoleInnerId(roleInnerId);
    }

    /**
     *  修改用户密码
     * @param dto
     * @return
     */
    @Override
    public Boolean updateUserPassword(UserPasswordDTO dto) {
        log.info("进入修改用户密码");
        SysUserInner sysUserInner = null;
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            sysUserInner = getById(AuthHelp.getUserId());
            Boolean flag = new BCryptPasswordEncoder().matches(dto.getPassword(), sysUserInner.getPassword());
            if (!flag) {
                log.warn("旧密码输入错误！");
                throw new BusinessException(ErrorCodeConstants.PASSWORD_FAIL, "旧密码输入错误！");
            }
            // 对密码进行加密
            sysUserInner.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
            // 更新缓存信息
            log.info("内部用户-修改成功-更新缓存");
            selectUserInnerPowerInfo(sysUserInner.getId());
            return updateById(sysUserInner);
        }
        sysUserInner = getById(dto.getId());
        // 对密码进行加密
        sysUserInner.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
        // 更新缓存信息
        log.info("内部用户-修改成功-更新缓存");
        selectUserInnerPowerInfo(ConvertHelp.toLong(AuthHelp.getUserId()));
        return updateById(sysUserInner);
    }
}
