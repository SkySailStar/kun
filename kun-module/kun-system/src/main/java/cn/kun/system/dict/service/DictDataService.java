package cn.kun.system.dict.service;

import cn.kun.system.dict.entity.dto.DictDataAddDTO;
import cn.kun.system.dict.entity.dto.DictDataEditDTO;
import cn.kun.system.dict.entity.dto.DictDataPageDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.api.entity.system.dto.DictDataListDTO;
import cn.kun.system.dict.entity.po.DictData;
import cn.kun.system.dict.entity.vo.DictDataDetailVO;
import cn.kun.system.dict.entity.vo.DictDataPageVO;
import cn.kun.base.api.entity.system.vo.DictDataListVO;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author 天航星
 * @since 2023-03-23 10:32
 */
public interface DictDataService extends IService<DictData> {

    /**
     * 分页
     *
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<DictDataPageVO> page(DictDataPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    DictDataDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    DictData add(DictDataAddDTO dto);
    
    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    DictData edit(DictDataEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 根据类型获取列表
     *
     * @param type 字典类型
     * @return 公用下拉框-返回值
     */
    List<BaseSelectVO> list(String type);

    /**
     * 根据类型和键值获取标签
     *
     * @param type 字典类型
     * @param value 字典键值
     * @return 字典标签
     */
    String getLabel(String type, String value);

    /**
     * 字典列表
     * @param dto 字典数据-列表-传入值
     * @return 字典列表
     */
    List<DictDataListVO> list(DictDataListDTO dto);
    
}
