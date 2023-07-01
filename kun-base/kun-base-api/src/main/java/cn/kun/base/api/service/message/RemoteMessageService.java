package cn.kun.base.api.service.message;

import cn.kun.base.api.entity.message.dto.SendMessageDTO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 预警内部调用接口
 *
 * @author eric
 * @date 2023/4/20 14:16
 */
@FeignClient(ServiceConstants.MESSAGE)
public interface RemoteMessageService {

    /**
     * 发送消息
     * @param dto 查询-传入值
     * @return 结果
     */
    @PostMapping("storage/pushInfo/sendMessage")
    BaseResult<Boolean> sendMessage(@RequestBody SendMessageDTO dto);
}