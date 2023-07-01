package cn.kun.base.core.data.config;

import cn.kun.base.core.data.enums.CustomSqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import cn.kun.base.core.data.method.UpdateBatchById;

import java.util.List;

/**
 * 自定义方法注入
 * 
 * @author 廖航
 * @date 2023-02-28 11:53
 */
public class CustomSqlInjector extends DefaultSqlInjector {
    
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加批量修改方法
        methodList.add(new UpdateBatchById(CustomSqlMethod.UPDATE_BATCH_BY_ID.getMethod()));
        return methodList;
    }
}
