package cn.kun.base.core.global.config;

import cn.kun.base.core.global.util.id.IdHelp;
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