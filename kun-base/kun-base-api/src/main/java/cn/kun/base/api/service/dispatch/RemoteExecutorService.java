package cn.kun.base.api.service.dispatch;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.base.api.service.dispatch.factory.RemoteExecutorFallbackFactory;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程执行器服务
 *
 * @author SkySailStar
 * @date 2023-06-07 10:07
 */
@FeignClient(value = ServiceConstants.DISPATCH, fallbackFactory = RemoteExecutorFallbackFactory.class)
public interface RemoteExecutorService {

    /**
     * 分页
     * @param dto 执行器-分页-传入值
     * @return 执行器分页列表
     */
    @PostMapping("executor/page")
    BaseResult<Page<ExecutorPageVO>> page(@RequestBody ExecutorPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情信息
     */
    @GetMapping("executor/{id}")
    BaseResult<ExecutorDetailVO> detail(@PathVariable("id") Integer id);
    
}
