package cn.kun.base.core.security.entity;

import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.util.Collection;
import java.util.List;

/**
 * 登录用户
 *
 * @author 天航星
 */
@Schema(description = "登录用户")
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "用户信息")
    private UserInfo userInfo;

    @Schema(description = "权限信息")
    private List<String> permissions;

    /**
     * 存储SpringSecurity所需要的权限信息的集合<br>
     * 由于是私密信息，不能序列化存储到缓存中，所以这里取消了序列化
     */
    @Schema(description = "认证信息")
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(UserInfo userInfo, List<String> permissions) {
        this.userInfo = userInfo;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 如果已经存在权限集合，直接返回
        if (ObjUtil.isNotNull(authorities)) {
            return authorities;
        }
        // 如果不存在权限集合，则从权限信息中获取
        return permissions.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userInfo.getUserName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
    
}