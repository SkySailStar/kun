package cn.kun.base.core.data.config;

import cn.kun.base.core.data.util.IdHelp;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 自定义主键生成
 *
 * @author 廖航
 */
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        return IdHelp.getSnowflakeId();
    }
}