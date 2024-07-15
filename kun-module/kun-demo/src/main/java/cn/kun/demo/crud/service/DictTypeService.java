package cn.kun.demo.crud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.demo.crud.entity.dto.DictTypeAddDTO;
import cn.kun.demo.crud.entity.dto.DictTypeEditDTO;
import cn.kun.demo.crud.entity.dto.DictTypePageDTO;
import cn.kun.demo.crud.entity.po.DictType;
import cn.kun.demo.crud.entity.vo.DictTypeDetailVO;
import cn.kun.demo.crud.entity.vo.DictTypePageVO;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author 天航星
 * @since 2023-03-23 10:24
 */
public interface DictTypeService extends IService<DictType> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<DictTypePageVO> page(DictTypePageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    DictTypeDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    String add(DictTypeAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    boolean edit(DictTypeEditDTO dto);

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
