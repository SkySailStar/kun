package cn.kun.auth.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.kun.auth.project.entity.po.SysProject;
import cn.kun.auth.project.service.SysProjectService;
import cn.kun.auth.user.entity.dto.UserProjectDTO;
import cn.kun.auth.user.entity.po.SysUserProjectInner;
import cn.kun.auth.user.mapper.SysUserProjectInnerMapper;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.user.service.SysUserInnerService;
import cn.kun.auth.user.service.SysUserProjectInnerService;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 内部用户项目表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysUserProjectInnerServiceImpl extends ServiceImpl<SysUserProjectInnerMapper, SysUserProjectInner> implements SysUserProjectInnerService {
    @Resource
    private SysUserInnerService sysUserInnerService;

    @Resource
    private SysProjectService sysProjectService;

    /**
     * 新增数据
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserProjectDTO dto){

        log.info("内部用户项目-开始添加：{}", dto);
        // 内部用户ID
        Long userId = dto.getUserId();
        // 新项目ID列表转Map，空间换时间，
        List<String> newProjectNoList = dto.getProjectNoList();
        // 通用查询条件
        QueryWrapper<SysUserProjectInner> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserProjectInner::getUserInnerId, userId);
        /* 如果项目ID列表为空，删除所有关联的项目 */
        if (CollUtil.isEmpty(newProjectNoList)) {
            remove(qw);
            return;
        }
        // 旧数据列表
        List<SysUserProjectInner> oldList = list(qw);
        // 新数据列表
        List<SysUserProjectInner> newList = newProjectNoList.stream().map(ProjectNo -> {
            SysUserProjectInner newObj = new SysUserProjectInner();
            newObj.setUserInnerId(userId);
            newObj.setProjectNo(ProjectNo);
            return newObj;
        }).toList();
        // 如果旧数据列表为空，说明所有数据都是新数据，直接新增
        if (CollUtil.isEmpty(oldList)) {
            saveBatch(newList);
            return;
        }
        // 删除列表
        List<SysUserProjectInner> removeList = new LinkedList<>();
        // 新增列表
        List<SysUserProjectInner> addList = new LinkedList<>();
        // 修改列表
        List<SysUserProjectInner> editList = new LinkedList<>();
        // 空间换时间，List转Map降低时间复杂度
        Map<String, SysUserProjectInner> oldMap = oldList.stream().collect(Collectors.toMap(SysUserProjectInner::getProjectNo, Function.identity()));
        Map<String, SysUserProjectInner> newMap = newList.stream().collect(Collectors.toMap(SysUserProjectInner::getProjectNo, Function.identity()));
        // 旧数据中不包含新数据的部分，说明需要删除
        for (SysUserProjectInner oldObj : oldList) {
            if (!newMap.containsKey(oldObj.getProjectNo())) {
                removeList.add(oldObj);
            }
        }
        // 新数据中不包含旧数据的部分，说明需要新增
        for (SysUserProjectInner newObj : newList) {
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
            SysUserProjectInner addObj = addList.get(0);
            addObj.setId(removeList.get(0).getId());
            editList.add(addObj);
            addList.remove(0);
            removeList.remove(0);
        }
        // 批量新增
        if (CollUtil.isNotEmpty(addList)) {
            saveBatch(addList);
            log.info("用户项目-成功批量添加");
        }
        // 批量修改
        if (CollUtil.isNotEmpty(editList)) {
            updateBatchById(editList);
            log.info("用户项目-成功批量修改");
        }
        // 批量删除
        if (CollUtil.isNotEmpty(removeList)) {
            removeBatchByIds(removeList);
            log.info("用户项目-成功批量删除");
        }
        // 更新用户缓存
        sysUserInnerService.selectUserInnerPowerInfo(userId);
    }
    


    /**
     * 通过用户id获取项目id
     * @param dto
     * @return
     */
    public String getProjectNosByUserId(UserProjectDTO dto){
        List<Long> projectIdscollect  = new ArrayList<>();
        String projectIdsStr = null;
        Long userId = dto.getUserId();
        //先去redis缓存中取
        projectIdsStr = (String) RedisHelp.getHash(AuthCacheConstants.PROJECT_HASH, AuthCacheConstants.USER_ID +userId);
        if(ObjUtil.isNull(projectIdsStr)){
            // 判断是否是系统管理员
            if (ObjUtil.equals(AuthConstants.SYS_ADMIN,userId)){
                List<SysProject> sysProjects = sysProjectService.list();
                projectIdscollect = sysProjects.stream().map(item -> item.getId()).toList();
            }
            else{
                QueryWrapper<SysUserProjectInner> wrapper = new QueryWrapper<>();
                wrapper.lambda().eq(SysUserProjectInner::getUserInnerId,userId);
                List<SysUserProjectInner> list = list(wrapper);
//                List<Long> userProjectIds = list.stream().map(item -> item.getProjectId()).toList();
                // 查询用户所有角色的项目
//                List<String> roleProjectIds = sysProjectService.selectProjectNoByUserInnerId(userId);
                // 将角色id查询出来的项目id和用户id查询出来的项目id进行合并
//                userProjectIds.addAll(roleProjectIds);
//                projectIdscollect = userProjectIds.stream().distinct().toList(); // 去重
            }
            projectIdsStr = StringUtils.join(projectIdscollect, ",");  // 将集合转换成字符串
            // 将项目信息存入redis缓存
            RedisHelp.setHash(AuthCacheConstants.PROJECT_HASH, AuthCacheConstants.USER_ID +userId,projectIdsStr);
        }
    return projectIdsStr;
    }


    public  void flushSysProjectOnChange(Long userId){
        /** 项目如果发生改变清除用户项目缓存*/
        RedisHelp.delHash(AuthCacheConstants.PROJECT_HASH, AuthCacheConstants.USER_ID +userId);
    }


}
