package cn.kun.auth.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sevnce.auth.job.entity.po.SysJobMenuInner;
import com.sevnce.auth.job.entity.po.SysJobMenuOuter;
import com.sevnce.auth.job.service.SysJobMenuInnerService;
import com.sevnce.auth.job.service.SysJobMenuOuterService;
import com.sevnce.auth.menu.entity.dto.MenuAddDTO;
import com.sevnce.auth.menu.entity.dto.MenuEditDTO;
import com.sevnce.auth.menu.entity.dto.MenuPageDTO;
import com.sevnce.auth.menu.entity.dto.UserMenuPermissDTO;
import com.sevnce.auth.menu.entity.po.SysMenu;
import com.sevnce.auth.menu.entity.vo.MenuDetailVO;
import com.sevnce.auth.menu.entity.vo.MenuPageVO;
import com.sevnce.auth.menu.entity.vo.MenuRedisVo;
import com.sevnce.auth.menu.mapper.SysMenuMapper;
import com.sevnce.auth.menu.service.SysMenuService;
import com.sevnce.auth.role.entity.po.SysRoleMenuInner;
import com.sevnce.auth.role.entity.po.SysRoleMenuOuter;
import com.sevnce.auth.role.service.SysRoleMenuInnerService;
import com.sevnce.auth.role.service.SysRoleMenuOuterService;
import com.sevnce.auth.user.entity.po.SysUserMenuInner;
import com.sevnce.auth.user.entity.po.SysUserMenuOuter;
import com.sevnce.auth.user.service.SysUserInnerService;
import com.sevnce.auth.user.service.SysUserMenuInnerService;
import com.sevnce.auth.user.service.SysUserMenuOuterService;
import com.sevnce.base.api.service.system.BaseDictService;
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
import com.sevnce.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserMenuInnerService sysUserMenuInnerService;
    @Autowired
    private SysUserMenuOuterService sysUserMenuOuterService;
    @Autowired
    private SysUserInnerService sysUserInnerService;
    @Autowired
    private SysJobMenuOuterService sysJobMenuOuterService;
    @Autowired
    private SysJobMenuInnerService sysJobMenuInnerService;
    @Autowired
    private SysRoleMenuInnerService sysRoleMenuInnerService;
    @Autowired
    private SysRoleMenuOuterService sysRoleMenuOuterService;
    @Autowired
    private BaseDictService baseDictService;



    @Override
    public Page<MenuPageVO> page(MenuPageDTO dto) {

        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            dto.setUserId(CastHelp.toLong(AuthHelp.getUserId()));
        }
        Page<MenuPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        List<MenuPageVO> list;
        if (AuthHelp.getCharacteristic()) {
            list = baseMapper.selectInnerPage(dto);
        } else {
            list = baseMapper.selectOuterPage(dto);
        }
        // 字典翻译菜单
        for (MenuPageVO vo : list) {
            vo.setType(baseDictService.getLabel(AuthDictTypeConstants.MENU,vo.getType()));
        }
        // 分页查询
        list = new BaseTreeBuild(list).buildTree();
        page.setTotal(list.size());
        page.setRecords(list.stream().skip((long) (dto.getPageNo() - 1) * dto.getPageSize()).limit(dto.getPageSize()).collect(Collectors.toList()));
        return page;
    }

    @Override
    public MenuDetailVO detail(Long id) {
        log.info("进入菜单详情");
        // 查询详情
        SysMenu sysMenu = getById(id);
        if (ObjUtil.isEmpty(sysMenu)) {
            log.warn("找不到该菜单!");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "找不到该菜单");
        }
        /* 复制到返回值 */
        MenuDetailVO vo = new MenuDetailVO();
        BeanUtil.copyProperties(sysMenu, vo);
        // 获取上级菜单名称
        if ((ObjUtil.isNotNull(sysMenu.getParentId()) && (sysMenu.getParentId()) >1)){
            SysMenu obj = getById(sysMenu.getParentId());
            if (ObjUtil.isNotEmpty(obj)) {
                String parentMenu = obj.getName();
                vo.setParentMenu(parentMenu);
                vo.setParentMenuId(obj.getId());
            }
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenu add(MenuAddDTO dto) {

        log.info("菜单-开始添加：{}", dto);

        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 传入值复制到数据库对象 */
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(dto,sysMenu);

        // 添加对象
        boolean add = save(sysMenu);
        if (!add) {
            log.warn("菜单-添加失败");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"添加失败");
        }
        log.info("菜单-成功添加");

        Long userId = CastHelp.toLong(AuthHelp.getUserId());
        // 关联菜单和内部用户
        if(AuthHelp.getCharacteristic()){
            if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
                SysUserMenuInner sysUserMenuInner = new SysUserMenuInner();
                sysUserMenuInner.setMenuId(sysMenu.getId());
                sysUserMenuInner.setUserInnerId(userId);
                sysUserMenuInnerService.save(sysUserMenuInner);
                log.info("关联菜单和内部用户");
                // 重新内部用户获取缓存
                sysUserInnerService.selectUserInnerPowerInfo(userId);
            }
            return sysMenu;
        }
        return sysMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenu edit(MenuEditDTO dto) {

        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));


        if(!AuthHelp.getCharacteristic()){
            log.warn("外部用户不能修改菜单");
            throw new BusinessException(ErrorCodeConstants.NO_AUTH,"");
        }
            log.info("菜单-开始修改：{}", dto);
            /* 获取数据库对象 */
            SysMenu sysMenu = getById(dto.getId());
            if (ObjUtil.isNull(sysMenu)) {
                log.warn("菜单-修改的数据不存在，无法修改");
                throw new BusinessException(ErrorCodeConstants.WITHOUT, "菜单-修改的数据不存在，无法修改");
            }

            // 传入值复制到数据库对象
            BeanUtil.copyProperties(dto, sysMenu);
            // 修改对象
            boolean edit = updateById(sysMenu);
            // 判断是否修改成功
            if (!edit) {
                log.warn("菜单-修改失败");
                throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"菜单修改失败");
            }
            log.info("菜单-修改成功");
            log.info("菜单-修改成功-清除缓存");
            // 清除所有用户、角色缓存
            RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_OUTER_HASH);
            return sysMenu;
        }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("用户登录信息-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }

        // 删除菜单前，需要校验当前菜单是否有下级菜单，如果有，则不能删除，反之即可。
        QueryWrapper<SysMenu> queryMenu = new QueryWrapper<>();
        queryMenu.lambda().eq(SysMenu::getParentId,id);
        List<SysMenu> menuList = list(queryMenu);
        /* 判断是否存在下级菜单 */
        if (CollUtil.isEmpty(menuList)){
            // 删除
            boolean remove = removeById(id);
            // 判断是否删除
            if (!remove) {
                log.warn("菜单-删除失败");
                throw new BusinessException(ErrorCodeConstants.NULL, "菜单-删除失败");
            }
            Long userId = CastHelp.toLong(AuthHelp.getUserId());
            // 关联菜单和内部用户
            if(AuthHelp.getCharacteristic()){
                // 删除关联菜单和内部用户
                QueryWrapper<SysUserMenuInner> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .eq(SysUserMenuInner::getMenuId,id)
                        .eq(SysUserMenuInner::getUserInnerId,userId);
                sysUserMenuInnerService.remove(queryWrapper);
                log.info("删除关联菜单和内部用户");
                // 删除关联菜单和内部职位信息
                QueryWrapper<SysJobMenuInner> jobMenuWrapper = new QueryWrapper<>();
                jobMenuWrapper.lambda()
                        .eq(SysJobMenuInner::getMenuId,id);
                sysJobMenuInnerService.remove(jobMenuWrapper);
                log.info("删除关联菜单和内部职位信息");
                // 删除关联菜单和内部角色信息
                QueryWrapper<SysRoleMenuInner> roleMenuInnerQueryWrapper = new QueryWrapper<>();
                roleMenuInnerQueryWrapper.lambda().
                        eq(SysRoleMenuInner::getMenuId,id);
                sysRoleMenuInnerService.remove(roleMenuInnerQueryWrapper);
                log.info("删除关联菜单和内部角色信息");
            }else {
                // 关联菜单和内部用户
                QueryWrapper<SysUserMenuOuter> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .eq(SysUserMenuOuter::getMenuId,id)
                        .eq(SysUserMenuOuter::getUserOuterId,userId);
                sysUserMenuOuterService.remove(queryWrapper);
                log.info("删除关联菜单和外部用户");
                // 删除关联菜单和内部职位信息
                QueryWrapper<SysJobMenuOuter> jobOuterMenuWrapper = new QueryWrapper<>();
                jobOuterMenuWrapper.lambda()
                        .eq(SysJobMenuOuter::getMenuId,id);
                sysJobMenuOuterService.remove(jobOuterMenuWrapper);
                log.info("删除关联菜单和外部职位信息");
                // 删除关联菜单和内部角色信息
                QueryWrapper<SysRoleMenuOuter> roleMenuOuterQueryWrapper = new QueryWrapper<>();
                roleMenuOuterQueryWrapper.lambda().
                        eq(SysRoleMenuOuter::getMenuId,id);
                sysRoleMenuOuterService.remove(roleMenuOuterQueryWrapper);
                log.info("删除关联菜单和外部角色信息");
            }
            log.info("菜单-删除成功-清除缓存");
            // 清除所有用户、角色缓存
            RedisHelp.del(AuthCacheConstants.USER_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.USER_OUTER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_INNER_HASH);
            RedisHelp.del(AuthCacheConstants.ROLE_OUTER_HASH);
        }
        else {
            log.warn("菜单-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前菜单有下级菜单信息，不能删除当前菜单信息");
        }
    }




    /**
     * 根据内部用户ID查询权限列表
     * @param userId 内部用户ID
     * @return 权限列表
     */
    @Override
    public List<String> selectPermsInner(Long userId) {
        return baseMapper.selectPermsInner(userId);
    }
    /**
     * 根据外部用户ID查询权限列表
     * @param userId 内部用户ID
     * @return 权限列表
     */
    @Override
    public List<String> selectPermsOuter(Long userId) {
        return baseMapper.selectPermsOuter(userId);
    }


    /**
     * 通过内部角色id查询菜单权限信息
     * @param roleInnerId
     * @return
     */
    @Override
    public List<MenuRedisVo> getRoleMenuInnerAuth(Long roleInnerId) {
        return baseMapper.getRoleMenuInnerAuth(roleInnerId);
    }

    /**
     * 通过外部角色id查询菜单权限信息
     * @param roleOuterId
     * @return
     */
    @Override
    public List<MenuRedisVo> getRoleMenuOuterAuth(Long roleOuterId) {
        return baseMapper.getRoleMenuOuterAuth(roleOuterId);
    }


    /**
     *  根据登录用户获取当前用户所拥有的菜单信息
     * @return
     */
    @Override
    public List<BaseSelectVO> menuList(UserMenuPermissDTO dto){

        log.info("进入获取菜单列表信息");
        List<SysMenu> list = null;
        // 判断是否为内部用户
        if (AuthHelp.getCharacteristic()){
            // 判断是否为内部用户管理员
            if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
                list = baseMapper.menuList(dto.getUserId(),dto.getServiceCode());
                if (CollUtil.isNotEmpty(list)){
                    return list.stream().map(this::cast).toList();
                }
            }else {
                list = baseMapper.menuList(null,dto.getServiceCode());
                if (CollUtil.isNotEmpty(list)){
                    return list.stream().map(this::cast).toList();
                }
            }
        }
        // 外部用户
        list = baseMapper.menuListByUserOuterId(dto.getUserId(),dto.getServiceCode());
        if (CollUtil.isNotEmpty(list)){
            return list.stream().map(this::cast).toList();
        }
        return null;
    }

    /**
     *  根据获取当前用户所拥有的系统名称
     * @return
     */
    @Override
    public List<BaseSelectVO> serviceCode() {
        Long userId = CastHelp.toLong(AuthHelp.getUserId());
        List<String> stringList = null;
        List<BaseSelectVO> baseSelectVOList = new ArrayList<>();
        if (AuthHelp.getCharacteristic()) {
            if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
                stringList = baseMapper.serviceCode(null);
            } else {
                stringList = baseMapper.serviceCode(userId);
            }
        } else {
            stringList = baseMapper.serviceCode(userId);
        }

        for (String str : stringList) {
            BaseSelectVO baseSelectVO = new BaseSelectVO();
            baseSelectVO.setValue(str);
            baseSelectVO.setLabel(baseDictService.getLabel(AuthDictTypeConstants.SERVICE_CODE, str));
            baseSelectVOList.add(baseSelectVO);
        }
        return baseSelectVOList;
    }

    @Override
    public List<BaseSelectVO> menuListByUserInnerId(UserMenuPermissDTO dto) {

        log.info("进入获取菜单列表信息");
        List<SysMenu> list = null;
        list = baseMapper.menuList(dto.getUserId(),dto.getServiceCode());
        return list.stream().map(this::cast).toList();
    }

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */
    @Override
    public List<BaseSelectVO> innerMenuListInfo(Long userId){
        log.info("进入内部用户获取当前用户所拥有的菜单信息（不包含上级信息）");
        List<SysMenu> list = null;
        list = baseMapper.innerMenuListInfo(userId);
        return list.stream().map(this::cast).toList();
    }

    /**
     *  根据内部用户获取当前用户所拥有的菜单信息（不包含上级信息）
     * @return
     */
    @Override
    public List<BaseSelectVO> outerMenuListInfo(Long userId){
        log.info("进入内部用户获取当前用户所拥有的菜单信息（不包含上级信息）");
        List<SysMenu> list = null;
        list = baseMapper.outerMenuListInfo(userId);
        return list.stream().map(this::cast).toList();
    }


    @Override
    public List<BaseSelectVO> menuListByUserOuterId(UserMenuPermissDTO dto) {

        log.info("进入获取菜单列表信息");
        List<SysMenu> list = null;
        list = baseMapper.menuListByUserOuterId(dto.getUserId(),dto.getServiceCode());
        return list.stream().map(this::cast).toList();
    }




    /**
     * 通过内部职位id查询菜单权限信息
     * @param dto
     * @return
     */
    @Override
    public List<BaseSelectVO> selectListByJobInnerId(UserMenuPermissDTO dto) {
        List<SysMenu> list = baseMapper.selectListByJobInnerId(dto);
        return list.stream().map(this::cast).toList();
    }

    /**
     * 根据外部职位id查询该职位的菜单权限信息
     * @param dto
     * @return
     */
    @Override
    public List<BaseSelectVO> selectListByJobOuterId(UserMenuPermissDTO dto){
        List<SysMenu> list = baseMapper.selectListByJobOuterId(dto);
        return list.stream().map(this::cast).toList();
    }

    @Override
    public List<SysMenu> selectListByRoleInnerId(UserMenuPermissDTO dto) {
        return baseMapper.selectListByRoleInnerId(dto);
    }

    @Override
    public List<SysMenu> selectListByRoleOuterId(UserMenuPermissDTO dto) {
        return baseMapper.selectListByRoleOuterId(dto);
    }


    /**
     *  角色左侧树状图
     * @return
     */
    @Override
    public List<BaseSelectVO> tree() {
        log.info("进入内部角色树状图");
        Long userId = CastHelp.toLong(AuthHelp.getUserId());
        List<SysMenu> list = null;
        // 判断是否为内部用户管理员
        if(!StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())){
            if(AuthHelp.getCharacteristic()){
                list = baseMapper.menuList(userId,null);
                if (CollUtil.isNotEmpty(list)){
                    return list.stream().map(this::tree).toList();
                }
            }else {
                list = baseMapper.menuListByUserOuterId(userId,null);
                if (CollUtil.isNotEmpty(list)){
                    return list.stream().map(this::tree).toList();
                }
            }
        }else {
            list = baseMapper.menuList(null,null);
            if (CollUtil.isNotEmpty(list)){
                return list.stream().map(this::tree).toList();
            }
        }
        return null;
    }

    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO tree(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        if (obj instanceof SysMenu sysMenu) {
            vo.setValue(sysMenu.getId().toString());
            vo.setLabel(sysMenu.getName());
            vo.setType("menu");
            vo.setParentValue(sysMenu.getParentId().toString());
            vo.setParentValues(sysMenu.getParentIds());
        }
        return vo;
    }

    /**
     * 转换
     * @param sysMenu 菜单对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(SysMenu sysMenu) {
        
        BaseSelectVO vo = new BaseSelectVO();
        vo.setValue(Convert.toStr(sysMenu.getId()));
        vo.setLabel(sysMenu.getName());
        vo.setParentValue(Convert.toStr(sysMenu.getParentId()));
        vo.setParentValues(sysMenu.getParentIds());
        vo.setSort(sysMenu.getSort());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serviceCode",sysMenu.getServiceCode());
        vo.setExpand(jsonObject);
        return vo;
    }

    /**
     * 转换
     * @param menuPageVO 菜单-分页-返回值
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(MenuPageVO menuPageVO) {

        BaseSelectVO vo = new BaseSelectVO();
        vo.setValue(Convert.toStr(menuPageVO.getId()));
        vo.setLabel(menuPageVO.getName());
        vo.setParentValue(Convert.toStr(menuPageVO.getParentId()));
        vo.setParentValues(menuPageVO.getParentIds());
        vo.setSort(menuPageVO.getSort());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serviceCode",menuPageVO.getServiceCode());
        vo.setExpand(jsonObject);
        vo.setType(baseDictService.getLabel(AuthDictTypeConstants.MENU,vo.getType()));
        return vo;
    }

    /**
     *  获取系统编码
     * @return 菜单列表
     */
    @Override
    public  List<SysMenu> menuServiceCode(){
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().groupBy(SysMenu::getServiceCode);
        return list(queryWrapper);
    }
}
