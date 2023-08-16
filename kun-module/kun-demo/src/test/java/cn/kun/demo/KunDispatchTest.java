package cn.kun.demo;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.base.api.service.dispatch.RemoteTaskService;
import org.junit.jupiter.api.Test;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 调度服务-测试类
 *
 * @author SkySailStar
 * @date 2023-06-07 10:38
 */
@SpringBootTest
public class KunDispatchTest {
    
    @Resource
    private RemoteTaskService remoteTaskService;
    
    @Test
    public void pageTask() {

        TaskPageDTO dto = new TaskPageDTO();
        Page<TaskPageVO> page = remoteTaskService.page(dto).getData();
        for (TaskPageVO record : page.getRecords()) {
            System.out.println(JSON.toJSONString(record));
        }
    }
    
}
