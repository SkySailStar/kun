package cn.kun.system.log.service;

import cn.kun.system.log.entity.dto.RunLogAddDTO;
import cn.kun.system.log.entity.dto.RunLogPageDTO;
import cn.kun.system.log.entity.po.RunLog;
import cn.kun.system.log.entity.vo.RunLogDetailVO;
import cn.kun.system.log.entity.vo.RunLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 运行日志 服务类
 * </p>
 *
 * @author 天航星
 * @since 2023-04-09 16:48
 */
public interface RunLogService extends IService<RunLog> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<RunLogPageVO> page(RunLogPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    RunLogDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    RunLog add(RunLogAddDTO dto);
    
}
