package cn.kun.system.dict.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.entity.dto.DictTypeAddDTO;
import cn.kun.system.dict.entity.dto.DictTypeEditDTO;
import cn.kun.system.dict.entity.dto.DictTypePageDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanUtils;
import cn.kun.system.dict.entity.po.DictType;
import cn.kun.system.dict.entity.vo.DictTypeDetailVO;
import cn.kun.system.dict.entity.vo.DictTypePageVO;
import cn.kun.system.dict.mapper.DictTypeMapper;
import cn.kun.system.dict.service.DictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author 天航星
 * @since 2023-03-23 10:24
 */
@Slf4j
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Override
    public Page<DictTypePageVO> page(DictTypePageDTO dto) {

        log.info("字典类型-分页-开始：{}", dto);
        // 封装分页参数
        Page<DictType> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 查询条件构建
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DictType::getId,
                        DictType::getCode,
                        DictType::getName,
                        DictType::getEnName,
                        DictType::getUpdateTime)
                .like(StrUtil.isNotBlank(dto.getCode()), DictType::getCode, dto.getCode())
                .like(StrUtil.isNotBlank(dto.getName()), DictType::getName, dto.getName())
                .like(StrUtil.isNotBlank(dto.getEnName()), DictType::getEnName, dto.getEnName())
                .orderByDesc(DictType::getUpdateTime);
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<DictTypePageVO>) page.convert(dictType -> BeanUtils.copyProperties(dictType, DictTypePageVO.class));
    }

    @Override
    public DictTypeDetailVO detail(Long id) {

        log.info("字典类型-详情-开始：{}", id);
        // 查询详情
        DictType dictType = getById(id);
        if (ObjUtil.isNull(dictType)) {
            log.warn("字典类型-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "字典类型-详情数据不存在");
        }
        // 复制到返回值
        return BeanUtils.copyProperties(dictType, DictTypeDetailVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictType add(DictTypeAddDTO dto) {

        log.info("字典类型-添加-开始：{}", dto);
        // 重复校验
        QueryWrapper<DictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictType::getCode, dto.getCode());
        List<DictType> list = list(queryWrapper);
        if (list.size() > 0) {
            log.warn("编码重复");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "编码重复");
        }
        // 传入值复制到数据库对象
        DictType dictType = BeanUtils.copyProperties(dto, DictType.class);
        // 添加
        save(dictType);
        return dictType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictType edit(DictTypeEditDTO dto) {

        log.info("字典类型-修改-开始：{}", dto);
        // 获取数据库对象
        DictType dictType = getById(dto.getId());
        if (ObjUtil.isNull(dictType)) {
            log.warn("字典类型-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "字典类型-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanUtils.copyPropertiesIgnoreNull(dto, dictType);
        // 修改
        updateById(dictType);
        return dictType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("字典类型-删除-开始：{}", id);
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

        log.info("字典类型-批量删除-开始：{}", dto);
        // 批量删除
        removeByIds(dto.getIdList());
    }
    
}
