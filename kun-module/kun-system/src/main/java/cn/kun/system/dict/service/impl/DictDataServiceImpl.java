package cn.kun.system.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.entity.dto.DictDataAddDTO;
import cn.kun.system.dict.entity.dto.DictDataEditDTO;
import cn.kun.system.dict.entity.dto.DictDataPageDTO;
import cn.kun.system.dict.mapper.DictDataMapper;
import cn.kun.system.dict.service.DictDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.entity.system.dto.DictDataListDTO;
import cn.kun.base.api.entity.system.vo.DictDataListVO;
import cn.kun.base.core.global.constant.BaseConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.cache.constant.SystemCacheConstants;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.str.StrHelp;
import cn.kun.system.dict.entity.po.DictData;
import cn.kun.system.dict.entity.vo.DictDataDetailVO;
import cn.kun.system.dict.entity.vo.DictDataPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-03-23 10:32
 */
@Slf4j
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {
    
    @Override
    public Page<DictDataPageVO> page(DictDataPageDTO dto) {

        log.info("字典数据-分页-开始：{}", dto);
        // 封装分页参数
        Page<DictData> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 查询条件构建
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DictData::getId,
                        DictData::getTypeCode,
                        DictData::getValue,
                        DictData::getLabel,
                        DictData::getEnLabel,
                        DictData::getSort,
                        DictData::getEnableFlag,
                        DictData::getUpdateName,
                        DictData::getUpdateDate)
                .like(StrUtil.isNotBlank(dto.getTypeCode()), DictData::getTypeCode, dto.getTypeCode())
                .like(StrUtil.isNotBlank(dto.getValue()), DictData::getValue, dto.getValue())
                .like(StrUtil.isNotBlank(dto.getLabel()), DictData::getLabel, dto.getLabel())
                .like(StrUtil.isNotBlank(dto.getEnLabel()), DictData::getEnLabel, dto.getEnLabel())
                .eq(StrUtil.isNotBlank(dto.getEnableFlag()), DictData::getEnableFlag, dto.getEnableFlag())
                .orderByAsc(DictData::getTypeCode)
                .orderByAsc(DictData::getSort)
                .orderByDesc(DictData::getUpdateDate);
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<DictDataPageVO>) page.convert(dictData -> BeanHelp.copyProperties(dictData, DictDataPageVO.class));
    }

    @Override
    public DictDataDetailVO detail(Long id) {

        log.info("字典数据-详情-开始：{}", id);
        // 查询详情
        DictData dictData = getById(id);
        if (ObjUtil.isNull(dictData)) {
            log.warn("字典数据-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "字典数据-详情数据不存在");
        }
        // 复制到返回值
        return BeanHelp.copyProperties(dictData, DictDataDetailVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictData add(DictDataAddDTO dto) {

        log.info("字典数据-添加-开始：{}", dto);
        // 重复校验
        String label = getLabel(dto.getTypeCode(), dto.getValue());
        if (StrUtil.isNotBlank(label)) {
            log.warn("该字典值已存在");
            throw new BusinessException(ErrorCodeConstants.REPEAT, "该字典值已存在");
        }
        // 传入值复制到数据库对象
        DictData dictData = BeanHelp.copyProperties(dto, DictData.class);
        // 如果启用标识为空，则设默认值
        if (StrHelp.isBlank(dictData.getEnableFlag())) {
            dictData.setEnableFlag(BaseConstants.YES);
        }
        // 添加
        boolean result = save(dictData);
        // 添加成功后添加缓存
        if (result) {
            log.info("字典数据-添加-成功");
            RedisHelp.setHash(SystemCacheConstants.DICT_DATA_HASH, dictData.getTypeCode(), listNoCache(dictData.getTypeCode()));
            log.info("字典数据-添加缓存-成功");
        }
        return dictData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictData edit(DictDataEditDTO dto) {

        log.info("字典数据-修改-开始：{}", dto);
        // 获取数据库对象
        DictData dictData = getById(dto.getId());
        if (ObjUtil.isNull(dictData)) {
            log.warn("字典数据-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "字典数据-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanHelp.copyPropertiesIgnoreNull(dto, dictData);
        // 修改
        boolean result = updateById(dictData);
        // 修改成功后更新缓存
        if (result) {
            log.info("字典数据-修改-成功");
            // 更新缓存
            RedisHelp.setHash(SystemCacheConstants.DICT_DATA_HASH, dictData.getTypeCode(), listNoCache(dictData.getTypeCode()));
            log.info("字典数据-修改缓存-成功");
        }
        return dictData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("字典数据-删除-开始：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
        // 获取对象
        DictData dictData = getById(id);
        if (ObjUtil.isNull(dictData)) {
            log.warn("字典数据-删除数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "字典数据-删除数据不存在");
        }
        // 删除
        boolean result = removeById(id);
        // 删除后删除缓存
        if (result) {
            log.info("字典数据-删除-成功");
            RedisHelp.setHash(SystemCacheConstants.DICT_DATA_HASH, dictData.getTypeCode(), listNoCache(dictData.getTypeCode()));
            log.info("字典数据-删除缓存-成功");
        }
    }

    @Override
    public List<BaseSelectVO> list(String type) {

        // 先从缓存中查询
        Object obj = RedisHelp.getHash(SystemCacheConstants.DICT_DATA_HASH, type);
        if (ObjUtil.isNotNull(obj) && obj instanceof List list) {
            if (CollUtil.isNotEmpty(list)) {
                return list;
            }
        }
        // 缓存中没有再从数据库查询
        List<BaseSelectVO> voList = listNoCache(type);
        // 数据库查询的结果存入缓存
        RedisHelp.setHash(SystemCacheConstants.DICT_DATA_HASH, type, voList);
        return voList;
    }

    @Override
    public String getLabel(String type, String value) {
        
        // 返回值
        String result = StrUtil.EMPTY;
        // 判空
        if (StrUtil.isBlank(type)) {
            log.warn("字典类型不能为空");
            return result;
        }
        if (StrUtil.isBlank(value)) {
            log.warn("字典键值不能为空");
            return result;
        }
        // 获取列表
        List<BaseSelectVO> voList = list(type);
        if (CollUtil.isEmpty(voList)) {
            return result;
        }
        // 列表中取值
        for (BaseSelectVO vo : voList) {
            if (StrUtil.equals(vo.getValue(), value)) {
                return vo.getLabel();
            }
        }
        return result;
    }

    @Override
    public List<DictDataListVO> list(DictDataListDTO dto) {
        
        List<DictDataListVO> voList = new ArrayList<>();
        for (String type : dto.getTypeList()) {
            DictDataListVO vo = new DictDataListVO();
            vo.setDictType(type);
            vo.setDictDataList(list(type));
            voList.add(vo);
        }
        return voList;
    }

    private List<BaseSelectVO> listNoCache(String type) {

        // 判空
        if (StrUtil.isBlank(type)) {
            log.warn("字典类型不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "字典类型不能为空");
        }
        // 构建查询条件
        QueryWrapper<DictData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DictData::getValue,
                        DictData::getLabel,
                        DictData::getEnLabel,
                        DictData::getSort)
                .eq(DictData::getTypeCode, type)
                .eq(DictData::getEnableFlag, BaseConstants.YES);
        // 列表查询
        List<DictData> list = list(queryWrapper);
        return list.stream().map(this::cast).collect(Collectors.toList());
    }
    
    /**
     * 转换
     *
     * @param dictData 字典数据
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(DictData dictData) {

        BaseSelectVO vo = new BaseSelectVO();
        vo.setValue(dictData.getValue());
        vo.setLabel(dictData.getLabel());
        vo.setSort(dictData.getSort());
        return vo;
    }
    
}
