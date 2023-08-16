package cn.kun.system.external.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.external.entity.dto.ExternalUrlAddDTO;
import cn.kun.system.external.entity.dto.ExternalUrlEditDTO;
import cn.kun.system.external.entity.dto.ExternalUrlPageDTO;
import cn.kun.system.external.entity.vo.ExternalUrlDetailVO;
import cn.kun.system.external.mapper.ExternalUrlMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import cn.kun.system.external.entity.po.ExternalUrl;
import cn.kun.system.external.entity.vo.ExternalUrlPageVO;
import cn.kun.system.external.service.ExternalUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 外部链接 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Slf4j
@Service
public class ExternalUrlServiceImpl extends ServiceImpl<ExternalUrlMapper, ExternalUrl> implements ExternalUrlService {

    @Autowired
    private DictDataService dictDataService;
    
    @Override
    public Page<ExternalUrlPageVO> page(ExternalUrlPageDTO dto) {

        log.info("外部链接-分页-开始：{}", dto);
        // 封装分页参数
        Page<ExternalUrl> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建查询条件
        QueryWrapper<ExternalUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(ExternalUrl::getId,
                        ExternalUrl::getOwnerType,
                        ExternalUrl::getName,
                        ExternalUrl::getUrl,
                        ExternalUrl::getEnableFlag)
                .eq(StrUtil.isNotBlank(dto.getOwnerType()), ExternalUrl::getOwnerType, dto.getOwnerType())
                .like(StrUtil.isNotBlank(dto.getName()), ExternalUrl::getName, dto.getName())
                .eq(StrUtil.isNotBlank(dto.getEnableFlag()), ExternalUrl::getEnableFlag, dto.getEnableFlag());
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<ExternalUrlPageVO>) page.convert(externalUrl -> {
            ExternalUrlPageVO vo = new ExternalUrlPageVO();
            BeanHelp.copyProperties(externalUrl, vo);
            // 所属对象类型名称
            vo.setOwnerTypeName(dictDataService.getLabel(SystemDictTypeConstants.OWNER_TYPE, externalUrl.getOwnerType()));
            // 启用标识名称
            vo.setEnableFlagName(DictHelp.castFlag(externalUrl.getEnableFlag()));
            return vo;
        });
    }

    @Override
    public ExternalUrlDetailVO detail(Long id) {

        log.info("外部链接-详情-开始：{}", id);
        // 查询详情
        ExternalUrl externalUrl = getById(id);
        if (ObjUtil.isNull(externalUrl)) {
            log.warn("外部链接-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "外部链接-详情数据不存在");
        }
        // 复制到返回值
        ExternalUrlDetailVO vo = new ExternalUrlDetailVO();
        BeanHelp.copyProperties(externalUrl, vo);
        // 所属对象类型名称
        vo.setOwnerTypeName(dictDataService.getLabel(SystemDictTypeConstants.OWNER_TYPE, externalUrl.getOwnerType()));
        // 启用标识名称
        vo.setEnableFlagName(DictHelp.castFlag(externalUrl.getEnableFlag()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExternalUrl add(ExternalUrlAddDTO dto) {

        log.info("外部链接-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        ExternalUrl externalUrl = new ExternalUrl();
        BeanHelp.copyProperties(dto, externalUrl);
        // 添加
        save(externalUrl);
        return externalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExternalUrl edit(ExternalUrlEditDTO dto) {

        log.info("外部链接-修改-开始：{}", dto);
        // 获取数据库对象
        ExternalUrl externalUrl = getById(dto.getId());
        if (ObjUtil.isNull(externalUrl)) {
            log.warn("外部链接-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "外部链接-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanHelp.copyPropertiesIgnoreNull(dto, externalUrl);
        // 修改
        updateById(externalUrl);
        return externalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {

        log.info("外部链接-删除-开始：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
        // 删除
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBatch(BaseIdListDTO dto) {

        log.info("外部链接-批量删除-开始：{}", dto);
        // 批量删除
        return removeByIds(dto.getIdList());
    }
    
}
