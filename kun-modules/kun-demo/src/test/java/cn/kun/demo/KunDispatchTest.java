package cn.kun.demo;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.base.api.service.dispatch.RemoteTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 调度服务-测试类
 *
 * @author 廖航
 * @date 2023-06-07 10:38
 */
@SpringBootTest
public class KunDispatchTest {
    
    @Autowired
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
