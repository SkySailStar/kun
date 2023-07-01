package cn.kun.base.core.data.method;

import cn.kun.base.core.data.enums.CustomSqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import java.io.Serial;
import java.util.List;

/**
 * 通过ID批量更新
 * 
 * @author 廖航
 * @date 2023-02-28 11:44
 */
public class UpdateBatchById extends AbstractMethod {
    
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    public UpdateBatchById(String methodName) {
        super(methodName);
    }


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        CustomSqlMethod sqlMethod = CustomSqlMethod.UPDATE_BATCH_BY_ID;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlSet(tableInfo), tableInfo.getKeyColumn(), this.sqlIn(tableInfo.getKeyProperty()));
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }

    private String sqlSet(TableInfo tableInfo) {
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        StringBuilder sb = new StringBuilder();

        for (TableFieldInfo fieldInfo : fieldList) {
            sb.append("<if test=\"ew.updateFields.contains(&quot;").append(fieldInfo.getColumn()).append("&quot;)\">")
                    .append(fieldInfo.getColumn()).append(" =\n")
                    .append("CASE ").append(tableInfo.getKeyColumn()).append("\n")
                    .append("<foreach collection=\"list\" item=\"et\" >\n")
                    .append("WHEN #{et.").append(tableInfo.getKeyProperty()).append("} THEN #{et.").append(fieldInfo.getProperty()).append("}\n")
                    .append("</foreach>\n").append("END ,\n")
                    .append("</if>\n");

        }
        return "<set>\n" + sb + "</set>";
    }

    private String sqlIn(String keyProperty) {

        return "<foreach collection=\"list\" item=\"et\" separator=\",\" open=\"(\" close=\")\">\n" +
                "#{et." + keyProperty + "}" +
                "</foreach>\n";
    }
}
