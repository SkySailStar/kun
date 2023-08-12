package cn.kun.system.log.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.log.entity.dto.RunLogAddDTO;
import cn.kun.system.log.entity.dto.RunLogPageDTO;
import cn.kun.system.log.entity.vo.RunLogDetailVO;
import cn.kun.system.log.entity.vo.RunLogPageVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.system.log.entity.po.RunLog;
import cn.kun.system.log.mapper.RunLogMapper;
import cn.kun.system.log.service.RunLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 运行日志 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Slf4j
@Service
public class RunLogServiceImpl extends ServiceImpl<RunLogMapper, RunLog> implements RunLogService {

    @Autowired
    private DictDataService dictDataService;
    
    @Override
    public Page<RunLogPageVO> page(RunLogPageDTO dto) {

        log.info("运行日志-分页-开始：{}", dto);
        // 封装分页参数
        Page<RunLog> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建查询条件
        QueryWrapper<RunLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(RunLog::getId,
                        RunLog::getSerialNumber,
                        RunLog::getMethodName,
                        RunLog::getLogLevel,
                        RunLog::getRunTime,
                        RunLog::getDataSource,
                        RunLog::getExceptionCode,
                        RunLog::getExceptionMsg)
                .like(StrUtil.isNotBlank(dto.getSerialNumber()), RunLog::getSerialNumber, dto.getSerialNumber())
                .like(StrUtil.isNotBlank(dto.getMethodName()), RunLog::getMethodName, dto.getMethodName())
                .eq(StrUtil.isNotBlank(dto.getLogLevel()), RunLog::getLogLevel, dto.getLogLevel())
                .gt(ObjUtil.isNotNull(dto.getRunStartTime()), RunLog::getRunTime, dto.getRunStartTime())
                .lt(ObjUtil.isNotNull(dto.getRunEndTime()), RunLog::getRunTime, dto.getRunEndTime())
                .eq(StrUtil.isNotBlank(dto.getDataSource()), RunLog::getDataSource, dto.getDataSource());
        // 构建排序条件
        if (ObjUtil.isNotNull(dto.getRunTimeAsc())) {
            if (dto.getRunTimeAsc()) {
                queryWrapper.lambda().orderByAsc(RunLog::getRunTime);
            } else {
                queryWrapper.lambda().orderByDesc(RunLog::getRunTime);
            }
        } else {
            queryWrapper.lambda().orderByDesc(RunLog::getUpdateDate);
        }
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<RunLogPageVO>) page.convert(runLog -> {
            RunLogPageVO vo = new RunLogPageVO();
            BeanHelp.copyProperties(runLog, vo);
            // 日志级别名称
            vo.setLogLevelName(dictDataService.getLabel(SystemDictTypeConstants.LOG_LEVEL, runLog.getLogLevel()));
            // 数据来源名称
            vo.setDataSourceName(dictDataService.getLabel(SystemDictTypeConstants.DATA_SOURCE, runLog.getDataSource()));
            return vo;
        });
    }

    @Override
    public RunLogDetailVO detail(Long id) {

        log.info("运行日志-详情-开始：{}", id);
        // 查询详情
        RunLog runLog = getById(id);
        if (ObjUtil.isNull(runLog)) {
            log.warn("运行日志-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "运行日志-详情数据不存在");
        }
        // 复制到返回值
        RunLogDetailVO vo = new RunLogDetailVO();
        BeanHelp.copyProperties(runLog, vo);
        // 日志级别名称
        vo.setLogLevelName(dictDataService.getLabel(SystemDictTypeConstants.LOG_LEVEL, runLog.getLogLevel()));
        // 数据来源名称
        vo.setDataSourceName(dictDataService.getLabel(SystemDictTypeConstants.DATA_SOURCE, runLog.getDataSource()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RunLog add(RunLogAddDTO dto) {

        log.info("运行日志-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        RunLog runLog = new RunLog();
        BeanHelp.copyProperties(dto, runLog);
        // 添加
        save(runLog);
        return runLog;
    }
    
}
