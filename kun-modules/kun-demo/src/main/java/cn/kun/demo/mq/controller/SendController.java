package cn.kun.demo.mq.controller;

import cn.kun.demo.mq.constants.DemoQueueConstants;
import cn.kun.demo.mq.constants.DemoRoutingConstants;
import com.alibaba.fastjson2.JSON;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.mq.constant.ExchangeConstants;
import cn.kun.base.core.mq.service.RabbitService;
import cn.kun.demo.crud.entity.po.Area;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * 发送消息-控制层
 *
 * @author 廖航
 */
@Tag(name = "消息队列")
@RestController
@RequestMapping("send")
public class SendController extends BaseController {
    
    @Autowired
    private RabbitService rabbitService;

    @Operation(summary = "直连模式")
    @PostMapping("direct")
    public BaseResult<Void> direct(@RequestBody Area area) {

        // 推送到AI平台
        rabbitService.send(DemoQueueConstants.DIRECT_DEMO_BUSINESS, area);
        return success();
    }

    @Operation(summary = "工作模式")
    @PostMapping("work")
    public BaseResult<Void> work(@RequestBody Area area) {

        // 推送到AI平台
        rabbitService.send(DemoQueueConstants.WORK_DEMO_BUSINESS, area);
        return success();
    }
    
    @Operation(summary = "路由模式")
    @PostMapping("route")
    public BaseResult<Void> routing(@RequestBody Area area) {
        
        // 推送到AI平台
        rabbitService.send(DemoRoutingConstants.ROUTE_DEMO_BUSINESS1, area);
        // 推送到RBP
        rabbitService.send(DemoRoutingConstants.ROUTE_DEMO_BUSINESS2, area);
        return success();
    }

    @Operation(summary = "主题模式")
    @PostMapping("topic/{routingKey}")
    public BaseResult<Void> topic(@PathVariable String routingKey, @RequestBody Area area) {
        
        // 推送主题消息
        rabbitService.send(ExchangeConstants.TOPIC, routingKey, area);
        return success();
    }

    @Operation(summary = "订阅模式")
    @PostMapping("subscribe")
    public BaseResult<Void> subscribe(@RequestBody Area area) {

        // 推送订阅消息
        rabbitService.send(ExchangeConstants.FANOUT, null, area);
        return success();
    }

    @Operation(summary = "头部模式")
    @PostMapping("headers/{key}/{value}")
    public BaseResult<Void> headers(@PathVariable String key, @PathVariable String value, @RequestBody Area area) {

        // 推送订阅消息
        Message sendMessage = MessageBuilder
                .withBody(JSON.toJSONString(area).getBytes())
                .setHeader(key, value)
                .build();
        rabbitService.send(ExchangeConstants.HEADERS, null, sendMessage);
        return success();
    }
    
    @Operation(summary = "死信队列")
    @PostMapping("dead_letter")
    public BaseResult<Void> deadLetter(@RequestBody Area area) {

        // 推送死信消息
        rabbitService.send(DemoQueueConstants.DIRECT_DEMO_NORMAL, area);
        return success();
    }
    
    @Operation(summary = "获取队列属性")
    @GetMapping("queue/properties/{queueName}")
    public BaseResult<Properties> getQueueProperties(@PathVariable String queueName) {
        
        return success(rabbitService.getQueueProperties(queueName));
    }
    
    @Operation(summary = "获取队列信息")
    @GetMapping("queue/info/{queueName}")
    public BaseResult<QueueInformation> getQueueInfo(@PathVariable String queueName) {
        
        return success(rabbitService.getQueueInfo(queueName));
    }
    
    @Operation(summary = "清空队列")
    @DeleteMapping("queue/clear/{queueName}")
    public BaseResult<Integer> clearQueue(@PathVariable String queueName) {
        
        return success(rabbitService.clearQueue(queueName));
    }
    
    @Operation(summary = "删除队列")
    @DeleteMapping("queue/remove/{queueName}")
    public BaseResult<Boolean> removeQueue(@PathVariable String queueName) {
        
        return success(rabbitService.removeQueue(queueName));
    }

    @Operation(summary = "删除交换机")
    @DeleteMapping("exchange/remove/{exchangeName}")
    public BaseResult<Boolean> removeExchange(@PathVariable String exchangeName) {

        return success(rabbitService.removeExchange(exchangeName));
    }
    
}
