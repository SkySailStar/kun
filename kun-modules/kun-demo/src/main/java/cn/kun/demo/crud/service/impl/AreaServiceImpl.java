package cn.kun.demo.crud.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.kun.demo.crud.mapper.AreaMapper;
import cn.kun.demo.crud.service.AreaService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.obj.ObjHelp;
import cn.kun.demo.crud.entity.dto.AreaAddDTO;
import cn.kun.demo.crud.entity.dto.AreaEditDTO;
import cn.kun.demo.crud.entity.dto.AreaPageDTO;
import cn.kun.demo.crud.entity.po.Area;
import cn.kun.demo.crud.entity.vo.AreaDetailVO;
import cn.kun.demo.crud.entity.vo.AreaPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 行政区划 服务实现类
 * </p>
 *
 * @author 廖航
 * @since 2023-04-06 18:08
 */
@Slf4j
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Autowired
    private BaseDictService baseDictService;

    @Override
    public Page<AreaPageVO> page(AreaPageDTO dto) {

        log.info("区域-分页：{}", dto);
        // 封装分页参数
        Page<Area> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页列表查询
        Page<AreaPageVO> voPage = baseMapper.selectPage(page, dto);
        try {
            voPage.convert(vo -> {
                vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.AREA_TYPE, vo.getType()));
                return vo;
            });
        } catch (Exception e) {
            log.warn("系统服务-字典数据：调用失败");
        }
        return voPage;
    }

    @Override
    public AreaDetailVO detail(Long id) {

        log.info("区域-详情：{}", id);
        if (ObjHelp.isNull(id)) {
            log.warn("区域-详情：主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "区域-详情：主键不能为空");
        }
        // 查询详情
        Area area = getById(id);
        if (ObjUtil.isNull(area)) {
            log.warn("区域-详情：数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "区域-详情：数据不存在");
        }
        // 复制到返回值
        AreaDetailVO vo = BeanHelp.copyProperties(area, AreaDetailVO.class);
        // 类别名称
        vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.AREA_TYPE, area.getType()));
        // 上级名称
        area = getById(area.getParentId());
        if (ObjUtil.isNotNull(area)) {
            vo.setParentName(area.getName());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(AreaAddDTO dto) {

        log.info("区域-添加：{}", dto);
        // 传入值复制到数据库对象
        Area area = BeanHelp.copyProperties(dto, Area.class);
        // 添加
        save(area);
        return area.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBatch(List<AreaAddDTO> dtoList) {

        log.info("区域-批量添加：{}", dtoList);
        // 传入值列表转换为数据库对象列表
        List<Area> sysAreaList = dtoList.stream().map(this::cast).toList();
        // 批量添加
        return saveBatch(sysAreaList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(AreaEditDTO dto) {

        log.info("区域-修改：{}", dto);
        // 获取数据库对象
        Area area = getById(dto.getId());
        if (ObjUtil.isNull(area)) {
            log.warn("区域-修改：数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "区域-修改：数据不存在");
        }
        // 传入值复制到数据库对象（只复制不为空的属性）
        BeanHelp.copyPropertiesIgnoreNull(dto, area);
        // 修改
        return updateById(area);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editBatch(List<AreaEditDTO> dtoList) {

        log.info("区域-批量修改：{}", dtoList);
        // 传入值列表转换为数据库对象列表
        List<Area> sysAreaList = dtoList.stream().map(this::cast).toList();
        // 批量修改
        return updateBatchById(sysAreaList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Long id) {

        log.info("区域-删除：{}", id);
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

        log.info("区域-批量删除：{}", dto);
        // 批量删除
        return removeByIds(dto.getIdList());
    }
    
    /**
     * 转换
     *
     * @param dto 区域-添加-传入值
     * @return 行政区域表
     */
    private Area cast(AreaAddDTO dto) {
        return BeanHelp.copyProperties(dto, Area.class);
    }

    /**
     * 转换
     *
     * @param dto 区域-修改-传入值
     * @return 行政区域表
     */
    private Area cast(AreaEditDTO dto) {

        Area area = new Area();
        // 传入值复制到数据库对象（只复制不为空的属性）
        BeanHelp.copyPropertiesIgnoreNull(dto, area);
        return area;
    }
    
}
