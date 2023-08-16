package cn.kun.auth.system.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.role.entity.dto.RoleEditDTO;
import cn.kun.auth.system.role.entity.dto.RolePersonnelDTO;
import cn.kun.auth.system.role.entity.dto.RoleUsersDTO;
import cn.kun.auth.system.role.mapper.SysRoleInnerMapper;
import cn.kun.auth.system.role.service.SysRoleProjectInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.auth.system.menu.entity.dto.UserMenuPermissDTO;
import cn.kun.auth.system.menu.entity.po.SysMenu;
import cn.kun.auth.system.menu.entity.vo.MenuRedisVo;
import cn.kun.auth.system.menu.service.SysMenuService;
import cn.kun.auth.system.project.entity.vo.PersonnelDetailVO;
import cn.kun.auth.system.project.entity.vo.ProjectRedisVO;
import cn.kun.auth.system.project.service.SysProjectService;
import cn.kun.auth.system.role.entity.dto.RoleAddDTO;
import cn.kun.auth.system.role.entity.dto.RolePageDTO;
import cn.kun.auth.system.role.entity.dto.RolePermissDTO;
import cn.kun.auth.system.role.entity.po.SysRoleInner;
import cn.kun.auth.system.role.entity.po.SysRoleMenuInner;
import cn.kun.auth.system.role.entity.po.SysRoleProjectInner;
import cn.kun.auth.system.role.entity.vo.PersonnelRoleVO;
import cn.kun.auth.system.role.entity.vo.RoleDetailVO;
import cn.kun.auth.system.role.entity.vo.RoleMenuPermissVO;
import cn.kun.auth.system.role.entity.vo.RolePageVO;
import cn.kun.auth.system.role.entity.vo.SysRoleInfoVO;
import cn.kun.auth.system.role.service.SysRoleInnerService;
import cn.kun.auth.system.role.service.SysRoleMenuInnerService;
import cn.kun.auth.system.user.entity.po.SysUserRoleInner;
import cn.kun.auth.system.user.entity.po.UserDetailList;
import cn.kun.auth.system.user.entity.vo.RoleProjectPermissVO;
import cn.kun.auth.system.user.entity.vo.UserPersonnelPageVO;
import cn.kun.auth.system.user.service.SysUserInnerService;
import cn.kun.auth.system.user.service.SysUserRoleInnerService;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.RoleInfoByUserIdVO;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
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

;

