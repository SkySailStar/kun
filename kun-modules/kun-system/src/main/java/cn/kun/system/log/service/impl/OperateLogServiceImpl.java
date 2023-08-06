package cn.kun.system.log.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.log.entity.dto.OperateLogAddDTO;
import cn.kun.system.log.entity.dto.OperateLogPageDTO;
import cn.kun.system.log.entity.po.OperateLog;
import cn.kun.system.log.entity.vo.OperateLogDetailVO;
import cn.kun.system.log.entity.vo.OperateLogPageVO;
import cn.kun.system.log.mapper.OperateLogMapper;
import cn.kun.system.log.service.OperateLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Slf4j
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    @Autowired
    private DictDataService dictDataService;
    
    @Override
    public Page<OperateLogPageVO> page(OperateLogPageDTO dto) {

        log.info("操作日志-分页-开始：{}", dto);
        // 封装分页参数
        Page<OperateLog> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建排序条件
        if (ObjUtil.isNotNull(dto.getOperateTimeAsc())) {
            QueryWrapper<OperateLog> queryWrapper = new QueryWrapper<>();
            // 正序
            if (dto.getOperateTimeAsc()) {
                queryWrapper.lambda().orderByAsc(OperateLog::getOperateTime);
            }
            // 倒序
            else {
                queryWrapper.lambda().orderByDesc(OperateLog::getOperateTime);
            }
            dto.setOrderBy(queryWrapper.getTargetSql());
        }
        // 分页列表查询
        Page<OperateLogPageVO> voPage = baseMapper.selectPage(page, dto);
        return (Page<OperateLogPageVO>) voPage.convert(vo -> {
            // 操作类型名称
            vo.setOperateTypeName(dictDataService.getLabel(SystemDictTypeConstants.OPERATE_TYPE, vo.getOperateType()));
            // 操作标识名称
            vo.setOperateFlagName(DictHelp.castSuccessFlag(vo.getOperateFlag()));
            // 操作对象类型名称
            vo.setOperateTargetTypeName(dictDataService.getLabel(SystemDictTypeConstants.OPERATE_TARGET_TYPE, vo.getOperateTargetType()));
            return vo;
        });
    }

    @Override
    public OperateLogDetailVO detail(Long id) {

        log.info("操作日志-详情-开始：{}", id);
        // 查询详情
        OperateLog operateLog = getById(id);
        if (ObjUtil.isNull(operateLog)) {
            log.warn("操作日志-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "操作日志-详情数据不存在");
        }
        // 复制到返回值
        OperateLogDetailVO vo = new OperateLogDetailVO();
        BeanHelp.copyProperties(operateLog, vo);
        // 操作类型名称
        vo.setOperateTypeName(dictDataService.getLabel(SystemDictTypeConstants.OPERATE_TYPE, operateLog.getOperateType()));
        // 操作标识名称
        vo.setOperateFlagName(DictHelp.castSuccessFlag(operateLog.getOperateFlag()));
        // 操作对象类型名称
        vo.setOperateTargetTypeName(dictDataService.getLabel(SystemDictTypeConstants.OPERATE_TARGET_TYPE, operateLog.getOperateTargetType()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperateLog add(OperateLogAddDTO dto) {

        log.info("操作日志-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        OperateLog operateLog = new OperateLog();
        BeanHelp.copyProperties(dto, operateLog);
        // 添加
        save(operateLog);
        return operateLog;
    }
    
}
