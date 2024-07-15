package cn.kun.demo.mq.constants;

/**
 * 路由标识
 *
 * @author 天航星
 * @date 2023-04-19 16:35
 */
public class DemoRoutingConstants {
    
    /**
     * 路由模式1（示例）
     */
    public static final String ROUTE_DEMO_BUSINESS1 = "route.demo.business1";
    
    /**
     * 路由模式2（示例）
     */
    public static final String ROUTE_DEMO_BUSINESS2 = "route.demo.business2";
    
    /**
     * 主题模式（示例）：*代表1个单词，#代表0个或多个单词
     */
    public static final String TOPIC_DEMO = "topic.*.topic.#.topic";

    /**
     * 头部模式-键（示例）
     */
    public static final String HEADERS_DEMO_KEY = "headers.demo.key";

    /**
     * 头部模式-值（示例）
     */
    public static final String HEADERS_DEMO_VALUE = "headers.demo.value";
    
}
