package cn.kun.auth.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sevnce.auth.user.entity.dto.RoleUserDTO;
import com.sevnce.auth.user.entity.dto.UserRoleDTO;
import com.sevnce.auth.user.entity.po.SysUserRoleInner;
import com.sevnce.auth.user.mapper.SysUserRoleInnerMapper;
import com.sevnce.auth.user.service.SysUserInnerService;
import com.sevnce.auth.user.service.SysUserRoleInnerService;
import com.sevnce.base.core.cache.constant.AuthCacheConstants;
import com.sevnce.base.core.cache.util.RedisHelp;
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
 * 内部用户角色表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysUserRoleInnerServiceImpl extends ServiceImpl<SysUserRoleInnerMapper, SysUserRoleInner> implements SysUserRoleInnerService {
    @Autowired
    private SysUserInnerService sysUserInnerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserRoleDTO dto){

        log.info("内部用户角色-开始添加：{}", dto);
        // 内部用户ID
        Long userId = dto.getUserId();
        // 新角色ID列表转Map，空间换时间，
        List<Long> newRoleIdList = dto.getRoleIdList();
        // 通用查询条件
        QueryWrapper<SysUserRoleInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserRoleInner::getUserInnerId, userId);
        /* 如果角色ID列表为空，删除所有关联的角色 */
        if (CollUtil.isEmpty(newRoleIdList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysUserRoleInner> oldList = list(qw);
        // 新数据列表
        List<SysUserRoleInner> newList = newRoleIdList.stream().map(RoleId -> {
            SysUserRoleInner newObj = new SysUserRoleInner();
            newObj.setUserInnerId(userId);
            newObj.setRoleInnerId(RoleId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysUserRoleInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysUserRoleInner> addList = new LinkedList<>();
        // 修改列表
        List<SysUserRoleInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysUserRoleInner> oldMap = oldList.stream().collect(Collectors.toMap(SysUserRoleInner::getRoleInnerId, Function.identity()));
        Map<Long, SysUserRoleInner> newMap = newList.stream().collect(Collectors.toMap(SysUserRoleInner::getRoleInnerId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysUserRoleInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getRoleInnerId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysUserRoleInner newObj : newList) {
            if (!oldMap.containsKey(newObj.getRoleInnerId())) {
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
            SysUserRoleInner addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("用户角色-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("用户角色-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("用户角色-成功批量删除");
        }
        // 更新用户缓存
        sysUserInnerService.selectUserInnerPowerInfo(userId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleUserDTO dto){

        log.info("内部角色用户-开始添加：{}", dto);
        // 内部角色ID
        Long roleId = dto.getRoleId();
        // 新用户ID列表转Map，空间换时间，
        List<Long> newUserIdList = dto.getUserIdList();
        // 通用查询条件
        QueryWrapper<SysUserRoleInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserRoleInner::getRoleInnerId, roleId);
        /* 如果用户ID列表为空，删除所有关联的用户 */
        if (CollUtil.isEmpty(newUserIdList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysUserRoleInner> oldList = list(qw);
        // 新数据列表
        List<SysUserRoleInner> newList = newUserIdList.stream().map(UserId -> {
            SysUserRoleInner newObj = new SysUserRoleInner();
            newObj.setRoleInnerId(roleId);
            newObj.setUserInnerId(UserId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysUserRoleInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysUserRoleInner> addList = new LinkedList<>();
        // 修改列表
        List<SysUserRoleInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysUserRoleInner> oldMap = oldList.stream().collect(Collectors.toMap(SysUserRoleInner::getUserInnerId, Function.identity()));
        Map<Long, SysUserRoleInner> newMap = newList.stream().collect(Collectors.toMap(SysUserRoleInner::getUserInnerId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysUserRoleInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getUserInnerId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysUserRoleInner newObj : newList) {
            if (!oldMap.containsKey(newObj.getUserInnerId())) {
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
            SysUserRoleInner addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("角色用户-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("角色用户-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("角色用户-成功批量删除");
        }
        RedisHelp.del(AuthCacheConstants.ROLE_INNER_HASH);
        // 更新用户缓存
        sysUserInnerService.selectUserInnerPowerInfo(roleId);
    }

}
