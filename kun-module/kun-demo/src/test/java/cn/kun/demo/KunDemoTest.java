package cn.kun.demo;

import cn.kun.base.api.service.auth.LoginService;
import cn.kun.base.core.security.entity.dto.LoginDTO;
import cn.kun.base.api.service.system.DictService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类
 *
 * @author 天航星
 * @date 2023-03-10 17:29
 */
@SpringBootTest
public class KunDemoTest {
    
    @DubboReference
    private LoginService loginService;

    @DubboReference
    private DictService dictService;
    
    /**
     * 登录测试
     */
    @Test
    public void login() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLoginName("");
        loginDTO.setPassword("");
        System.out.println(loginService.login(loginDTO));
    }

    /**
     * 远程服务测试
     */
    @Test
    public void test2() {
        System.out.println(dictService.getLabel("", ""));
    }
    
}
