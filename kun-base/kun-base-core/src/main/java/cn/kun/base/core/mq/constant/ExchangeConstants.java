package cn.kun.base.core.mq.constant;

/**
 * 交换机
 *
 * @author 天航星
 * @date 2023-04-19 14:49
 */
public class ExchangeConstants {

    /**
     * 默认直连交换机
     */
    public static final String DIRECT = "amq.direct";

    /**
     * 默认主题交换机
     */
    public static final String TOPIC = "amq.topic";
    
    /**
     * 默认扇出交换机
     */
    public static final String FANOUT = "amq.fanout";
    
    /**
     * 默认头部交换机
     */
    public static final String HEADERS = "amq.headers";

    /**
     * 死信交换机
     */
    public static final String DEAD_LETTER = "dl.direct";
    
}
