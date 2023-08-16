package cn.kun.auth.login.service;

import cn.kun.auth.login.entity.dto.LoginUserAddDTO;
import cn.kun.auth.login.entity.dto.LoginUserPageDTO;
import cn.kun.auth.login.entity.po.LoginUserInfoOuter;
import cn.kun.auth.login.entity.vo.LoginUserPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 外部用户登录信息表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:39
 */
public interface LoginUserInfoOuterService extends IService<LoginUserInfoOuter> {
    /**
     * 分页列表
     * @param dto 用户登录信息分页-传入值
     * @return 用户登录信息分页列表-返回值
     */
    Page<LoginUserPageVO> page(LoginUserPageDTO dto);


    /**
     * 添加
     * @param dto 用户登录信息添加-传入值
     * @return 用户登录信息对象-返回值
     */
    LoginUserInfoOuter add(LoginUserAddDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

}
