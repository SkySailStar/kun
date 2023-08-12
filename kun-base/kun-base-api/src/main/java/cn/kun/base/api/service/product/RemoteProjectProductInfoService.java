package cn.kun.base.api.service.product;

import cn.kun.base.api.entity.product.dto.ProjectProductInfoListDTO;
import cn.kun.base.api.entity.product.dto.ProjectProductInfoPageDTO;
import cn.kun.base.api.entity.product.vo.ProjectProductInfoPageVO;
import cn.kun.base.api.entity.product.vo.ProjectProductInfoSimpleVO;
import cn.kun.base.api.service.product.factory.RemoteProjectProductInfoFallbackFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 远程项目挂靠产品信息 服务类
 * </p>
 *
 * @author kuangjc
 * @since 2023-03-06 17:08
 */
@FeignClient(value = ServiceConstants.PRODUCT, fallbackFactory = RemoteProjectProductInfoFallbackFactory.class)
public interface RemoteProjectProductInfoService {
    /**
     * 分页
     * @param dto 远程项目挂靠产品信息分页-传入值
     * @return 远程项目挂靠产品信息分页-返回值
     */
    @PostMapping("product/projectProductInfo/page")
    BaseResult<Page<ProjectProductInfoPageVO>> page(@RequestBody @Valid ProjectProductInfoPageDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    @DeleteMapping("product/projectProductInfo/{id}")
    BaseResult<Void>  remove(@PathVariable("id") Long id);

    /**
     * 列表
     * @param dto 远程项目挂靠产品信息列表-传入值
     * @return 公用下拉框-返回值
     */
    @PostMapping("product/projectProductInfo/list")
    BaseResult<List<BaseSelectVO>> list(@RequestBody @Valid ProjectProductInfoListDTO dto);

    /**
     * 项目挂靠产品信息详情-返回值
     * @param id
     * @return
     */
    @PostMapping("product/projectProductInfo/simple/{id}")
    BaseResult<ProjectProductInfoSimpleVO> detailSimpleVO(@PathVariable("id") Long id);

    /**
     * 项目挂靠产品信息列表
     * @param dto
     * @return
     */
    @PostMapping("product/projectProductInfo/simple/queryListByIds")
    BaseResult<List<ProjectProductInfoSimpleVO>> queryVOListByIds(@RequestBody BaseIdListDTO dto);
}
