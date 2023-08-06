package cn.kun.system.monitor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigAddDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigEditDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigPageDTO;
import cn.kun.system.monitor.entity.po.MonitorConfig;
import cn.kun.system.monitor.entity.vo.MonitorConfigDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorConfigPageVO;

/**
 * <p>
 * 监控项配置 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-10 10:08
 */
public interface MonitorConfigService extends IService<MonitorConfig> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<MonitorConfigPageVO> page(MonitorConfigPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    MonitorConfigDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    MonitorConfig add(MonitorConfigAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    MonitorConfig edit(MonitorConfigEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 根据主键列表批量删除
     * @param dto 批量删除-公用传入值
     */
    void removeBatch(BaseIdListDTO dto);
    
}
