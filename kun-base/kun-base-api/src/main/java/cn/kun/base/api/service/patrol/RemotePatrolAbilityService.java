package cn.kun.base.api.service.patrol;

import cn.kun.base.api.entity.patrol.dto.PatrolAbilityQueryDTO;
import cn.kun.base.api.entity.patrol.vo.PatrolAbilityVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.patrol.factory.RemotePatrolAbilityFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程巡检能力服务
 *
 * @author kuangjc
 * @date 2023-04-06 17:41
 */
@FeignClient(value = ServiceConstants.PATROL, fallbackFactory = RemotePatrolAbilityFallbackFactory.class)
public interface RemotePatrolAbilityService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("patrol/patrol-ability/query")
    BaseResult<List<PatrolAbilityVO>> query(@RequestBody PatrolAbilityQueryDTO dto);

    /**
     * 项目挂靠产品信息详情-返回值
     * @param id
     * @return
     */
    @PostMapping("product/projectProductInfo/{id}")
    BaseResult<PatrolAbilityVO> detail(@PathVariable("id") Long id);

}
