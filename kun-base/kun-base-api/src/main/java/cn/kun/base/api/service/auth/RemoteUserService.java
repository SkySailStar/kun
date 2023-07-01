package cn.kun.base.api.service.auth;

import cn.kun.base.api.entity.auth.dto.UserListDTO;
import cn.kun.base.api.entity.auth.vo.SysUserCacheInfoVO;
import cn.kun.base.api.entity.auth.vo.UserDetailVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.system.factory.RemoteDictFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *  远程调用用户接口
 * @author eric
 * @date 2023/3/24 17:21
 */
@FeignClient(value = ServiceConstants.AUTH, fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 根据用户id集合获取内部用户信息
     * @param dto 用户id集合
     * @return
     */
    @PostMapping("system/sysUserInner/list")
    BaseResult<List<UserDetailVO>> userInnerList(@RequestBody UserListDTO dto);
    /**
     * 根据用户id集合获取外部用户信息
     * @param dto 用户id集合
     * @return
     */
    @PostMapping("system/sysUserOuter/list")
    BaseResult<List<UserDetailVO>> userOuterList(@RequestBody UserListDTO dto);

    /**
     *  获取内部用户权限、菜单权限、项目权限、角色权限、公司、部门、职位信息
     * @param userId 用户id
     * @return
     */
    @GetMapping("system/sysUserInner/getUserCacheInfoByUserId/{userId}")
    BaseResult<SysUserCacheInfoVO> userInnerDetail(@PathVariable("userId") Long userId);

    /**
     *  获取外部用户权限、菜单权限、项目权限、角色权限、公司、部门、职位信息
     * @param userId 用户id
     * @return
     */
    @GetMapping("system/sysUserOuter/getUserCacheInfoByUserId/{userId}")
    BaseResult<SysUserCacheInfoVO> userOuterDetail(@PathVariable("userId") Long userId);
}