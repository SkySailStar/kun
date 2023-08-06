package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.DeviceTypePageDTO;
import cn.kun.base.api.entity.product.dto.DeviceTypeQueryDTO;
import cn.kun.base.api.entity.product.vo.DeviceTypePageVO;
import cn.kun.base.api.entity.product.vo.DeviceTypeQueryVO;
import cn.kun.base.api.entity.product.vo.DeviceTypeSimpleVO;
import cn.kun.base.api.service.product.factory.RemoteDeviceTypeFallbackFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 远程挂载装置类型服务
 *
 * @author SkySailStar
 * @date 2023-03-17 17:01
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDeviceTypeFallbackFactory.class)
public interface RemoteDeviceTypeService {

    /**
     * 分页
     * @param dto 挂载装置类型-分页-传入值
     * @return 分页列表
     */
    @PostMapping("device/deviceType/page")
    BaseResult<Page<DeviceTypePageVO>> page(@RequestBody @Valid DeviceTypePageDTO dto);
    
    /**
     * 列表
     * @return 公用下拉框-返回值
     */
    @GetMapping("device/deviceType/list")
    BaseResult<List<BaseSelectVO>> list();
    
    /**
     * 删除
     * @param id 主键
     * @return 结果
     */
    @DeleteMapping("device/deviceType/{id}")
    BaseResult<Void> remove(@PathVariable("id") Long id);

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("device/deviceType/query")
    BaseResult<List<DeviceTypeQueryVO>> query(@RequestBody DeviceTypeQueryDTO dto);

    /**
     * 获取单项
     * @param id 主键
     * @return 单项
     */
    @GetMapping("device/deviceType/simple/{id}")
    BaseResult<DeviceTypeSimpleVO> simple(@PathVariable("id") Long id);

    /**
     * 获取多项
     * @param dto 主键列表-公用传入值
     * @return 多项
     */
    @PostMapping("device/deviceType/simple/queryListByIds")
    BaseResult<List<DeviceTypeSimpleVO>> queryListByIds(@RequestBody @Valid BaseIdListDTO dto);
}
