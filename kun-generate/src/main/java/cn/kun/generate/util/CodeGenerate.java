package cn.kun.generate.util;

import cn.kun.base.core.global.entity.BaseEntity;
import cn.kun.generate.constant.GenerateConstants;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

public class CodeGenerate {

    /**
     * 作者名
     */
    public static final String AUTHOR = "SkySailStar";
    
    /**
     * 生成的表名（多个表用英文逗号分隔，所有表则不输入）
     */
    private static final String TABLES = "";

    /**
     * 数据库
     */
    private static final String DATABASE = "kun_auth";

    /**
     * 项目所在目录
     */
    private static final String PROJECT = "D:\\Project\\kun\\kun-cloud";

    /**
     * 模块名
     */
    private static final String MODEL = "kun-modules\\kun-auth";

    /**
     * 包名
     */
    private static final String PACKAGE = "cn.kun.auth";

    /**
     * 业务名
     */
    private static final String BUSINESS = "";
    
    public static void main(String[] args) {
        
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(GenerateConstants.DATABASE_URL + DATABASE + GenerateConstants.DATABASE_ENCODE);
        dataSource.setUsername(GenerateConstants.DATABASE_USERNAME);
        dataSource.setPassword(GenerateConstants.DATABASE_PASSWORD);

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfig();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfig() {
        
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();

        // 注释配置
        globalConfig.getJavadocConfig()
                // 作者
                .setAuthor(AUTHOR);
        
        // 包配置
        globalConfig.getPackageConfig()
                // 包名
                .setBasePackage(PACKAGE + BUSINESS);
        
        // 策略配置
        globalConfig.getStrategyConfig()
                // 生成的数据库
                .setGenerateSchema(DATABASE)
                // 生成的表（未配置时，生成所有表）
                .setGenerateTable(TABLES);

        // Entity配置
        globalConfig.enableEntity()
                // 启用 Lombok
                .setWithLombok(true)
                // 设置父类
                .setSuperClass(BaseEntity.class)
                // Swagger版本配置
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC)
                // 是否覆盖原有文件
                .setOverwriteEnable(true);

        // Mapper配置
        globalConfig.enableMapper();

        // Mapper.xml配置
        globalConfig.enableMapperXml();
        
        // Service配置
        globalConfig.enableService();
        
        // ServiceImpl配置
        globalConfig.enableServiceImpl();
        
        // Controller配置
        globalConfig.enableController()
                // REST风格
                .setRestStyle(true);
        
        return globalConfig;
    }
}