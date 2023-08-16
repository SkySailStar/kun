package cn.kun.base.core.security.service;


import cn.kun.base.core.security.entity.LoginUser;
import cn.kun.base.core.security.entity.vo.LoginVO;
import cn.kun.base.core.security.entity.dto.CheckTokenDTO;
import cn.kun.base.core.security.entity.dto.LoginDTO;

import java.util.List;

/**
 * 认证-服务层
 *
 * @author SkySailStar
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param dto 系统用户
     * @return 结果
     */
    LoginVO login(LoginDTO dto) throws Exception;

    /**
     * 退出登录
     */
    void logout();

    /**
     * 校验Token
     *
     * @param dto 校验Token-传入值
     * @return 是否有效
     */
    boolean checkToken(CheckTokenDTO dto);

    /**
     * 获取权限列表
     * @return 限列表
     */
    List<String> permissions();

    /**
     * 获取实时用户信息
     * @return 用户信息
     */
    LoginUser getRealUserInfo();
}
