package cn.kun.system.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.server.entity.dto.ServerInfoAddDTO;
import cn.kun.system.server.entity.dto.ServerInfoEditDTO;
import cn.kun.system.server.entity.dto.ServerInfoPageDTO;
import cn.kun.system.server.entity.vo.ServerInfoDetailVO;
import cn.kun.system.server.entity.vo.ServerInfoPageVO;
import cn.kun.system.server.mapper.ServerInfoMapper;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.service.SoftwareInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务器信息 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:35
 */
@Slf4j
@Service
public class ServerInfoServiceImpl extends ServiceImpl<ServerInfoMapper, ServerInfo> implements ServerInfoService {

    @Autowired
    private DictDataService dictDataService;
    
    @Autowired
    private SoftwareInfoService softwareInfoService;

    @Override
    public Page<ServerInfoPageVO> page(ServerInfoPageDTO dto) {

        log.info("服务器信息-分页-开始：{}", dto);
        // 封装分页参数
        Page<ServerInfo> page = Page.of(dto.getPageNo(), dto.getPageSize());
        Page<ServerInfoPageVO> voPage = baseMapper.selectPage(page, dto);
        return (Page<ServerInfoPageVO>) voPage.convert(vo -> {
            // 运行状态名称
            vo.setRunStatusName(dictDataService.getLabel(SystemDictTypeConstants.RUN_STATUS, vo.getRunStatus()));
            // 使用状态名称
            vo.setUseStatusName(dictDataService.getLabel(SystemDictTypeConstants.USE_STATUS, vo.getUseStatus()));
            // 预警标识名称
            vo.setWarnFlagName(DictHelp.castFlag(vo.getWarnFlag()));
            return vo;
        });
    }

    @Override
    public ServerInfoDetailVO detail(Long id) {

        log.info("服务器信息-详情-开始：{}", id);
        // 查询详情
        ServerInfo serverInfo = getById(id);
        if (ObjUtil.isNull(serverInfo)) {
            log.warn("服务器信息-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "服务器信息-详情数据不存在");
        }
        // 复制到返回值
        ServerInfoDetailVO vo = new ServerInfoDetailVO();
        BeanUtil.copyProperties(serverInfo, vo);
        // 运行状态名称
        vo.setRunStatusName(dictDataService.getLabel(SystemDictTypeConstants.RUN_STATUS, serverInfo.getRunStatus()));
        // 使用状态名称
        vo.setUseStatusName(dictDataService.getLabel(SystemDictTypeConstants.USE_STATUS, serverInfo.getUseStatus()));
        // 预警标识名称
        vo.setWarnFlagName(DictHelp.castFlag(serverInfo.getWarnFlag()));
        // 服务数量
        QueryWrapper<SoftwareInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SoftwareInfo::getServerId, id);
        vo.setServiceNum(softwareInfoService.count(queryWrapper));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerInfo add(ServerInfoAddDTO dto) {

        log.info("服务器信息-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        ServerInfo serverInfo = new ServerInfo();
        BeanUtil.copyProperties(dto, serverInfo);
        // 添加
        save(serverInfo);
        return serverInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerInfo edit(ServerInfoEditDTO dto) {

        log.info("服务器信息-修改-开始：{}", dto);
        // 获取数据库对象
        ServerInfo serverInfo = getById(dto.getId());
        if (ObjUtil.isNull(serverInfo)) {
            log.warn("服务器信息-修改数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "服务器信息-修改数据不存在");
        }
        // 传入值复制到数据库对象（只复制不为空的属性）
        BeanHelp.copyPropertiesIgnoreNull(dto, serverInfo);
        // 修改
        updateById(serverInfo);
        return serverInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("服务器信息-删除-开始：{}", id);
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

        log.info("服务器信息-批量删除-开始：{}", dto);
        // 批量删除
        removeByIds(dto.getIdList());
    }
}