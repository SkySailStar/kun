package cn.kun.generate.util;

import cn.kun.base.core.global.util.str.StrHelp;
import cn.kun.generate.constant.GenerateConstants;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashSet;
import java.util.Set;

public class CodeGenerate {

    /**
     * 作者名
     */
    public static final String AUTHOR = "SkySailStar";
    
    /**
     * 生成的表名（多个表用英文逗号分隔，所有表输入 all）
     */
    private static final String TABLES = "warn_level";

    /**
     * 数据库
     */
    private static final String DATABASE = "rmp";

    /**
     * 项目所在目录
     */
    private static final String PROJECT = "D:\\Project\\kun\\kun-cloud";

    /**
     * 模块名
     */
    private static final String MODEL = "kun-modules\\kun-app";

    /**
     * 包名
     */
    private static final String PACKAGE = "cn.skysailstar.app";

    /**
     * 业务名
     */
    private static final String BUSINESS = "warn";
    
    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(GenerateConstants.DATABASE_URL + DATABASE + GenerateConstants.DATABASE_ENCODE);
        dataSource.setUsername(GenerateConstants.DATABASE_USERNAME);
        dataSource.setPassword(GenerateConstants.DATABASE_PASSWORD);

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfig();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfig() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage(PACKAGE);

        //设置生成哪些表
        if (StrHelp.equals(TABLES, "all")) {
            globalConfig.setGenerateTable(DATABASE);
        } else {
            globalConfig.setGenerateTable(TABLES.split(","));
        }
        
        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);

        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}