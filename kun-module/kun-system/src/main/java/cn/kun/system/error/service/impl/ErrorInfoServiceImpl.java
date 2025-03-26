package cn.kun.system.error.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.error.entity.po.ErrorInfo;
import cn.kun.system.error.entity.vo.ErrorInfoPageVO;
import cn.kun.system.error.mapper.ErrorInfoMapper;
import cn.kun.system.error.service.ErrorInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.DictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanUtils;
import cn.kun.base.core.mq.entity.dto.ErrorInfoMqDTO;
import cn.kun.system.error.entity.dto.ErrorInfoPageDTO;
import cn.kun.system.error.entity.vo.ErrorInfoDetailVO;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 错误编码 服务实现类
 * </p>
 *
 * @author 天航星
 * @since 2023-04-11 17:22
 */
@Slf4j
@Service
public class ErrorInfoServiceImpl extends ServiceImpl<ErrorInfoMapper, ErrorInfo> implements ErrorInfoService {

    @Resource
    private DictService baseDictService;

    @Override
    public Page<ErrorInfoPageVO> page(ErrorInfoPageDTO dto) {

        log.info("错误信息-分页-开始：{}", dto);
        // 封装分页参数
        Page<ErrorInfo> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建查询条件
        QueryWrapper<ErrorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(ErrorInfo::getId,
                        ErrorInfo::getCode,
                        ErrorInfo::getName,
                        ErrorInfo::getType)
                .like(StrUtil.isNotBlank(dto.getCode()), ErrorInfo::getCode, dto.getCode())
                .like(StrUtil.isNotBlank(dto.getName()), ErrorInfo::getName, dto.getName())
                .eq(StrUtil.isNotBlank(dto.getType()), ErrorInfo::getType, dto.getType())
                .orderByDesc(ErrorInfo::getUpdateTime);
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<ErrorInfoPageVO>) page.convert(errorInfo -> {
            ErrorInfoPageVO vo = new ErrorInfoPageVO();
            BeanUtils.copyProperties(errorInfo, vo);
            // 类型名称
            vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.ERROR_TYPE, errorInfo.getType()));
            return vo;
        });
    }

    @Override
    public ErrorInfoDetailVO detail(Long id) {

        log.info("错误信息-详情-开始：{}", id);
        // 查询详情
        ErrorInfo errorInfo = getById(id);
        if (ObjUtil.isNull(errorInfo)) {
            log.warn("错误信息-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "错误信息-详情数据不存在");
        }
        // 复制到返回值
        ErrorInfoDetailVO vo = new ErrorInfoDetailVO();
        BeanUtils.copyProperties(errorInfo, vo);
        // 类型名称
        vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.LOGIN_WAYS, errorInfo.getType()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ErrorInfoMqDTO dto) {

        log.info("错误信息-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        ErrorInfo errorInfo = new ErrorInfo();
        BeanUtils.copyProperties(dto, errorInfo);
        // 添加
        save(errorInfo);
    }
    
}
