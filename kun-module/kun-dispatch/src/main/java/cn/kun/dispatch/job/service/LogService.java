package cn.kun.dispatch.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;

/**
 * 日志-服务层
 *
 * @author 廖航
 */
public interface LogService {

    /**
     * 分页
     * @param dto 日志-分页-传入值
     * @return 日志-分页-返回值
     */
    Page<LogPageVO> page(LogPageDTO dto);

}