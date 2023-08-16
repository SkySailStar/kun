package cn.kun.system.fault.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.api.service.auth.RemoteProjectService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.fault.entity.dto.FaultInfoAddDTO;
import cn.kun.system.fault.entity.dto.FaultInfoPageDTO;
import cn.kun.system.fault.entity.po.FaultInfo;
import cn.kun.system.fault.entity.vo.FaultInfoDetailVO;
import cn.kun.system.fault.entity.vo.FaultInfoPageVO;
import cn.kun.system.fault.mapper.FaultInfoMapper;
import cn.kun.system.fault.service.FaultInfoService;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 故障信息 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:24
 */
@Slf4j
@Service
public class FaultInfoServiceImpl extends ServiceImpl<FaultInfoMapper, FaultInfo> implements FaultInfoService {

    @Resource
    private DictDataService dictDataService;
    
    @Resource
    private RemoteProjectService remoteProjectService;

    @Override
    public Page<FaultInfoPageVO> page(FaultInfoPageDTO dto) {

        log.info("故障信息-分页-开始：{}", dto);
        // 封装分页参数
        Page<FaultInfo> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建排序条件
        QueryWrapper<FaultInfo> queryWrapper = new QueryWrapper<>();
        if (ObjUtil.isNotNull(dto.getRecordTimeAsc())) {
            if (dto.getRecordTimeAsc()) {
                queryWrapper.lambda().orderByAsc(FaultInfo::getRecordTime);
            } else {
                queryWrapper.lambda().orderByDesc(FaultInfo::getRecordTime);
            }
        } else {
            queryWrapper.lambda().orderByDesc(FaultInfo::getUpdateDate);
        }
        // 分页列表查询
        dto.setOrderBy(queryWrapper.getTargetSql());
        Page<FaultInfoPageVO> voPage = baseMapper.selectPage(page, dto);
        return (Page<FaultInfoPageVO>) voPage.convert(faultInfo -> {
            FaultInfoPageVO vo = new FaultInfoPageVO();
            BeanHelp.copyProperties(faultInfo, vo);
            // 故障来源分类名称
            vo.setSourceTypeName(dictDataService.getLabel(SystemDictTypeConstants.FAULT_SOURCE_TYPE, faultInfo.getSourceType()));
            // 状态名称
            vo.setStatusName(dictDataService.getLabel(SystemDictTypeConstants.FAULT_STATUS, faultInfo.getStatus()));
            return vo;
        });
    }

    @Override
    public FaultInfoDetailVO detail(Long id) {

        log.info("故障信息-详情-开始：{}", id);
        // 查询详情
        FaultInfo faultInfo = getById(id);
        if (ObjUtil.isNull(faultInfo)) {
            log.warn("故障信息-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "故障信息-详情数据不存在");
        }
        // 复制到返回值
        FaultInfoDetailVO vo = new FaultInfoDetailVO();
        BeanHelp.copyProperties(faultInfo, vo);
        // 项目名称
        SysProjectInfoVO projectInfo = remoteProjectService.getProjectInfoByProjectNo(faultInfo.getProjectNo()).getData();
        if (ObjUtil.isNotNull(projectInfo)) {
            vo.setProjectName(projectInfo.getProjectName());
        }
        // 故障来源分类名称
        vo.setSourceTypeName(dictDataService.getLabel(SystemDictTypeConstants.FAULT_SOURCE_TYPE, faultInfo.getSourceType()));
        // 状态名称
        vo.setStatusName(dictDataService.getLabel(SystemDictTypeConstants.FAULT_STATUS, faultInfo.getStatus()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FaultInfo add(FaultInfoAddDTO dto) {

        log.info("故障信息-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        FaultInfo faultInfo = new FaultInfo();
        BeanHelp.copyProperties(dto, faultInfo);
        // 添加
        save(faultInfo);
        return faultInfo;
    }
    
}
