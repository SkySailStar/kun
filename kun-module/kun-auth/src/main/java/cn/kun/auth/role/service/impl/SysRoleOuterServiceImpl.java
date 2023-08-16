package cn.kun.auth.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.company.service.SysCompanyOuterService;
import cn.kun.auth.project.entity.vo.PersonnelDetailVO;
import cn.kun.auth.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.project.service.SysProjectService;
import cn.kun.auth.role.entity.dto.RoleEditDTO;
import cn.kun.auth.role.entity.dto.RolePersonnelDTO;
import cn.kun.auth.role.entity.dto.RoleUsersDTO;
import cn.kun.auth.role.mapper.SysRoleOuterMapper;
import cn.kun.auth.role.entity.dto.RoleAddDTO;
import cn.kun.auth.role.entity.dto.RolePageDTO;
import cn.kun.auth.role.entity.dto.RolePermissDTO;
import cn.kun.auth.role.entity.vo.PersonnelRoleVO;
import cn.kun.auth.role.entity.vo.RoleDetailVO;
import cn.kun.auth.role.entity.vo.RoleMenuPermissVO;
import cn.kun.auth.role.entity.vo.RolePageVO;
import cn.kun.auth.role.entity.vo.SysRoleInfoVO;
import cn.kun.auth.user.entity.po.SysUserRoleOuter;
import cn.kun.auth.user.entity.po.UserDetailList;
import cn.kun.auth.user.entity.vo.RoleProjectPermissVO;
import cn.kun.auth.user.entity.vo.UserPersonnelPageVO;
import cn.kun.auth.user.service.SysUserOuterService;
import cn.kun.auth.user.service.SysUserRoleOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.menu.entity.po.SysMenu;
import cn.kun.auth.menu.entity.vo.MenuRedisVo;
import cn.kun.auth.menu.service.SysMenuService;
import cn.kun.auth.role.entity.po.SysRoleMenuOuter;
import cn.kun.auth.role.entity.po.SysRoleOuter;
import cn.kun.auth.role.entity.po.SysRoleProjectOuter;
import cn.kun.auth.role.service.SysRoleMenuOuterService;
import cn.kun.auth.role.service.SysRoleOuterService;
import cn.kun.auth.role.service.SysRoleProjectOuterService;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.RoleInfoByUserIdVO;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.global.constant.dict.type.AuthDictTypeConstants;
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
 * 外部角色表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysRoleOuterServiceImpl extends ServiceImpl<SysRoleOuterMapper, SysRoleOuter> implements SysRoleOuterService {
    @Resource
    private SysProjectService sysProjectService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserOuterService sysUserOuterService;
    @Resource
    private SysUserRoleOuterService sysUserRoleOuterService;
    @Resource
    private SysRoleProjectOuterService sysRoleProjectOuterService;
    @Resource
    private SysRoleMenuOuterService sysRoleMenuOuterService;
    @Resource
    private SysCompanyOuterService sysCompanyOuterService;
    @Resource
    private BaseDictService baseDictService;


    @Override
    public Page<RolePageVO> page(RolePageDTO dto) {

        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!ObjUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            dto.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        Page<RolePageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        Page<RolePageVO> voList = baseMapper.selectPage(page, dto);
        for (RolePageVO vo : voList.getRecords()) {
            vo.setType(baseDictService.getLabel(AuthDictTypeConstants.ROLE_TYPE,vo.getType()));
        }
        return voList;
    }

    @Override
    public RoleDetailVO detail(Long id) {
        if(ObjUtil.isEmpty(id)){
            log.warn("角色id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色id不能为空");
        }
        return baseMapper.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleOuter add(RoleAddDTO dto) {

        log.info("外部角色-开始添加：{}", dto);
        /* 传入值复制到数据库对象 */
        SysRoleOuter sysRoleOuter = new SysRoleOuter();
        BeanUtil.copyProperties(dto,sysRoleOuter);
        sysRoleOuter.setCompanyOuterId(dto.getCompanyId());
        // 添加对象
        boolean add = save(sysRoleOuter);
        if (add) {
            log.info("外部角色-成功添加");
            log.info("外部角色-成功添加缓存");
            // 绑定刚添加的角色信息
            SysUserRoleOuter sysUserRoleOuter = new SysUserRoleOuter();
            sysUserRoleOuter.setUserOuterId(ConvertHelp.toLong(AuthHelp.getUserId()));
            sysUserRoleOuter.setRoleOuterId(sysRoleOuter.getId());
            if (!StrUtil.equals(AuthHelp.getLoginName(),AuthConstants.SYS_ADMIN)){
                boolean userRoleSave = sysUserRoleOuterService.save(sysUserRoleOuter);
                if(!userRoleSave){
                    log.warn("内部用户角色-添加失败#{}",sysUserRoleOuter);
                    throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"用户角色-添加失败");
                }
            }
            RedisHelp.setHash(AuthCacheConstants.ROLE_INNER_HASH,sysRoleOuter.getId().toString(),sysRoleOuter);
        }
        else {
            log.warn("外部角色-添加失败");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"角色-添加失败");
        }
        return sysRoleOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleOuter edit(RoleEditDTO dto) {

        log.info("外部角色-开始修改：{}", dto);
        /* 获取数据库对象 */
        SysRoleOuter sysRoleOuter = getById(dto.getId());
        if (ObjUtil.isNull(sysRoleOuter)) {
            log.warn("外部角色-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "角色-修改的数据不存在，无法修改");
        }

        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysRoleOuter);
        sysRoleOuter.setCompanyOuterId(dto.getCompanyId());
        // 修改对象
        boolean edit = updateById(sysRoleOuter);
        // 判断是否修改成功
        if (edit) {
            log.info("外部角色-成功修改");
            log.info("外部角色-删除成功-清除缓存");
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            selectRoleOuterPowerInfo(dto.getId());
        }
        else {
            log.warn("外部角色-修改失败");
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"角色-修改失败");
        }
        return sysRoleOuter;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("外部角色-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("角色id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "角色id不能为空");
        }
        // 组装删除用户角色参数
        QueryWrapper<SysUserRoleOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRoleOuter::getRoleOuterId,id);
        // 组装删除角色菜单参数
        QueryWrapper<SysRoleMenuOuter> menuInnerQueryWrapper = new QueryWrapper<>();
        menuInnerQueryWrapper.lambda().eq(SysRoleMenuOuter::getRoleOuterId,id);
        // 删除角色前，需要校验当前角色是否绑定用户，如果有，则不能删除，反之即可。
        List<Long> sysUserOuters = sysUserOuterService.selectUserIdsByRoleOuterId(id);
        /* 判断当前角色是否绑定了用户 */
        if (CollUtil.isEmpty(sysUserOuters)){
            // 删除角色信息
            boolean remove = removeById(id);
            if (remove) {
                // 删除用户角色管理关系
                sysUserRoleOuterService.remove(queryWrapper);
                // 删除角色菜单管理关系
                sysRoleMenuOuterService.remove(menuInnerQueryWrapper);
                log.info("外部角色-成功删除");
                log.info("外部角色-删除成功-清除缓存");
                RedisHelp.delHash(AuthCacheConstants.ROLE_OUTER_HASH, Convert.toStr(id));
                RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            }
            else {
                log.warn("外部角色-删除失败");
                throw new BusinessException(ErrorCodeConstants.NULL, "外部角色-删除失败");
            }
        } else if (sysUserOuters.size() < 1  &&
                (ObjUtil.equals(sysUserOuters.get(0), AuthHelp.getUserId())
                || StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName()))) {
            log.info("外部角色-成功删除");
            removeById(id);
            // 删除用户角色管理关系
            sysUserRoleOuterService.remove(queryWrapper);
            // 删除角色菜单管理关系
            sysRoleMenuOuterService.remove(menuInnerQueryWrapper);
            log.info("外部角色-删除成功-清除缓存");
            RedisHelp.delHash(AuthCacheConstants.ROLE_OUTER_HASH, Convert.toStr(id));
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
        } else {
            log.warn("外部角色-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前角色绑定了用户，不能删除当前角色信息");
        }

    }
    

    /**
     * 通过外部角色id查询角色缓存信息
     * @param roleOuterId
     * @return
     */
    @Override
    public SysRoleInfoVO getRoleOuterInfoByRoleOuterId(Long roleOuterId) {
        SysRoleInfoVO sysRoleInfoVo = (SysRoleInfoVO) RedisHelp.getHash(AuthCacheConstants.ROLE_OUTER_HASH, String.valueOf(roleOuterId));
        if (ObjUtil.isNull(sysRoleInfoVo)){
            return selectRoleOuterPowerInfo(roleOuterId);
        }
        return sysRoleInfoVo;
    }

    /**
     *  获取外部角色权限信息
     * @param roleOuterId 角色id
     * @return
     */
    public SysRoleInfoVO selectRoleOuterPowerInfo(Long roleOuterId){
        SysRoleInfoVO sysRoleInfoOuterVo = new SysRoleInfoVO();
        // 查询角色信息
        SysRoleOuter sysRoleOuterInfo = baseMapper.selectById(roleOuterId);
        if (ObjUtil.isNotNull(sysRoleOuterInfo)){
            // 赋值角色信息
            sysRoleInfoOuterVo.setRoleId(roleOuterId);
            sysRoleInfoOuterVo.setRoleName(sysRoleOuterInfo.getName());
            sysRoleInfoOuterVo.setRoleType(sysRoleOuterInfo.getType());
            sysRoleInfoOuterVo.setPermission(sysRoleOuterInfo.getPermission());
            // 查询项目权限信息
            List<ProjectRedisVO> roleProjectOuterAuth = sysProjectService.getRoleProjectOuterAuth(roleOuterId);
            // 查询菜单权限信息
            List<MenuRedisVo> roleMenuOuterAuth = sysMenuService.getRoleMenuOuterAuth(roleOuterId);
            sysRoleInfoOuterVo.setRoleProjectAuth(roleProjectOuterAuth);
            sysRoleInfoOuterVo.setRoleMenuAuth(roleMenuOuterAuth);
            // 添加redis缓存
            RedisHelp.setHash(AuthCacheConstants.ROLE_OUTER_HASH, String.valueOf(roleOuterId),sysRoleInfoOuterVo);
        }
        return sysRoleInfoOuterVo;
    }

    @Override
    public List<RoleInfoByUserIdVO> getRoleInfoByUserId(Long userId) {
        return baseMapper.getRoleInfoByUserId(userId);
    }

    @Override
    public List<RoleInfoByUserIdVO> getRoleOuterByRoleOuterId(Long userOuterId) {
        return baseMapper.getRoleOuterByRoleOuterId(userOuterId);
    }

    /**
     *  角色-人员配置---获取角色下所有用户信息
     * @param roleId 角色id
     * @param companyIds 公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectOuterPersonnelInfoRoleId(Long roleId, List<String> companyIds){
        return baseMapper.selectOuterPersonnelInfoRoleId(roleId,companyIds);
    }

    /**
     *  角色-人员配置---获取角色下所有用户信息、当前用户所拥有的公司信息、部门信息、职位信息
     * @param roleId 角色id
     * @return
     */
    @Override
    public PersonnelRoleVO personnelRoleInfo(Long roleId){
        log.info("进入外部角色-获取人员配置方法");
        PersonnelRoleVO personnelRoleVO = new PersonnelRoleVO();
        Long companyId = ConvertHelp.toLong(AuthHelp.getCompanyId());
        String loginName = AuthHelp.getLoginName();
        // 调用获取树结构
        PersonnelDetailVO personnelDetailVO = sysProjectService.personnelTree();
        log.info("进入角色-人员配置-获取内部人员信息");
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            personnelRoleVO.setUsersInner(selectOuterPersonnelInfoRoleId(roleId, null));
        } else {
            List<String> comList = new ArrayList<>();
            comList.add(companyId.toString());
            personnelRoleVO.setUsersOuter(selectOuterPersonnelInfoRoleId(roleId, comList));
        }
        personnelRoleVO.setAllUserInner(personnelDetailVO.getAllUserInner());
        personnelRoleVO.setAllUserOuter(personnelDetailVO.getAllUserOuter());
        return personnelRoleVO;
    }


    /**
     *  角色-人员配置-分页功能
     * @param dto
     * @return
     */
    @Override
    public Page<UserPersonnelPageVO> personnelRoleInfoPage(RolePersonnelDTO dto){
        log.info("进入外部角色-获取人员配置分页方法");
        if(ObjUtil.isEmpty(dto.getRoleId())){
            log.warn("找不到角色id");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色信息不能为空");
        }
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

        return baseMapper.personnelRoleInfoPage(page,dto,loginName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean savePermiss(RolePermissDTO dto) {

        log.info("角色开始授权-项目、菜单、用户");
        // 判断用户是否有授权
        Long roleId = dto.getRoleId();
        List<String> projectNoList = dto.getProjectNoList();
        List<Long> menuIdList = dto.getMenuIdList();
        List<Long> userIdList = dto.getUserIdList();

        QueryWrapper<SysRoleProjectOuter> roleProjectQuery = new QueryWrapper<>();
        roleProjectQuery.lambda().eq(SysRoleProjectOuter::getRoleOuterId,roleId);
        QueryWrapper<SysRoleMenuOuter> roleMenuQuery  = new QueryWrapper<>();
        roleMenuQuery.lambda().eq(SysRoleMenuOuter::getRoleOuterId,roleId);
        QueryWrapper<SysUserRoleOuter> userRoleQuery  = new QueryWrapper<>();
        userRoleQuery.lambda().eq(SysUserRoleOuter::getRoleOuterId,roleId);

        // 判断是否取消所有授权
//        if (CollUtil.isEmpty(projectNoList) && CollUtil.isEmpty(menuIdList) && CollUtil.isEmpty(userIdList)){
//            log.warn("用户取消了角色所有授权");
//            // 删除数据表
//            sysRoleProjectOuterService.remove(roleProjectQuery);
//            sysRoleMenuOuterService.remove(roleMenuQuery);
//            sysUserRoleOuterService.remove(userRoleQuery);
//            // 删除用户缓存信息
//            RedisUtils.del(AuthCacheConstants.USER_OUTER_HASH);
//            // 重新查询角色权限并存入缓存
//            selectRoleOuterPowerInfo(roleId);
//            return true;
//        }

        /* 先删除角色项目表数据，再新增角色项目 */
//        if (CollUtil.isNotEmpty(projectNoList)) {
//            // 删除项目
//            sysRoleProjectOuterService.remove(roleProjectQuery);
//            List<SysRoleProjectOuter> list = new ArrayList<>();
//            for (String projectNo : projectNoList) {
//                SysRoleProjectOuter sysRoleProjectOuter = new SysRoleProjectOuter();
//                sysRoleProjectOuter.setRoleOuterId(roleId);
//                sysRoleProjectOuter.setProjectNo(projectNo);
//                list.add(sysRoleProjectOuter);
//            }
//            // 保存项目
//            boolean addBatch = sysRoleProjectOuterService.saveBatch(list);
//            if (addBatch) {
//                log.info("角色项目-成功保存角色项目");
//            }
//
//        }

        /* 先删除角色菜单表数据，再新增角色菜单 */
        if (CollUtil.isNotEmpty(menuIdList)) {
            // 删除菜单
            sysRoleMenuOuterService.remove(roleMenuQuery);
            List<SysRoleMenuOuter> list = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysRoleMenuOuter sysRoleMenuOuter = new SysRoleMenuOuter();
                sysRoleMenuOuter.setRoleOuterId(roleId);
                sysRoleMenuOuter.setMenuId(menuId);
                list.add(sysRoleMenuOuter);
            }
            // 保存角色菜单
            boolean addBatch = sysRoleMenuOuterService.saveBatch(list);
            if (addBatch) {
                log.info("角色菜单-成功保存角色菜单");
            }
        }else if (menuIdList != null && menuIdList.size() < 1) {
            // 删除用户角色
            sysRoleMenuOuterService.remove(roleMenuQuery);
            log.info("角色菜单-成功保存角色菜单");
        }

        /* 先删除用户角色表数据，再新增用户角色 */
        if (CollUtil.isNotEmpty(userIdList)) {
            // 删除用户角色
            sysUserRoleOuterService.remove(userRoleQuery);
            List<SysUserRoleOuter> list = new ArrayList<>();
            for (Long userId : userIdList) {
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
        }else if (menuIdList != null && menuIdList.size() < 1) {
            // 删除用户角色
            sysUserRoleOuterService.remove(userRoleQuery);
            log.info("用户角色-成功保存用户角色");
        }

        // 删除用户缓存信息
        RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
        // 重新查询角色权限并存入缓存
        selectRoleOuterPowerInfo(roleId);
        return true;
    }
    /**
     *  外部角色-人员配置-保存
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelSave(RolePersonnelDTO dto){
        log.info("进入外部角色-获取人员配置方法");

        if(ObjUtil.isEmpty(dto.getRoleId())){
            log.warn("找不到角色id");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色信息不能为空");
        }
        QueryWrapper<SysUserRoleOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRoleOuter::getRoleOuterId,dto.getRoleId());

        // 判断用户id 为空数组 则全部清除
        if(dto.getUserIds() != null && dto.getUserIds().size() == 0){
            sysUserRoleOuterService.remove(queryWrapper);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            selectRoleOuterPowerInfo(dto.getRoleId());
            return true;
        }
        sysUserRoleOuterService.remove(queryWrapper);

        List<SysUserRoleOuter> list = new ArrayList<>();
        SysUserRoleOuter sysUserRoleouter = null;
        for(long userId : dto.getUserIds()){
            sysUserRoleouter = new SysUserRoleOuter();
            sysUserRoleouter.setRoleOuterId(dto.getRoleId());
            sysUserRoleouter.setUserOuterId(userId);
            list.add(sysUserRoleouter);
            RedisHelp.delHash(AuthCacheConstants.USER_OUTER_HASH,Convert.toStr(userId));
        }
        boolean saveFlag = sysUserRoleOuterService.saveBatch(list);
        if(!saveFlag){
            log.warn("添加外部用户角色失败!");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加外部用户角色失败!");
        }
        selectRoleOuterPowerInfo(dto.getRoleId());
        return true;
    }

    /**
     *  角色-人员配置-删除功能
     * @param dto 角色id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelRoleInfoDelete(RoleUsersDTO dto){
        log.info("进入角色人员配置-删除");
        if(ObjUtil.isEmpty(dto.getRoleId())){
            log.warn("找不到角色id");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色id不能为空");
        }
        QueryWrapper<SysUserRoleOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysUserRoleOuter::getRoleOuterId,dto.getRoleId())
                .eq(SysUserRoleOuter::getUserOuterId,dto.getUserId());
        log.info("删除角色人员配置");
        sysUserRoleOuterService.remove(queryWrapper);

        log.info("修改角色缓存");
        selectRoleOuterPowerInfo(dto.getRoleId());
        log.info("清除用户缓存");
        RedisHelp.delHash(AuthCacheConstants.USER_OUTER_HASH,Convert.toStr(dto.getUserId()));
        return true;
    }

    @Override
    public RoleMenuPermissVO selectMenuListByRoleId(UserMenuPermissDTO dto) {

        log.info("进入获取菜单列表信息");
        RoleMenuPermissVO vo = new RoleMenuPermissVO();
        List<BaseSelectVO> loginUserVo = null;
        // 当前登录用户传入值
        UserMenuPermissDTO userMenuPermissDTO = new UserMenuPermissDTO();
        userMenuPermissDTO.setServiceCode(dto.getServiceCode());

        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            userMenuPermissDTO.setUserId(null);
            loginUserVo = sysMenuService.menuListByUserOuterId(userMenuPermissDTO);
        }
        else{
            userMenuPermissDTO.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            loginUserVo = sysMenuService.menuListByUserOuterId(userMenuPermissDTO);
        }

        // 当前授权角色-菜单查询
        List<SysMenu> userVoList = sysMenuService.selectListByRoleOuterId(dto);
        List<BaseSelectVO> roleVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setRoleVo(roleVo);
        return vo;
    }

    @Override
    public RoleProjectPermissVO selectProjectListByRoleId(Long roleId) {

        log.info("开始查询用户-项目权限");
        RoleProjectPermissVO vo = new RoleProjectPermissVO();
        List<ProjectParentVO> loginVoList = null;
        // 判断是否为管理员
        if(StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            loginVoList = sysProjectService.selectProjectNoByUserOuterId(null);
        }else {
            loginVoList = sysProjectService.selectProjectNoByUserOuterId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        List<BaseSelectVO> loginUserVo = loginVoList.stream().map(this::cast).toList();

        // 查询当前角色的项目权限
        List<ProjectParentVO> userVoList = sysProjectService.selectListByRoleOuterId(roleId);
        List<BaseSelectVO> userVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
    }

    @Override
    public List<BaseSelectVO> selectListByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }

    /**
     *  根据公司获取角色信息
     * @param companyIds 公司数组
     * @return
     */
    @Override
    public List<BaseSelectVO> selectRoleInfoByCompanyIds(List<Long> companyIds){
        return baseMapper.selectRoleInfoByCompanyIds(companyIds);
    }

    /**
     *  角色左侧树状图
     * @return
     */
    @Override
    public List<BaseSelectVO> tree() {
        log.info("进入内部角色树状图");
        String loginName = AuthHelp.getLoginName();
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            loginName = null;
        }
        List<BaseSelectVO> companyList = sysCompanyOuterService.selectOuterParent();
        List<Long> companyIds = new ArrayList<>();
        if (ObjUtil.isEmpty(companyList)) {
            log.warn("没有公司信息");
            return null;
        }
        for (BaseSelectVO personnelVO : companyList) {
            companyIds.add(Long.parseLong(personnelVO.getValue()));
        }
        List<BaseSelectVO> roles = selectRoleInfoByCompanyIds(companyIds);
//        List<BaseSelectVO> users = sysUserOuterService.selectUserRoleByCompanyIds(companyIds);
        if(CollUtil.isNotEmpty(roles)){
            roles.addAll(companyList);
        }
//        companyList.addAll(users);
        return roles;
    }
    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        if (obj instanceof SysMenu sysMenu) {
            vo.setValue(Convert.toStr(sysMenu.getId()));
            vo.setLabel(sysMenu.getName());
            vo.setParentValue(Convert.toStr(sysMenu.getParentId()));
            vo.setParentValues(sysMenu.getParentIds());
            vo.setSort(sysMenu.getSort());
        }
        return vo;
    }

}
