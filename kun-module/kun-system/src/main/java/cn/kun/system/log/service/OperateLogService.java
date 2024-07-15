package cn.kun.system.log.service;

import cn.kun.system.log.entity.dto.OperateLogAddDTO;
import cn.kun.system.log.entity.dto.OperateLogPageDTO;
import cn.kun.system.log.entity.po.OperateLog;
import cn.kun.system.log.entity.vo.OperateLogDetailVO;
import cn.kun.system.log.entity.vo.OperateLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author 天航星
 * @since 2023-04-09 16:48
 */
public interface OperateLogService extends IService<OperateLog> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<OperateLogPageVO> page(OperateLogPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    OperateLogDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    OperateLog add(OperateLogAddDTO dto);
    
}
