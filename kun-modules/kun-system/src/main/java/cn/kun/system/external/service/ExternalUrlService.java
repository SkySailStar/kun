package cn.kun.system.external.service;

import cn.kun.system.external.entity.dto.ExternalUrlAddDTO;
import cn.kun.system.external.entity.dto.ExternalUrlEditDTO;
import cn.kun.system.external.entity.dto.ExternalUrlPageDTO;
import cn.kun.system.external.entity.vo.ExternalUrlDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.external.entity.po.ExternalUrl;
import cn.kun.system.external.entity.vo.ExternalUrlPageVO;

/**
 * <p>
 * 外部链接 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
public interface ExternalUrlService extends IService<ExternalUrl> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<ExternalUrlPageVO> page(ExternalUrlPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    ExternalUrlDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    ExternalUrl add(ExternalUrlAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    ExternalUrl edit(ExternalUrlEditDTO dto);

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
