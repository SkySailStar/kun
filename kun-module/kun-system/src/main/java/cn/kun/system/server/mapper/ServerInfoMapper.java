package cn.kun.system.server.mapper;

import cn.kun.system.server.entity.dto.ServerInfoPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.system.server.entity.po.ServerInfo;
import cn.kun.system.server.entity.vo.ServerInfoPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务器信息 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:35
 */
public interface ServerInfoMapper extends BaseMapper<ServerInfo> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<ServerInfoPageVO> selectPage(Page<ServerInfo> page, @Param("dto") ServerInfoPageDTO dto);
    
}
