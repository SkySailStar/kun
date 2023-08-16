package cn.kun.system.param.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.kun.system.param.entity.po.ParamConfig;
import cn.kun.system.param.entity.vo.ParamConfigDetailVO;
import cn.kun.system.param.entity.vo.ParamConfigPageVO;
import cn.kun.system.param.mapper.ParamConfigMapper;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.service.SoftwareInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import cn.kun.system.param.entity.dto.ParamConfigAddDTO;
import cn.kun.system.param.entity.dto.ParamConfigEditDTO;
import cn.kun.system.param.entity.dto.ParamConfigPageDTO;
import cn.kun.system.param.service.ParamConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 参数配置 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-12 16:16
 */
@Slf4j
@Service
public class ParamConfigServiceImpl extends ServiceImpl<ParamConfigMapper, ParamConfig> implements ParamConfigService {
    
    @Autowired
    private SoftwareInfoService softwareInfoService;
    
    @Override
    public Page<ParamConfigPageVO> page(ParamConfigPageDTO dto) {

        log.info("参数配置-分页-开始：{}", dto);
        // 封装分页参数
        Page<ParamConfig> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页列表查询
        Page<ParamConfigPageVO> voPage = baseMapper.selectPage(page, dto);
        return (Page<ParamConfigPageVO>) voPage.convert(vo -> {
            // 预置标识名称
            vo.setPresetFlagName(DictHelp.castFlag(vo.getPresetFlag()));
            return vo;
        });
    }

    @Override
    public ParamConfigDetailVO detail(Long id) {

        log.info("参数配置-详情-开始：{}", id);
        // 查询详情
        ParamConfig paramConfig = getById(id);
        if (ObjUtil.isNull(paramConfig)) {
            log.warn("参数配置-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "参数配置-详情数据不存在");
        }
        // 复制到返回值
        ParamConfigDetailVO vo = new ParamConfigDetailVO();
        BeanHelp.copyProperties(paramConfig, vo);
        // 服务名称
        SoftwareInfo softwareInfo = softwareInfoService.getById(paramConfig.getServiceId());
        if (ObjUtil.isNotNull(softwareInfo)) {
            vo.setServiceName(softwareInfo.getName());
        }
        // 预置标识名称
        vo.setPresetFlagName(DictHelp.castFlag(paramConfig.getPresetFlag()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParamConfig add(ParamConfigAddDTO dto) {

        log.info("参数配置-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        ParamConfig paramConfig = new ParamConfig();
        BeanHelp.copyProperties(dto, paramConfig);
        // 添加
        save(paramConfig);
        return paramConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParamConfig edit(ParamConfigEditDTO dto) {

        log.info("参数配置-修改-开始：{}", dto);
        // 获取数据库对象
        ParamConfig paramConfig = getById(dto.getId());
        if (ObjUtil.isNull(paramConfig)) {
            log.warn("参数配置-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "参数配置-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanHelp.copyPropertiesIgnoreNull(dto, paramConfig);
        // 修改
        updateById(paramConfig);
        return paramConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {

        log.info("参数配置-删除-开始：{}", id);
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

        log.info("参数配置-批量删除-开始：{}", dto);
        // 批量删除
        return removeByIds(dto.getIdList());
    }
    
}
