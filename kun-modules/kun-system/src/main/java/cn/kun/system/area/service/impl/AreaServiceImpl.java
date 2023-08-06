package cn.kun.system.area.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.data.AreaTypeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.entity.BaseTreeBuild;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.system.area.entity.dto.AreaAddDTO;
import cn.kun.system.area.entity.dto.AreaEditDTO;
import cn.kun.system.area.entity.dto.AreaPageDTO;
import cn.kun.system.area.entity.po.Area;
import cn.kun.system.area.entity.vo.AreaDetailVO;
import cn.kun.system.area.entity.vo.AreaPageVO;
import cn.kun.system.area.mapper.AreaMapper;
import cn.kun.system.area.service.AreaService;
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
 * @author SkySailStar
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
        // 查询详情
        Area area = getById(id);
        if (ObjUtil.isNull(area)) {
            log.warn("区域-详情：数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "区域-详情：数据不存在");
        }
        // 复制到返回值
        AreaDetailVO vo = new AreaDetailVO();
        BeanUtil.copyProperties(area, vo);
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
    public Area add(AreaAddDTO dto) {

        log.info("区域-添加：{}", dto);
        // 传入值复制到数据库对象
        Area area = new Area();
        BeanUtil.copyProperties(dto, area);
        // 添加
        save(area);
        return area;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Area edit(AreaEditDTO dto) {

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
        updateById(area);
        return area;
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

    @Override
    public List<BaseSelectVO> listAll() {

        // 下拉选列表
        List<BaseSelectVO> voList = baseMapper.selectAllList();
        // 构建树形
        return new BaseTreeBuild(voList).buildTree();
    }

    @Override
    public List<BaseSelectVO> list(Long id) {

        log.info("区域-根据ID查询下级列表：{}", id);
        // 过滤条件
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Area::getId,
                        Area::getName,
                        Area::getParentId,
                        Area::getParentIds);
        // 查询的区域类型
        String queryType = StrUtil.EMPTY;
        // 主键为空，查最顶级
        if (ObjUtil.isNull(id) || ObjUtil.equals(id, 0L)) {
            queryType = AreaTypeConstants.NATION;
        } else {
            Area area = getById(id);
            if (ObjUtil.isNull(area)) {
                log.warn("区域-列表：数据不存在");
                throw new BusinessException(ErrorCodeConstants.WITHOUT, "区域-列表：数据不存在");
            }
            // 区域类型
            String type = area.getType();
            // 如果当前为国级，查省级
            if (StrUtil.equals(type, AreaTypeConstants.NATION)) {
                queryType = AreaTypeConstants.PROVINCE;
            }
            // 如果当前为省级，查市级
            if (StrUtil.equals(type, AreaTypeConstants.PROVINCE)) {
                queryType = AreaTypeConstants.CITY;
            }
            // 如果当前为市级，查区级
            if (StrUtil.equals(type, AreaTypeConstants.CITY)) {
                queryType = AreaTypeConstants.COUNTY;
            }
            queryWrapper.lambda().eq(Area::getParentId, id);
        }
        queryWrapper.lambda().eq(StrUtil.isNotBlank(queryType), Area::getType, queryType);
        return list(queryWrapper).stream().map(this::cast).toList();
    }

    @Override
    public String getNameById(Long id) {
        
        log.info("区域-通过id获取名称-开始：{}", id);
        String name = StrUtil.EMPTY;
        if (ObjUtil.isNull(id)) {
            return name;
        }
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Area::getName)
                .eq(Area::getId, id);
        Area area = getOne(queryWrapper);
        if (ObjUtil.isNotNull(area)) {
            name = area.getName();
        }
        return name;
    }

    /**
     * 转换
     *
     * @param area 区域
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Area area) {

        BaseSelectVO vo = new BaseSelectVO();
        vo.setValue(Convert.toStr(area.getId()));
        vo.setLabel(area.getName());
        vo.setParentValue(Convert.toStr(area.getParentId()));
        vo.setParentValues(area.getParentIds());
        vo.setSort(area.getSort());
        return vo;
    }
    
}
