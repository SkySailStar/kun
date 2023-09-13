package cn.kun.dispatch.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;

/**
 * xxl-job 执行器-服务层
 *
 * @author 廖航
 * @date 2023-06-06 10:26
 */
public interface ExecutorService {

    /**
     * 分页
     * @param dto 执行器-分页-传入值
     * @return 执行器-分页-返回值
     */
    Page<ExecutorPageVO> page(ExecutorPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 执行器详情
     */
    ExecutorDetailVO detail(Integer id);
    
}
