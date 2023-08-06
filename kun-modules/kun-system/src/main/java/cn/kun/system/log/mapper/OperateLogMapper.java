package cn.kun.system.log.mapper;

import cn.kun.system.log.entity.dto.OperateLogPageDTO;
import cn.kun.system.log.entity.po.OperateLog;
import cn.kun.system.log.entity.vo.OperateLogPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
public interface OperateLogMapper extends BaseMapper<OperateLog> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<OperateLogPageVO> selectPage(Page<OperateLog> page, @Param("dto") OperateLogPageDTO dto);
    
}
