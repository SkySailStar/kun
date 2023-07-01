package cn.kun.auth.system.role.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.kun.auth.system.role.mapper.SysRoleMenuOuterMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.kun.auth.system.role.entity.dto.RoleMenuDTO;
import cn.kun.auth.system.role.entity.po.SysRoleMenuOuter;
import cn.kun.auth.system.role.service.SysRoleMenuOuterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.role.service.SysRoleOuterService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 外部角色菜单表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysRoleMenuOuterServiceImpl extends ServiceImpl<SysRoleMenuOuterMapper, SysRoleMenuOuter> implements SysRoleMenuOuterService {
    @Autowired
    private SysRoleOuterService sysRoleOuterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleMenuDTO dto){

        log.info("外部角色菜单-开始添加：{}", dto);
        // 外部角色ID
        Long roleId = dto.getRoleId();
        // 新菜单ID列表转Map，空间换时间，
        List<Long> newMenuIdList = dto.getMenuIdList();
        // 通用查询条件
        QueryWrapper<SysRoleMenuOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(SysRoleMenuOuter::getRoleOuterId, roleId);
        /* 如果菜单ID列表为空，删除所有关联的菜单 */
        if (CollUtil.isEmpty(newMenuIdList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysRoleMenuOuter> oldList = list(qw);
        // 新数据列表
        List<SysRoleMenuOuter> newList = newMenuIdList.stream().map(menuId -> {
            SysRoleMenuOuter newObj = new SysRoleMenuOuter();
            newObj.setRoleOuterId(roleId);
            newObj.setMenuId(menuId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysRoleMenuOuter> removeList = new LinkedList<>();
        // 新增列表
        List<SysRoleMenuOuter> addList = new LinkedList<>();
        // 修改列表
        List<SysRoleMenuOuter> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysRoleMenuOuter> oldMap = oldList.stream().collect(Collectors.toMap(SysRoleMenuOuter::getMenuId, Function.identity()));
        Map<Long, SysRoleMenuOuter> newMap = newList.stream().collect(Collectors.toMap(SysRoleMenuOuter::getMenuId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysRoleMenuOuter oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getMenuId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysRoleMenuOuter newObj : newList) {
            if (!oldMap.containsKey(newObj.getMenuId())) {
                addList.add(newObj);
            }
        }
        // 取出删除列表的长度进行循环，避免因为删除列表的长度减少而减少循环次数
        int size = removeList.size();
        for (int i = 0; i < size; i++) {
            // 如果新增列表为空，说明新增项已全部添加到修改列表，不用再循环，跳出
            if (CollUtil.isEmpty(addList)) {
                break;
            }
            /* 为了节省空间，将准备删除的项改为新增项进行修改 */
            SysRoleMenuOuter addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("角色菜单-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("角色菜单-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("角色菜单-成功批量删除");
        }
        RedisHelp.delHash(AuthCacheConstants.USER_OUTER_HASH);
        // 更新角色缓存
        sysRoleOuterService.selectRoleOuterPowerInfo(roleId);
    }
    
}
