package cn.kun.demo.crud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.demo.crud.entity.dto.AreaAddDTO;
import cn.kun.demo.crud.entity.dto.AreaEditDTO;
import cn.kun.demo.crud.entity.dto.AreaPageDTO;
import cn.kun.demo.crud.entity.po.Area;
import cn.kun.demo.crud.entity.vo.AreaDetailVO;
import cn.kun.demo.crud.entity.vo.AreaPageVO;

import java.util.List;

/**
 * <p>
 * 行政区划 服务类
 * </p>
 *
 * @author 天航星
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
    String add(AreaAddDTO dto);

    /**
     * 批量添加
     * @param dtoList 添加-传入值列表
     * @return 结果
     */
    boolean addBatch(List<AreaAddDTO> dtoList);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    boolean edit(AreaEditDTO dto);

    /**
     * 批量修改
     * @param dtoList 修改-传入值列表
     * @return 结果               
     */
    boolean editBatch(List<AreaEditDTO> dtoList);

    /**
     * 根据主键删除
     * @param id 主键
     * @return 结果          
     */
    boolean remove(Long id);

    /**
     * 根据主键列表批量删除
     * @param dto 主键列表-公用传入值
     * @return 结果           
     */
    boolean removeBatch(BaseIdListDTO dto);
    
}
