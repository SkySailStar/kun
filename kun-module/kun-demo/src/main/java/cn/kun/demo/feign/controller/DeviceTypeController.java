package cn.kun.demo.feign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.product.dto.DeviceTypePageDTO;
import cn.kun.base.api.entity.product.vo.DeviceTypePageVO;
import cn.kun.base.api.service.product.RemoteDeviceTypeService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * 挂载装置类型-控制层
 *
 * @author SkySailStar
 * @date 2023-03-17 17:39
 */
@Tag(name = "挂载装置类型")
@RestController
@RequestMapping("device/deviceType")
public class DeviceTypeController extends BaseController {

    @Autowired
    private RemoteDeviceTypeService remoteDeviceTypeService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<DeviceTypePageVO>> page(@RequestBody @Valid DeviceTypePageDTO dto) {
        return remoteDeviceTypeService.page(dto);
    }
    
    @Operation(summary = "列表")
    @GetMapping("list")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<List<BaseSelectVO>> list() {
        return remoteDeviceTypeService.list();
    }
    
    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        remoteDeviceTypeService.remove(id);
        return success();
    }
    
}