/**
 * <p>
 * 内部角色表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysRoleInnerServiceImpl extends ServiceImpl<SysRoleInnerMapper, SysRoleInner> implements SysRoleInnerService {
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysProjectService sysProjectService;

    @Resource
    private SysUserInnerService sysUserInnerService;
    @Resource
    private SysRoleProjectInnerService sysRoleProjectInnerService;
    @Resource
    private SysRoleMenuInnerService sysRoleMenuInnerService;
    @Resource
    private SysUserRoleInnerService sysUserRoleInnerService;
    @Resource
    private SysCompanyInnerService sysCompanyInnerService;
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
    public SysRoleInner add(RoleAddDTO dto) {

        log.info("内部角色-开始添加：{}", dto);
        /* 传入值复制到数据库对象 */
        SysRoleInner sysRoleInner = new SysRoleInner();
        BeanUtil.copyProperties(dto,sysRoleInner);
        sysRoleInner.setCompanyInnerId(dto.getCompanyId());
        // 添加对象
        boolean add = save(sysRoleInner);
        if (add) {
            log.info("内部角色-成功添加");
            log.info("内部角色-成功添加缓存");
            // 绑定刚添加的角色信息
            SysUserRoleInner sysUserRoleInner = new SysUserRoleInner();
            sysUserRoleInner.setUserInnerId(ConvertHelp.toLong(AuthHelp.getUserId()));
            sysUserRoleInner.setRoleInnerId(sysRoleInner.getId());
            if (!StrUtil.equals(AuthHelp.getLoginName(),AuthConstants.SYS_ADMIN)){
                boolean userRoleSave = sysUserRoleInnerService.save(sysUserRoleInner);
                if(!userRoleSave){
                    log.warn("内部用户角色-添加失败");
                    throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"用户角色-添加失败");
                }
            }
            RedisHelp.setHash(AuthCacheConstants.ROLE_INNER_HASH,sysRoleInner.getId().toString(),sysRoleInner);
        }
        else {
            log.warn("内部角色-添加失败");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"内部角色-添加失败");
        }
        return sysRoleInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleInner edit(RoleEditDTO dto) {

        log.info("内部角色-开始修改：{}", dto);
        /* 获取数据库对象 */
        SysRoleInner sysRoleInner = getById(dto.getId());
        if (ObjUtil.isNull(sysRoleInner)) {
            log.warn("内部角色-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "角色-修改的数据不存在，无法修改");
        }

        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysRoleInner);
        sysRoleInner.setCompanyInnerId(dto.getCompanyId());
        // 修改对象
        boolean edit = updateById(sysRoleInner);
        // 判断是否修改成功
        if (edit) {
            log.info("内部角色-修改成功");
            log.info("内部角色-修改成功-清除缓存");
            RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
            log.info("内部角色-修改成功-获取角色最新缓存");
            selectRoleInnerPowerInfo(dto.getId());
        }
        else {
            log.warn("内部角色-修改失败");
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"角色-修改失败");
        }
        return sysRoleInner;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("内部角色-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("角色id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "角色id不能为空");
        }
        // 组装删除用户角色参数
        QueryWrapper<SysUserRoleInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRoleInner::getRoleInnerId,id);
        // 组装删除角色菜单参数
        QueryWrapper<SysRoleMenuInner> menuInnerQueryWrapper = new QueryWrapper<>();
        menuInnerQueryWrapper.lambda().eq(SysRoleMenuInner::getRoleInnerId,id);

        // 删除角色前，需要校验当前角色是否绑定用户，如果有，则不能删除，反之即可。
        List<Long> sysUserInners = sysUserInnerService.selectUserIdsByRoleInnerId(id);
        /* 判断当前角色是否绑定了用户 */
        if (CollUtil.isEmpty(sysUserInners)){
            // 删除角色信息
            boolean remove = removeById(id);
            if (remove) {
                log.info("内部角色-删除成功");
                log.info("内部角色-删除成功-清除缓存");
                // 删除用户角色管理关系
                sysUserRoleInnerService.remove(queryWrapper);
                // 删除角色菜单管理关系
                sysRoleMenuInnerService.remove(menuInnerQueryWrapper);
                RedisHelp.delHash(AuthCacheConstants.ROLE_INNER_HASH,Convert.toStr(id));
                RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
            }
            else{
                log.warn("内部角色-删除失败");
                throw new BusinessException(ErrorCodeConstants.NULL, "角色-删除失败");
            }
        } else if (sysUserInners.size() < 2 &&
                (ObjUtil.equals(sysUserInners.get(0), AuthHelp.getUserId())
                        || StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName()))) {
            log.info("内部角色-删除成功");
            log.info("内部角色-删除成功-清除缓存");
            removeById(id);
            // 删除用户角色管理关系
            sysUserRoleInnerService.remove(queryWrapper);
            // 删除角色菜单管理关系
            sysRoleMenuInnerService.remove(menuInnerQueryWrapper);
            RedisHelp.delHash(AuthCacheConstants.ROLE_INNER_HASH, Convert.toStr(id));
            RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
        } else {
            log.warn("内部角色-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前角色绑定了用户，不能删除当前角色信息");
        }
    }

    /**
     * 通过内部角色id查询角色缓存信息
     * @param roleInnerId
     * @return
     */
    @Override
    public SysRoleInfoVO getRoleInnerInfoByRoleInnerId(Long roleInnerId) {
        SysRoleInfoVO sysRoleInfoVo = (SysRoleInfoVO) RedisHelp.getHash(AuthCacheConstants.ROLE_INNER_HASH, String.valueOf(roleInnerId));
        if (ObjUtil.isNull(sysRoleInfoVo)) {
            return selectRoleInnerPowerInfo(roleInnerId);
        }
        return sysRoleInfoVo;
    }

    /**
     *  获取内部角色权限信息
     * @param roleInnerId 角色id
     * @return
     */
    @Override
    public SysRoleInfoVO selectRoleInnerPowerInfo(Long roleInnerId){
        SysRoleInfoVO sysRoleInfoInnerVo = new SysRoleInfoVO();
        // 查询角色信息
        SysRoleInner sysRoleInnerInfo = baseMapper.selectById(roleInnerId);
        if (ObjUtil.isNotNull(sysRoleInnerInfo)) {
            // 赋值角色信息
            sysRoleInfoInnerVo.setRoleId(roleInnerId);
            sysRoleInfoInnerVo.setRoleName(sysRoleInnerInfo.getName());
            sysRoleInfoInnerVo.setRoleType(sysRoleInnerInfo.getType());
            // 查询角色信息
            sysRoleInfoInnerVo.setPermission(sysRoleInnerInfo.getPermission());
//                 查询项目权限信息
            List<ProjectRedisVO> roleProjectInnerAuth = sysProjectService.getRoleProjectInnerAuth(roleInnerId);
            // 查询菜单权限信息
            List<MenuRedisVo> roleMenuInnerAuth = sysMenuService.getRoleMenuInnerAuth(roleInnerId);
            sysRoleInfoInnerVo.setRoleProjectAuth(roleProjectInnerAuth);
            sysRoleInfoInnerVo.setRoleMenuAuth(roleMenuInnerAuth);
            // 将角色信息加入redis缓存
            RedisHelp.setHash(AuthCacheConstants.ROLE_INNER_HASH,  String.valueOf(sysRoleInnerInfo.getId()),sysRoleInfoInnerVo);
        }
        return sysRoleInfoInnerVo;
    }
    /**
     * 通过内部用户id查询角色信息
     * @param userInnerId
     * @return
     */
    @Override
    public List<RoleInfoByUserIdVO> getRoleInnerInfoByUserInnerId(Long userInnerId) {
        return baseMapper.getRoleInnerInfoByUserInnerId(userInnerId);
    }

    /**
     *  角色-人员配置---获取角色下所有用户信息
     * @param roleId 角色id
     * @param companyIds 公司id
     * @return
     */
    @Override
    public List<UserDetailList> selectInnerPersonnelInfoRoleId(Long roleId, List<String> companyIds){
        return baseMapper.selectInnerPersonnelInfoRoleId(roleId,companyIds);
    }


    /**
     *  角色-人员配置---获取角色下所有用户信息、当前用户所拥有的公司信息、部门信息、职位信息
     * @param roleId 角色id
     * @return
     */
    @Override
    public PersonnelRoleVO personnelRoleInfo(Long roleId){
        log.info("进入角色-获取人员配置方法");
        PersonnelRoleVO personnelRoleVO = new PersonnelRoleVO();
        Long companyId = ConvertHelp.toLong(AuthHelp.getCompanyId());
        String loginName = AuthHelp.getLoginName();
        // 调用获取树结构
        PersonnelDetailVO personnelDetailVO = sysProjectService.personnelTree();
        log.info("进入角色-人员配置-获取内部人员信息");
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, loginName)) {
            personnelRoleVO.setUsersInner(selectInnerPersonnelInfoRoleId(roleId, null));
        } else {
            List<String> comList = new ArrayList<>();
            comList.add(companyId.toString());
            personnelRoleVO.setUsersInner(selectInnerPersonnelInfoRoleId(roleId, comList));
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
        log.info("进入角色-获取人员配置方法");
        if(ObjUtil.isEmpty(dto.getRoleId())){
            log.warn("找不到角色id");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色信息不能为空");
        }
        String loginName = AuthHelp.getLoginName();
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

        return baseMapper.personnelRoleInfoPage(page,dto,loginName);
    }
    /**
     *  内部角色-人员配置-保存
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean personnelSave(RolePersonnelDTO dto){
        log.info("进入内部角色-获取人员配置方法");

        if(ObjUtil.isEmpty(dto.getRoleId())){
            log.warn("找不到角色id");
            throw new BusinessException(ErrorCodeConstants.NULL,"角色id不能为空");
        }
        QueryWrapper<SysUserRoleInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRoleInner::getRoleInnerId,dto.getRoleId());

        // 判断用户id 为空数组 则全部清除
        if(dto.getUserIds() != null && dto.getUserIds().size() == 0){
            sysUserRoleInnerService.remove(queryWrapper);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            selectRoleInnerPowerInfo(dto.getRoleId());
            return true;
        }

        sysUserRoleInnerService.remove(queryWrapper);

        List<SysUserRoleInner> list = new ArrayList<>();
        SysUserRoleInner sysUserRoleInner = null;
        for(long userId : dto.getUserIds()){
            sysUserRoleInner = new SysUserRoleInner();
            sysUserRoleInner.setRoleInnerId(dto.getRoleId());
            sysUserRoleInner.setUserInnerId(userId);
            list.add(sysUserRoleInner);
            RedisHelp.delHash(AuthCacheConstants.USER_INNER_HASH,Convert.toStr(userId));
        }
        boolean saveFlag = sysUserRoleInnerService.saveBatch(list);
        if(!saveFlag){
            log.warn("添加用户角色失败!");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加用户角色失败!");
        }
        selectRoleInnerPowerInfo(dto.getRoleId());
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
        QueryWrapper<SysUserRoleInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysUserRoleInner::getRoleInnerId,dto.getRoleId())
                .eq(SysUserRoleInner::getUserInnerId,dto.getUserId());
        log.info("删除角色人员配置");
        sysUserRoleInnerService.remove(queryWrapper);

        log.info("修改角色缓存");
        selectRoleInnerPowerInfo(dto.getRoleId());
        log.info("清除用户缓存");
        RedisHelp.delHash(AuthCacheConstants.USER_INNER_HASH,Convert.toStr(dto.getUserId()));
        return true;
    }

    @Override
    public List<BaseSelectVO> selectListByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }


    /**
     * 根据角色id查询菜单权限信息
     * @param dto
     * @return
     */
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
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }
        else{
            userMenuPermissDTO.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
            loginUserVo = sysMenuService.menuListByUserInnerId(userMenuPermissDTO);
        }

        // 当前授权角色-菜单查询
        List<SysMenu> userVoList = sysMenuService.selectListByRoleInnerId(dto);
        List<BaseSelectVO> roleVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setRoleVo(roleVo);
        return vo;

    }

    @Override
    public RoleProjectPermissVO selectProjectListByRoleId(Long roleId) {

        log.info("开始查询角色-项目权限");
        RoleProjectPermissVO vo = new RoleProjectPermissVO();
        List<ProjectParentVO> loginVoList = null;
        // 判断是否为管理员
        if(StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            loginVoList = sysProjectService.selectProjectNoByUserInnerId(null);
        }else {
            loginVoList = sysProjectService.selectProjectNoByUserInnerId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        List<BaseSelectVO> loginUserVo = loginVoList.stream().map(this::cast).toList();

        // 查询当前角色的项目权限
        List<ProjectParentVO> userVoList = sysProjectService.selectListByRoleInnerId(roleId);
        List<BaseSelectVO> userVo = userVoList.stream().map(this::cast).toList();

        vo.setLoginUserVo(loginUserVo);
        vo.setUserVo(userVo);
        return vo;
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

        QueryWrapper<SysRoleProjectInner> roleProjectQuery = new QueryWrapper<>();
        roleProjectQuery.lambda().eq(SysRoleProjectInner::getRoleInnerId,roleId);
        QueryWrapper<SysRoleMenuInner> roleMenuQuery  = new QueryWrapper<>();
        roleMenuQuery.lambda().eq(SysRoleMenuInner::getRoleInnerId,roleId);
        QueryWrapper<SysUserRoleInner> userRoleQuery  = new QueryWrapper<>();
        userRoleQuery.lambda().eq(SysUserRoleInner::getRoleInnerId,roleId);

        // 判断是否取消所有授权
//        if (CollUtil.isEmpty(projectNoList) && CollUtil.isEmpty(menuIdList) && CollUtil.isEmpty(userIdList)){
//            log.warn("用户取消了角色所有授权");
//            // 删除数据表
//            sysRoleProjectInnerService.remove(roleProjectQuery);
//            sysRoleMenuInnerService.remove(roleMenuQuery);
//            sysUserRoleInnerService.remove(userRoleQuery);
//            // 删除用户缓存信息
//            RedisUtils.del(AuthCacheConstants.USER_INNER_HASH);
//            // 重新查询角色权限并存入缓存
//            selectRoleInnerPowerInfo(roleId);
//            return true;
//        }

        /* 先删除角色项目表数据，再新增角色项目 */
//        if (CollUtil.isNotEmpty(projectNoList)) {
//            // 删除项目
//            sysRoleProjectInnerService.remove(roleProjectQuery);
//            List<SysRoleProjectInner> list = new ArrayList<>();
//            for (String projectNo : projectNoList) {
//                SysRoleProjectInner sysRoleProjectInner = new SysRoleProjectInner();
//                sysRoleProjectInner.setRoleInnerId(roleId);
//                sysRoleProjectInner.setProjectNo(projectNo);
//                list.add(sysRoleProjectInner);
//            }
//            // 保存项目
//            boolean addBatch = sysRoleProjectInnerService.saveBatch(list);
//            if (addBatch) {
//                log.info("角色项目-成功保存角色项目");
//            }
//        }

        /* 先删除角色菜单表数据，再新增角色菜单 */
        if (CollUtil.isNotEmpty(menuIdList)) {
            // 删除菜单
            sysRoleMenuInnerService.remove(roleMenuQuery);
            List<SysRoleMenuInner> list = new ArrayList<>();
            for (Long menuId : menuIdList) {
                SysRoleMenuInner sysRoleMenuInner = new SysRoleMenuInner();
                sysRoleMenuInner.setRoleInnerId(roleId);
                sysRoleMenuInner.setMenuId(menuId);
                list.add(sysRoleMenuInner);
            }
            // 保存角色菜单
            boolean addBatch = sysRoleMenuInnerService.saveBatch(list);
            if (addBatch) {
                log.info("角色菜单-成功保存角色菜单");
            }
        }else if (menuIdList != null && menuIdList.size() < 1) {
            // 删除用户角色
            sysRoleMenuInnerService.remove(roleMenuQuery);
            log.info("角色菜单-成功保存角色菜单");
        }


        /* 先删除用户角色表数据，再新增用户角色 */
        if (CollUtil.isNotEmpty(userIdList)) {
            // 删除用户角色
            sysUserRoleInnerService.remove(userRoleQuery);
            List<SysUserRoleInner> list = new ArrayList<>();
            for (Long userId : userIdList) {
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
        }else if (userIdList != null && userIdList.size() < 1) {
            // 删除用户角色
            sysUserRoleInnerService.remove(userRoleQuery);
            log.info("用户角色-成功保存用户角色");
        }

        // 删除用户缓存信息
        RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
        // 重新查询角色权限并存入缓存
        selectRoleInnerPowerInfo(roleId);
        return true;
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
        List<BaseSelectVO> companyList = sysCompanyInnerService.selectInnerParent();
        List<Long> companyIds = new ArrayList<>();
        if (ObjUtil.isEmpty(companyList)) {
            log.warn("没有公司信息");
            return null;
        }
        for (BaseSelectVO personnelVO : companyList) {
            companyIds.add(Long.parseLong(personnelVO.getValue()));
        }
        List<BaseSelectVO> roles = selectRoleInfoByCompanyIds(companyIds);
//        List<BaseSelectVO> users = sysUserInnerService.selectUserRoleByCompanyIds(companyIds);
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
