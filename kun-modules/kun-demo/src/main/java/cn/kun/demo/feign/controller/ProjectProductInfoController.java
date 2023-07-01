package cn.kun.demo.feign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.product.dto.ProjectProductInfoListDTO;
import cn.kun.base.api.entity.product.dto.ProjectProductInfoPageDTO;
import cn.kun.base.api.entity.product.vo.ProjectProductInfoPageVO;
import cn.kun.base.api.service.product.RemoteProjectProductInfoService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 项目下挂靠产品-控制层
 *
 * @author kuangjc
 * @date 2023-03-24 10:39
 */
@Tag(name = "项目下挂靠产品信息")
@RestController
@RequestMapping("product/projectProductInfo")
public class ProjectProductInfoController extends BaseController {

    @Autowired
    private RemoteProjectProductInfoService remoteProjectProductInfoService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<ProjectProductInfoPageVO>> page(@RequestBody @Valid ProjectProductInfoPageDTO dto) {
        return success(remoteProjectProductInfoService.page(dto).getData());
    }
    
    @Operation(summary = "列表")
    @PostMapping("list")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<List<BaseSelectVO>> list(@RequestBody @Valid ProjectProductInfoListDTO dto) {
        return success(remoteProjectProductInfoService.list(dto).getData());
    }
    
    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        remoteProjectProductInfoService.remove(id);
        return success();
    }
    
}
