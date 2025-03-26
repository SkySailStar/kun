package cn.kun.base.api.service.system;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;

import java.util.List;

/**
 * 字典-服务层
 *
 * @author 天航星
 * @date 2023-03-23 17:37
 */
public interface DictService {

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
    
}
