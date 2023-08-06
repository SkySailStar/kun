package cn.kun.base.core.data.wrapper;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义批量更新Wrapper
 * 
 * @author SkySailStar
 * @date 2023-02-28 11:48
 */
public class UpdateBatchWrapper<T> extends AbstractLambdaWrapper<T, UpdateBatchWrapper<T>> {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要更新的字段
     */
    private List<String> updateFields = null;

    @Override
    protected UpdateBatchWrapper<T> instance() {
        this.updateFields = new ArrayList<>();
        return this;
    }

	/**
     * 关键代码,为属性设置值
     */
    @SafeVarargs
    public final UpdateBatchWrapper<T> setUpdateFields(SFunction<T, ?>... columns) {
        this.updateFields = Arrays.asList(columnsToString(columns).split(","));
        return this;
    }

    public List<String> getUpdateFields() {
        return updateFields;
    }
}
