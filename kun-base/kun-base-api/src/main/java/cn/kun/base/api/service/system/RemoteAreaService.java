package cn.kun.base.api.service.system;

import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.system.factory.RemoteAreaFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程区域服务
 *
 * @author 廖航
 * @date 2023-04-26 16:40
 */
@FeignClient(value = ServiceConstants.SYSTEM, fallbackFactory = RemoteAreaFallbackFactory.class)
public interface RemoteAreaService {

    /**
     * 通过id获取名称
     * @param id 区域ID
     * @return 区域名称
     */
    @GetMapping("area/name/{id}")
    BaseResult<String> getNameById(@PathVariable("id") Long id);
    
}
