package cn.kun.auth.user.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sevnce.auth.user.entity.dto.UserJobDTO;
import com.sevnce.auth.user.entity.po.SysUserJobOuter;
import com.sevnce.auth.user.mapper.SysUserJobOuterMapper;
import com.sevnce.auth.user.service.SysUserJobOuterService;
import com.sevnce.auth.user.service.SysUserOuterService;
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
 * 外部用户职位表 服务实现类
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:21
 */
@Service
@Slf4j
public class SysUserJobOuterServiceImpl extends ServiceImpl<SysUserJobOuterMapper, SysUserJobOuter> implements SysUserJobOuterService {
    @Autowired
    private SysUserOuterService sysUserOuterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserJobDTO dto){

        log.info("外部用户职位-开始添加：{}", dto);
        // 职位ID
        Long userId = dto.getUserId();
        // 新职位ID列表转Map，空间换时间，
        List<Long> newJobIdList = dto.getJobIdList();
        // 通用查询条件
        QueryWrapper<SysUserJobOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserJobOuter::getUserOuterId, userId);
        /* 如果职位ID列表为空，删除所有关联的职位 */
        if (CollUtil.isEmpty(newJobIdList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysUserJobOuter> oldList = list(qw);
        // 新数据列表
        List<SysUserJobOuter> newList = newJobIdList.stream().map(jobId -> {
            SysUserJobOuter newObj = new SysUserJobOuter();
            newObj.setUserOuterId(userId);
            newObj.setJobOuterId(jobId);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysUserJobOuter> removeList = new LinkedList<>();
        // 新增列表
        List<SysUserJobOuter> addList = new LinkedList<>();
        // 修改列表
        List<SysUserJobOuter> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<Long, SysUserJobOuter> oldMap = oldList.stream().collect(Collectors.toMap(SysUserJobOuter::getJobOuterId, Function.identity()));
        Map<Long, SysUserJobOuter> newMap = newList.stream().collect(Collectors.toMap(SysUserJobOuter::getJobOuterId, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysUserJobOuter oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getJobOuterId())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysUserJobOuter newObj : newList) {
            if (!oldMap.containsKey(newObj.getJobOuterId())) {
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
            SysUserJobOuter addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("用户职位-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("用户职位-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("用户职位-成功批量删除");
        }
        // 更新用户缓存
        sysUserOuterService.selectUserOuterPowerInfo(userId);
    }
}
