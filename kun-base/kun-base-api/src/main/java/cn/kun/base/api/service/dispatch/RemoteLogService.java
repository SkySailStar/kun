package cn.kun.base.api.service.dispatch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;
import cn.kun.base.api.service.dispatch.factory.RemoteLogFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程日志服务
 *
 * @author 廖航
 * @date 2023-06-07 10:16
 */
@FeignClient(value = ServiceConstants.DISPATCH, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 分页
     * @param dto 日志-分页-传入值
     * @return 日志分页列表
     */
    @PostMapping("log/page")
    BaseResult<Page<LogPageVO>> page(@RequestBody LogPageDTO dto);
    
}
