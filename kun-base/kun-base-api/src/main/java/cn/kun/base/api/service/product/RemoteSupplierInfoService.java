package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.SupplierInfoPageDTO;
import cn.kun.base.api.entity.product.vo.DeviceTypePageVO;
import cn.kun.base.api.service.product.factory.RemoteSupplierInfoFallbackFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 供应商信息 服务
 *
 * @author kuangjc
 * @date 2023-03-27 17:01
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteSupplierInfoFallbackFactory.class)
public interface RemoteSupplierInfoService {

    /**
     * 分页
     * @param dto 供应商信息-分页-传入值
     * @return 分页列表
     */
    @PostMapping("supplier/supplierInfo/page")
    BaseResult<Page<DeviceTypePageVO>> page(@RequestBody @Valid SupplierInfoPageDTO dto);
    
    /**
     * 列表
     * @return 公用下拉框-返回值
     */
    @GetMapping("supplier/supplierInfo/list")
    BaseResult<List<BaseSelectVO>> list();
    
    /**
     * 删除
     * @param id 主键
     * @return 结果
     */
    @DeleteMapping("supplier/supplierInfo/{id}")
    BaseResult<Void> remove(@PathVariable("id") Long id);

    /**
     * 批量删除
     * @param dto
     * @return
     */
    @DeleteMapping("supplier/supplierInfo/batch")
    public BaseResult<Void> removeBatch(@RequestBody @Valid BaseIdListDTO dto);
}
