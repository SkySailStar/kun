package cn.kun.system.area.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.system.area.entity.dto.AreaAddDTO;
import cn.kun.system.area.entity.dto.AreaEditDTO;
import cn.kun.system.area.entity.dto.AreaPageDTO;
import cn.kun.system.area.entity.po.Area;
import cn.kun.system.area.entity.vo.AreaDetailVO;
import cn.kun.system.area.entity.vo.AreaPageVO;

import java.util.List;

/**
 * <p>
 * 行政区划 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-06 18:08
 */
public interface AreaService extends IService<Area> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<AreaPageVO> page(AreaPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    AreaDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    Area add(AreaAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    Area edit(AreaEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     * @return 结果          
     */
    boolean remove(Long id);

    /**
     * 根据主键列表批量删除
     * @param dto 批量删除-公用传入值
     * @return 结果           
     */
    boolean removeBatch(BaseIdListDTO dto);

    /**
     * 列表
     * @return 区域列表
     */
    List<BaseSelectVO> listAll();
    
    /**
     * 根据ID查询下级列表
     *
     * @param id 主键
     * @return 公用下拉框-返回值
     */
    List<BaseSelectVO> list(Long id);

    /**
     * 通过id获取名称
     * @param id 区域ID
     * @return 区域名称
     */
    String getNameById(Long id);
}
