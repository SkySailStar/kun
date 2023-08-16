package cn.kun.auth.system.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kun.auth.system.role.mapper.SysRoleProjectInnerMapper;
import cn.kun.auth.system.role.service.SysRoleInnerService;
import cn.kun.auth.system.role.service.SysRoleProjectInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.role.entity.dto.RoleProjectDTO;
import cn.kun.auth.system.role.entity.po.SysRoleProjectInner;
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
 * 内部角色项目表 服务实现类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:23
 */
@Service
@Slf4j
public class SysRoleProjectInnerServiceImpl extends ServiceImpl<SysRoleProjectInnerMapper, SysRoleProjectInner> implements SysRoleProjectInnerService {
    @Autowired
    private SysRoleInnerService sysRoleInnerService;

    /**
     * 新增数据
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(RoleProjectDTO dto){

        log.info("内部角色项目-开始添加：{}", dto);
        // 内部角色ID
        Long roleId = dto.getRoleId();
        // 新项目ID列表转Map，空间换时间，
        List<String> newProjectNoList = dto.getProjectNoList();
        // 通用查询条件
        QueryWrapper<SysRoleProjectInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysRoleProjectInner::getRoleInnerId, roleId);
        /* 如果项目ID列表为空，删除所有关联的项目 */
        if (CollUtil.isEmpty(newProjectNoList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysRoleProjectInner> oldList = list(qw);
        // 新数据列表
        List<SysRoleProjectInner> newList = newProjectNoList.stream().map(ProjectNo -> {
            SysRoleProjectInner newObj = new SysRoleProjectInner();
            newObj.setRoleInnerId(roleId);
            newObj.setProjectNo(ProjectNo);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysRoleProjectInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysRoleProjectInner> addList = new LinkedList<>();
        // 修改列表
        List<SysRoleProjectInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<String, SysRoleProjectInner> oldMap = oldList.stream().collect(Collectors.toMap(SysRoleProjectInner::getProjectNo, Function.identity()));
        Map<String, SysRoleProjectInner> newMap = newList.stream().collect(Collectors.toMap(SysRoleProjectInner::getProjectNo, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysRoleProjectInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getProjectNo())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysRoleProjectInner newObj : newList) {
            if (!oldMap.containsKey(newObj.getProjectNo())) {
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
            SysRoleProjectInner addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("角色项目-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("角色项目-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("角色项目-成功批量删除");
        }
        RedisHelp.delHash(AuthCacheConstants.USER_INNER_HASH);
        // 更新角色缓存
        sysRoleInnerService.selectRoleInnerPowerInfo(roleId);
    }
    
}
