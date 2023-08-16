package cn.kun.auth.login.mapper;

import cn.kun.auth.login.entity.po.LoginUserInfoInner;
import cn.kun.auth.login.entity.vo.LoginUserPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.auth.login.entity.dto.LoginUserPageDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 内部用户登录信息表 Mapper 接口
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:39
 */
public interface LoginUserInfoInnerMapper extends BaseMapper<LoginUserInfoInner> {
    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 用户登录信息分页列表-传入值
     * @return 分页列表-返回值
     */
    Page<LoginUserPageVO> selectPage(Page<LoginUserPageDTO> page, @Param("dto") LoginUserPageDTO dto);
}