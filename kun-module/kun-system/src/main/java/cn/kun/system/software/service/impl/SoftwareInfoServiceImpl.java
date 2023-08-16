package cn.kun.system.software.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.software.entity.dto.SoftwareInfoAddDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoEditDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoPageDTO;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.entity.vo.SoftwareInfoDetailVO;
import cn.kun.system.software.entity.vo.SoftwareInfoPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import cn.kun.system.server.entity.po.ServerInfo;
import cn.kun.system.server.service.ServerInfoService;
import cn.kun.system.software.mapper.SoftwareInfoMapper;
import cn.kun.system.software.service.SoftwareInfoService;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 软件服务信息 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:37
 */
@Slf4j
@Service
public class SoftwareInfoServiceImpl extends ServiceImpl<SoftwareInfoMapper, SoftwareInfo> implements SoftwareInfoService {

    @Resource
    private DictDataService dictDataService;
    
    @Resource
    private ServerInfoService serverInfoService;
    
    @Override
    public Page<SoftwareInfoPageVO> page(SoftwareInfoPageDTO dto) {

        log.info("软件服务信息-分页-开始：{}", dto);
        // 封装分页参数
        Page<SoftwareInfo> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页列表查询
        Page<SoftwareInfoPageVO> voPage = baseMapper.selectPage(page, dto);
        try {
            voPage.convert(vo -> {
                // 服务类型名称
                vo.setTypeName(dictDataService.getLabel(SystemDictTypeConstants.SERVER_TYPE, vo.getType()));
                // 运行状态名称
                vo.setRunStatusName(dictDataService.getLabel(SystemDictTypeConstants.RUN_STATUS, vo.getRunStatus()));
                // 使用状态名称
                vo.setUseStatusName(dictDataService.getLabel(SystemDictTypeConstants.USE_STATUS, vo.getUseStatus()));
                // 预警标识名称
                vo.setWarnFlagName(DictHelp.castFlag(vo.getWarnFlag()));
                return vo;
            });
        } catch (Exception e) {
            log.warn("系统服务-字典数据-调用失败");
        }
        return voPage;
    }

    @Override
    public SoftwareInfoDetailVO detail(Long id) {

        log.info("软件服务信息-详情-开始：{}", id);
        // 查询详情
        SoftwareInfo softwareInfo = getById(id);
        if (ObjUtil.isNull(softwareInfo)) {
            log.warn("软件服务信息-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "软件服务信息-详情数据不存在");
        }
        // 复制到返回值
        SoftwareInfoDetailVO vo = new SoftwareInfoDetailVO();
        BeanUtil.copyProperties(softwareInfo, vo);
        // 服务器名称
        ServerInfo serverInfo = serverInfoService.getById(softwareInfo.getServerId());
        if (ObjUtil.isNotNull(serverInfo)) {
            vo.setServerName(serverInfo.getName());
        }
        // 服务类型名称
        vo.setTypeName(dictDataService.getLabel(SystemDictTypeConstants.SERVER_TYPE, softwareInfo.getType()));
        // 运行状态名称
        vo.setRunStatusName(dictDataService.getLabel(SystemDictTypeConstants.RUN_STATUS, softwareInfo.getRunStatus()));
        // 使用状态名称
        vo.setUseStatusName(dictDataService.getLabel(SystemDictTypeConstants.USE_STATUS, softwareInfo.getUseStatus()));
        // 预警标识名称
        vo.setWarnFlagName(DictHelp.castFlag(softwareInfo.getWarnFlag()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoftwareInfo add(SoftwareInfoAddDTO dto) {

        log.info("软件服务信息-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        SoftwareInfo softwareInfo = new SoftwareInfo();
        BeanUtil.copyProperties(dto, softwareInfo);
        // 添加
        save(softwareInfo);
        return softwareInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoftwareInfo edit(SoftwareInfoEditDTO dto) {

        log.info("软件服务信息-修改-开始：{}", dto);
        // 获取数据库对象
        SoftwareInfo softwareInfo = getById(dto.getId());
        if (ObjUtil.isNull(softwareInfo)) {
            log.warn("软件服务信息-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "软件服务信息-修改数据不存在");
        }
        // 传入值复制到数据库对象
        BeanHelp.copyPropertiesIgnoreNull(dto, softwareInfo);
        // 修改
        updateById(softwareInfo);
        return softwareInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("软件服务信息-删除-开始：{}", id);
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

        log.info("软件服务信息-批量删除-开始：{}", dto);
        // 批量删除
        removeByIds(dto.getIdList());
    }
    
}
