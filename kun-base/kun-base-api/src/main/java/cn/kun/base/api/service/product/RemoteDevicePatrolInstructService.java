package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.DevicePatrolInstructQueryDTO;
import cn.kun.base.api.entity.product.vo.DevicePatrolInstructQueryVO;
import cn.kun.base.api.entity.product.vo.DevicePatrolInstructSimpleVO;
import cn.kun.base.api.service.product.factory.RemoteDevicePatrolInstructFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 远程挂载装置巡检指令服务
 *
 * @author SkySailStar
 * @date 2023-03-27 17:29
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteDevicePatrolInstructFallbackFactory.class)
public interface RemoteDevicePatrolInstructService {

    /**
     * 通过id查询基本信息
     * @param dto
     * @return
     */
    @PostMapping("device/patrol-instruct/query")
    BaseResult<List<DevicePatrolInstructQueryVO>> query(@RequestBody DevicePatrolInstructQueryDTO dto);

    /**
     * 获取单项
     * @param id 主键
     * @return 单项
     */
    @GetMapping("device/patrol-instruct/simple/{id}")
    BaseResult<DevicePatrolInstructSimpleVO> simple(@PathVariable("id") Long id);

    /**
     * 获取多项
     * @param dto 主键列表-公用传入值
     * @return 多项
     */
    @PostMapping("device/patrol-instruct/simple/queryListByIds")
    BaseResult<List<DevicePatrolInstructSimpleVO>> queryListByIds(@RequestBody @Valid BaseIdListDTO dto);

}
