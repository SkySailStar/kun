package cn.kun.system.software.service;

import cn.kun.system.software.entity.dto.SoftwareInfoAddDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoEditDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoPageDTO;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.entity.vo.SoftwareInfoDetailVO;
import cn.kun.system.software.entity.vo.SoftwareInfoPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;

/**
 * <p>
 * 软件服务信息 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:37
 */
public interface SoftwareInfoService extends IService<SoftwareInfo> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<SoftwareInfoPageVO> page(SoftwareInfoPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    SoftwareInfoDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    SoftwareInfo add(SoftwareInfoAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    SoftwareInfo edit(SoftwareInfoEditDTO dto);

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
