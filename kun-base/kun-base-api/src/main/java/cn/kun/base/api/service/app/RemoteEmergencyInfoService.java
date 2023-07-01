package cn.kun.base.api.service.app;

import cn.kun.base.api.service.app.factory.RemoteEmergencyInfoFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 远程预警服务
 *
 * @author 廖航
 */
@FeignClient(value = ServiceConstants.APP, fallbackFactory = RemoteEmergencyInfoFallbackFactory.class)
public interface RemoteEmergencyInfoService {

    /**
     * 推送预警
     * @return 结果
     */
    @PostMapping("websocket/warn/emergency-info/push/warn")
    BaseResult<Void> pushWarn();

}