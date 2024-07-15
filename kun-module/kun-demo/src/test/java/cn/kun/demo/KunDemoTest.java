package cn.kun.demo;

import cn.kun.demo.crud.service.DictTypeService;
import cn.kun.base.api.service.system.RemoteAreaService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.junit.jupiter.api.Test;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * 测试类
 *
 * @author 天航星
 * @date 2023-03-10 17:29
 */
@SpringBootTest
public class KunDemoTest {
    
    @Resource
    private DictTypeService dictTypeService;
    
    @Resource
    private RemoteAreaService remoteAreaService;
    
    /**
     * 普通测试
     */
    @Test
    public void test1() {
        System.out.println(dictTypeService.list());
    }

    /**
     * 远程服务测试
     */
    @Test
    public void test2() {
        System.out.println(remoteAreaService.getNameById(100000L).getData());
    }
    
}
