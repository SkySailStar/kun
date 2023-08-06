package cn.kun.system.server.service;

import cn.kun.system.server.entity.dto.ServerInfoAddDTO;
import cn.kun.system.server.entity.dto.ServerInfoEditDTO;
import cn.kun.system.server.entity.dto.ServerInfoPageDTO;
import cn.kun.system.server.entity.vo.ServerInfoDetailVO;
import cn.kun.system.server.entity.vo.ServerInfoPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.server.entity.po.ServerInfo;

/**
 * <p>
 * 服务器信息 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:35
 */
public interface ServerInfoService extends IService<ServerInfo> {

    /**
     * 分页
     *
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<ServerInfoPageVO> page(ServerInfoPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    ServerInfoDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    ServerInfo add(ServerInfoAddDTO dto);

    /**
     * 修改
     *
     * @param dto 修改-传入值
     * @return 修改后的数据
     */
    ServerInfo edit(ServerInfoEditDTO dto);

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
