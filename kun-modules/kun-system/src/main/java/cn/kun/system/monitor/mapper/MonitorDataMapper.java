package cn.kun.system.monitor.mapper;

import cn.kun.system.monitor.entity.po.MonitorData;
import cn.kun.system.monitor.entity.vo.MonitorDataPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.system.monitor.entity.dto.MonitorDataPageDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 监控数据 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
public interface MonitorDataMapper extends BaseMapper<MonitorData> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<MonitorDataPageVO> selectPage(Page<MonitorData> page, @Param("dto") MonitorDataPageDTO dto);
    
}
