package cn.kun.auth.system.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kun.auth.system.user.entity.dto.UserMenuDTO;
import cn.kun.auth.system.user.entity.po.SysUserMenuInner;
import cn.kun.auth.system.user.mapper.SysUserMenuInnerMapper;
import cn.kun.auth.system.user.service.SysUserInnerService;
import cn.kun.auth.system.user.service.SysUserMenuInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.menu.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 内部用户菜单表 服务实现类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:22
 */
@Service
@Slf4j
public class SysUserMenuInnerServiceImpl extends ServiceImpl<SysUserMenuInnerMapper, SysUserMenuInner> implements SysUserMenuInnerService {
    @Resource
    private SysUserInnerService sysUserInnerService;
    @Resource
    private SysMenuService sysMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserMenuDTO dto){

        log.info("内部用户菜单-开始添加：{}", dto);
        // 内部职位ID
        Long userId = dto.getUserId();
        // 新菜单ID列表转Map，空间换时间，
        List<Long> newMenuIdList = dto.getMenuIdList();
        // 通用查询条件
        QueryWrapper<SysUserMenuInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserMenuInner::getUserInnerId, userId);
        /* 如果菜单ID列表为空，删除所有关联的菜单 */
        if (CollUtil.isEmpty(newMenuIdList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysUserMenuInner> oldList = list(qw);
        // 新数据列表
        List<SysUserMenuInner> newList = newMenuIdList.stream().map(menuId -> {
            SysUserMenuInner newObj = new SysUserMenuInner();
            newObj.setUserInnerId(userId);
            newObj.setMenuId(menuId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysUserMenuInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysUserMenuInner> addList = new LinkedList<>();
        // 修改列表
        List<SysUserMenuInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysUserMenuInner> oldMap = oldList.stream().collect(Collectors.toMap(SysUserMenuInner::getMenuId, Function.identity()));
        Map<Long, SysUserMenuInner> newMap = newList.stream().collect(Collectors.toMap(SysUserMenuInner::getMenuId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysUserMenuInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getMenuId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysUserMenuInner newObj : newList) {
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
            SysUserMenuInner addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("用户菜单-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("用户菜单-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("用户菜单-成功批量删除");
        }
        // 更新用户缓存
        sysUserInnerService.selectUserInnerPowerInfo(userId);

    }


}
