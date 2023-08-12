package cn.kun.auth.system.login.mapper;

import cn.kun.auth.system.login.entity.po.LoginLimitInfoOuter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.system.login.entity.dto.LoginLimitPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.auth.system.login.entity.vo.LoginLimitPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 外部登录限制表 Mapper 接口
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:38
 */
public interface LoginLimitInfoOuterMapper extends BaseMapper<LoginLimitInfoOuter> {
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 登录限制分页列表-传入值
     * @return 分页列表-返回值
     */
    Page<LoginLimitPageVO> selectPage(Page<LoginLimitPageDTO> page, @Param("dto") LoginLimitPageDTO dto);
}
