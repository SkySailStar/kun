package cn.kun.base.core.global.util.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.kun.base.core.global.config.GlobalConfig;

/**
 * 主键工具类
 *
 * @author lujun
 */
public class IdHelp {

    /**
     * 获取雪花算法ID
     * @return 雪花算法ID
     */
    public static long getSnowflakeId(){
        Snowflake snowflake = IdUtil.getSnowflake(GlobalConfig.getMachineCode());
        return snowflake.nextId();
    }
}
