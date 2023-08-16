package cn.kun.system.monitor.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.monitor.service.MonitorDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.api.service.auth.RemoteProjectService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.system.monitor.entity.dto.MonitorDataAddDTO;
import cn.kun.system.monitor.entity.dto.MonitorDataPageDTO;
import cn.kun.system.monitor.entity.po.MonitorData;
import cn.kun.system.monitor.entity.vo.MonitorDataDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorDataPageVO;
import cn.kun.system.monitor.mapper.MonitorDataMapper;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 监控数据 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Slf4j
@Service
public class MonitorDataServiceImpl extends ServiceImpl<MonitorDataMapper, MonitorData> implements MonitorDataService {

    @Resource
    private DictDataService dictDataService;
    
    @Resource
    private RemoteProjectService remoteProjectService;

    @Override
    public Page<MonitorDataPageVO> page(MonitorDataPageDTO dto) {

        log.info("监控数据-分页-开始：{}", dto);
        // 封装分页参数
        Page<MonitorData> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建排序条件
        QueryWrapper<MonitorData> queryWrapper = new QueryWrapper<>();
        if (ObjUtil.isNotNull(dto.getRecordTimeAsc())) {
            if (dto.getRecordTimeAsc()) {
                queryWrapper.lambda().orderByAsc(MonitorData::getRecordTime);
            } else {
                queryWrapper.lambda().orderByDesc(MonitorData::getRecordTime);
            }
        } else {
            queryWrapper.lambda().orderByDesc(MonitorData::getUpdateDate);
        }
        dto.setOrderBy(queryWrapper.getTargetSql());
        // 分页列表查询
        Page<MonitorDataPageVO> voPage = baseMapper.selectPage(page, dto);
        return (Page<MonitorDataPageVO>) voPage.convert(monitorData -> {
            MonitorDataPageVO vo = new MonitorDataPageVO();
            BeanHelp.copyProperties(monitorData, vo);
            // 监控项来源分类名称
            vo.setSourceTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_SOURCE_TYPE, monitorData.getSourceType()));
            return vo;
        });
    }

    @Override
    public MonitorDataDetailVO detail(Long id) {

        log.info("监控数据-详情-开始：{}", id);
        // 查询详情
        MonitorData monitorData = getById(id);
        if (ObjUtil.isNull(monitorData)) {
            log.warn("监控数据-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "监控数据-详情数据不存在");
        }
        // 复制到返回值
        MonitorDataDetailVO vo = new MonitorDataDetailVO();
        BeanHelp.copyProperties(monitorData, vo);
        // 项目名称
        SysProjectInfoVO projectInfo = remoteProjectService.getProjectInfoByProjectNo(monitorData.getProjectNo()).getData();
        if (ObjUtil.isNotNull(projectInfo)) {
            vo.setProjectName(projectInfo.getProjectName());
        }
        // 监控项来源分类名称
        vo.setSourceTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_SOURCE_TYPE, monitorData.getSourceType()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MonitorData add(MonitorDataAddDTO dto) {

        log.info("监控数据-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        MonitorData monitorData = new MonitorData();
        BeanHelp.copyProperties(dto, monitorData);
        // 添加
        save(monitorData);
        return monitorData;
    }
    
}
