package cn.kun.system.monitor.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.service.DictDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import cn.kun.system.monitor.entity.dto.MonitorConfigAddDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigEditDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigPageDTO;
import cn.kun.system.monitor.entity.po.MonitorConfig;
import cn.kun.system.monitor.entity.vo.MonitorConfigDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorConfigPageVO;
import cn.kun.system.monitor.mapper.MonitorConfigMapper;
import cn.kun.system.monitor.service.MonitorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 监控项配置 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-10 10:08
 */
@Slf4j
@Service
public class MonitorConfigServiceImpl extends ServiceImpl<MonitorConfigMapper, MonitorConfig> implements MonitorConfigService {

    @Autowired
    private DictDataService dictDataService;
    
    @Override
    public Page<MonitorConfigPageVO> page(MonitorConfigPageDTO dto) {

        log.info("监控项配置-分页-开始：{}", dto);
        // 封装分页参数
        Page<MonitorConfig> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建查询条件
        QueryWrapper<MonitorConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(MonitorConfig::getId,
                        MonitorConfig::getCategory,
                        MonitorConfig::getType,
                        MonitorConfig::getCode,
                        MonitorConfig::getName,
                        MonitorConfig::getShowType,
                        MonitorConfig::getEnableFlag,
                        MonitorConfig::getUpdateName,
                        MonitorConfig::getUpdateDate)
                .eq(StrUtil.isNotBlank(dto.getCategory()), MonitorConfig::getCategory, dto.getCategory())
                .eq(StrUtil.isNotBlank(dto.getType()), MonitorConfig::getType, dto.getType())
                .like(StrUtil.isNotBlank(dto.getCode()), MonitorConfig::getCode, dto.getCode())
                .like(StrUtil.isNotBlank(dto.getName()), MonitorConfig::getName, dto.getName())
                .eq(StrUtil.isNotBlank(dto.getShowType()), MonitorConfig::getShowType, dto.getShowType())
                .eq(StrUtil.isNotBlank(dto.getEnableFlag()), MonitorConfig::getEnableFlag, dto.getEnableFlag());
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<MonitorConfigPageVO>) page.convert(monitorConfig -> {
            MonitorConfigPageVO vo = new MonitorConfigPageVO();
            BeanHelp.copyProperties(monitorConfig, vo);
            // 类别名称
            vo.setCategoryName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_CATEGORY, monitorConfig.getCategory()));
            // 类型名称
            vo.setTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_TYPE, monitorConfig.getType()));
            // 展示类型名称
            vo.setShowTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_SHOW_TYPE, monitorConfig.getShowType()));
            // 启用标识名称
            vo.setEnableFlagName(DictHelp.castFlag(monitorConfig.getEnableFlag()));
            return vo;
        });
    }

    @Override
    public MonitorConfigDetailVO detail(Long id) {

        log.info("监控项配置-详情-开始：{}", id);
        // 查询详情
        MonitorConfig monitorConfig = getById(id);
        if (ObjUtil.isNull(monitorConfig)) {
            log.warn("监控项配置-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "监控项配置-详情数据不存在");
        }
        // 复制到返回值
        MonitorConfigDetailVO vo = new MonitorConfigDetailVO();
        BeanHelp.copyProperties(monitorConfig, vo);
        // 类别名称
        vo.setCategoryName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_CATEGORY, monitorConfig.getCategory()));
        // 类型名称
        vo.setTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_TYPE, monitorConfig.getType()));
        // 展示类型名称
        vo.setShowTypeName(dictDataService.getLabel(SystemDictTypeConstants.MONITOR_SHOW_TYPE, monitorConfig.getShowType()));
        // 启用标识名称
        vo.setEnableFlagName(DictHelp.castFlag(monitorConfig.getEnableFlag()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MonitorConfig add(MonitorConfigAddDTO dto) {

        log.info("监控项配置-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        MonitorConfig monitorConfig = new MonitorConfig();
        BeanHelp.copyProperties(dto, monitorConfig);
        // 添加
        save(monitorConfig);
        return monitorConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MonitorConfig edit(MonitorConfigEditDTO dto) {

        log.info("监控项配置-修改-开始：{}", dto);
        // 获取数据库对象
        MonitorConfig monitorConfig = getById(dto.getId());
        if (ObjUtil.isNull(monitorConfig)) {
            log.warn("监控项配置-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "监控项配置-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanHelp.copyPropertiesIgnoreNull(dto, monitorConfig);
        // 修改
        updateById(monitorConfig);
        return monitorConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("监控项配置-删除-开始：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
        // 删除
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(BaseIdListDTO dto) {

        log.info("监控项配置-批量删除-开始：{}", dto);
        // 批量删除
        removeByIds(dto.getIdList());
    }
    
}
