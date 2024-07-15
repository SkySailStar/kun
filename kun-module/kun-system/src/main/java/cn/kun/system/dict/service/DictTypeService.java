package cn.kun.system.dict.service;

import cn.kun.system.dict.entity.dto.DictTypeAddDTO;
import cn.kun.system.dict.entity.dto.DictTypeEditDTO;
import cn.kun.system.dict.entity.dto.DictTypePageDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.dict.entity.po.DictType;
import cn.kun.system.dict.entity.vo.DictTypeDetailVO;
import cn.kun.system.dict.entity.vo.DictTypePageVO;

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
    DictType add(DictTypeAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    DictType edit(DictTypeEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 根据主键列表批量删除
     * @param dto 批量删除-公用传入值
     */
    void removeBatch(BaseIdListDTO dto);
    
}
