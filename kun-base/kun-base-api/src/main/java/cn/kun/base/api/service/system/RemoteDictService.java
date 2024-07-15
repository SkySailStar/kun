package cn.kun.base.api.service.system;

import cn.kun.base.api.entity.system.dto.DictDataListDTO;
import cn.kun.base.api.entity.system.vo.DictDataListVO;
import cn.kun.base.api.service.system.factory.RemoteDictFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程字典服务
 *
 * @author 天航星
 * @date 2023-03-23 16:58
 */
@FeignClient(value = ServiceConstants.SYSTEM, fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteDictService {

    /**
     * 根据类型获取列表
     * @param type 字典类型
     * @return 字典数据列表
     */
    @GetMapping("dict/dict-data/list/{type}")
    BaseResult<List<BaseSelectVO>> list(@PathVariable("type") String type);

    /**
     * 根据类型和键值获取标签
     * @param type 字典类型
     * @param value 字典键值
     * @return 字典标签
     */
    @GetMapping("dict/dict-data/label/{type}/{value}")
    BaseResult<String> getLabel(@PathVariable("type") String type, @PathVariable("value") String value);

    /**
     * 根据多个类型获取多个列表
     * @param dto 字典数据-列表-传入值
     * @return 字典数据-列表-返回值
     */
    @PostMapping("dict/dict-data/list")
    BaseResult<List<DictDataListVO>> list(@RequestBody DictDataListDTO dto);
}
