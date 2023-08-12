package cn.kun.system.param.service;

import cn.kun.system.param.entity.dto.ParamConfigAddDTO;
import cn.kun.system.param.entity.dto.ParamConfigEditDTO;
import cn.kun.system.param.entity.dto.ParamConfigPageDTO;
import cn.kun.system.param.entity.po.ParamConfig;
import cn.kun.system.param.entity.vo.ParamConfigDetailVO;
import cn.kun.system.param.entity.vo.ParamConfigPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;

/**
 * <p>
 * 参数配置 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-12 16:16
 */
public interface ParamConfigService extends IService<ParamConfig> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<ParamConfigPageVO> page(ParamConfigPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    ParamConfigDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    ParamConfig add(ParamConfigAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    ParamConfig edit(ParamConfigEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     * @return 结果
     */
    boolean remove(Long id);

    /**
     * 根据主键列表批量删除
     * @param dto 批量删除-公用传入值
     * @return 结果
     */
    boolean removeBatch(BaseIdListDTO dto);
    
}
