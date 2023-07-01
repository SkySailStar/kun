package cn.kun.base.api.service.patrol;

import cn.kun.base.api.entity.patrol.dto.PatrolCategoryQueryDTO;
import cn.kun.base.api.entity.patrol.vo.PatrolCategoryVO;
import cn.kun.base.api.service.patrol.factory.RemotePatrolCategoryFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程巡检类型服务
 *
 * @author kuangjc
 * @date 2023-04-06 17:41
 */
@FeignClient(value = ServiceConstants.PATROL, fallbackFactory = RemotePatrolCategoryFallbackFactory.class)
public interface RemotePatrolCategoryService {

    /**
     * 查询
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("patrol/patrol-category/query")
    BaseResult<List<PatrolCategoryVO>> query(@RequestBody PatrolCategoryQueryDTO dto);

}
