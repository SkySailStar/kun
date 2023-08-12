package cn.kun.system.monitor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.system.monitor.entity.dto.MonitorDataAddDTO;
import cn.kun.system.monitor.entity.dto.MonitorDataPageDTO;
import cn.kun.system.monitor.entity.po.MonitorData;
import cn.kun.system.monitor.entity.vo.MonitorDataDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorDataPageVO;

/**
 * <p>
 * 监控数据 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
public interface MonitorDataService extends IService<MonitorData> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<MonitorDataPageVO> page(MonitorDataPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    MonitorDataDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    MonitorData add(MonitorDataAddDTO dto);
    
}
