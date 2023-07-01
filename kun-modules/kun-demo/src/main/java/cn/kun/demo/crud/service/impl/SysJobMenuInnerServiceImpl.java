package cn.kun.demo.crud.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kun.demo.crud.mapper.SysJobMenuInnerMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.demo.crud.entity.dto.JobMenuInnerSaveDTO;
import cn.kun.demo.crud.entity.po.SysJobMenuInner;
import cn.kun.demo.crud.service.SysJobMenuInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 内部职位菜单表 服务实现类
 * </p>
 *
 * @author 廖航
 * @since 2023-02-27 17:54
 */
@Slf4j
@Service
public class SysJobMenuInnerServiceImpl extends ServiceImpl<SysJobMenuInnerMapper, SysJobMenuInner> implements SysJobMenuInnerService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(JobMenuInnerSaveDTO dto) {

        log.info("内部职位菜单-保存：{}", dto);
        // 返回值
        boolean result = false;
        // 内部职位ID
        Long jobInnerId = dto.getJobInnerId();
        // 新菜单ID列表
        List<Long> newMenuIdList = dto.getMenuIdList();
        // 通用查询条件
        QueryWrapper<SysJobMenuInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysJobMenuInner::getJobInnerId, jobInnerId);
        // 如果新菜单ID列表为空，说明该职位不需要关联菜单，删除所有关联的菜单
        if (CollUtil.isEmpty(newMenuIdList)) {
            return remove(qw);
        }
        // 旧数据列表
        List<SysJobMenuInner> oldList = list(qw);
        // 新数据列表
        List<SysJobMenuInner> newList = newMenuIdList.stream().map(menuId -> {
            SysJobMenuInner newObj = new SysJobMenuInner();
            newObj.setJobInnerId(jobInnerId);
            newObj.setMenuId(menuId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            return saveBatch(newList);
        }
        // 删除列表
        List<SysJobMenuInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysJobMenuInner> addList = new LinkedList<>();
        // 修改列表
        List<SysJobMenuInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysJobMenuInner> oldMap = oldList.stream().collect(Collectors.toMap(SysJobMenuInner::getMenuId, Function.identity()));
        Map<Long, SysJobMenuInner> newMap = newList.stream().collect(Collectors.toMap(SysJobMenuInner::getMenuId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysJobMenuInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getMenuId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysJobMenuInner newObj : newList) {
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
            SysJobMenuInner editObj = addList.get(0);
            editObj.setId(removeList.get(0).getId());
            editList.add(editObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            result = saveBatch(addList);
            log.info("职位菜单-批量添加：成功");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            result = updateBatchById(editList);
            log.info("职位菜单-批量修改：成功");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            result = removeBatchByIds(removeList);
            log.info("职位菜单-批量删除：成功");
        }
        return result;
    }
}