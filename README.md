![](https://oscimg.oschina.net/oscnet/up-0491a51f2c7ed4562ffdd89aebec2e879df.jpg)

# 项目介绍

**kun**（**琨**），美玉。

这是我**爱人**的名字，今天在**相识4周年**、**结婚2周年**之际，以她的名字建立此项目，作为送给她的礼物。

以此名字作为项目名称，还因为玉有以下特点：

- **细腻**。现在市面上优秀的开源项目很多，但特别细致的较少，对新手不太友好，本项目将尽力做得细致，使得：
  - 每个步骤都有说明
  - 每句关键代码都有注释
  - 每个组件都有集成步骤
- **温润**。本项目将极致精简，能够简略的地方绝不冗余，使得各流程顺畅，降低使用门槛。
- **自然**。自然可以衍生万物，本项目的追求也是如此，能够兼容各种业务场景。

**他山之石，可以攻玉**。本项目结合了**实战的工作项目**和**优秀的开源项目**的精华，集纳众家之长，演绎编程之妙。

# 架构图

![架构图](https://oscimg.oschina.net/oscnet/up-282e784a3eeefcb58913290a6080d60f.png)

> ~~划线~~代表**目前未实现**，**后面会实现**。

# 主要功能

- 认证授权
- 代码生成
- 多数据源
- 远程调用
- 文件处理
- 负载均衡
- 定时任务
- ~~集群部署~~
- ~~网关~~
- ~~流程管控~~
- ~~链路追踪~~
- ~~服务监控~~
- ~~分库分表~~
- ~~流量管控~~
- ~~分布式事务~~
- ~~分布式日志~~
- ~~数据处理~~
- ~~数据同步~~
- ~~国际化~~

# 技术选型

- **系统环境**
  - **Java 环境**：OracleJDK 17.0.5
  - **关系型数据库**：MySQL 8.0
  - **非关系型数据库**：Redis、~~MongDB~~、~~Elasticsearch~~
  - **非结构化数据存储**：MinIO
  - **项目管理**：Maven
- **主框架**：Spring Boot 3.1.0
- **微服务框架**：Spring Cloud Alibaba 2021.0.4.0
  - **分布式配置 服务注册与发现**：Nacos
  - **服务熔断**：~~Sentinel~~
  - **服务调用 负载均衡**：Spring Cloud OpenFeign
  - **服务路由**：~~Spring Cloud Gateway~~
  - **分布式消息**：Spring Cloud Stream RabbitMQ
  - **分布式事务**：~~Seata~~
- **安全框架**：Spring Security
- **定时任务框架**：xxl-job
- **编码工具**
  - **持久层 代码生成**：MyBatis-Flex
  - **多数据源**：Druid
  - **外部服务调用**：~~Dubbo~~
  - **接口文档**：Swagger 3 + Knife4j
  - **Json 处理**：FastJson 2
  - **文档处理**：~~POI~~、FreeMarker
  - **类构建 日志打印**：Lombok
  - **工具类**：Hutool
- **工作流**：~~Activiti~~
- **服务监控**：~~Spring Boot Admin~~
- **链路追踪**：~~SkyWalking~~
- **分库分表**：~~Sharding-JDBC~~
- **分布式日志**：~~ELK（Filebeat+Kafka+Elasticsearch+Logstash+Kibana）~~
- **服务部署**：Docker、~~K8s~~、~~Jenkins~~
- **反向代理 服务器端负载均衡**：Nginx
- **数据处理 数据同步**：~~NIFI~~
- **大数据**：~~Hadoop~~

# 项目结构

```
kun
├── kun-base                    // 公用模块
│      └── kun-base-api         // 公用模块-接口
│      └── kun-base-core        // 公用模块-核心（全局、缓存、数据、文件、安全）
│      └── kun-base-job         // 公用模块-定时任务
├── kun-generator               // 代码生成模块
├── kun-modules                 // 业务模块
│      └── kun-demo             // 示例服务[45000]
│      └── kun-auth             // 认证服务[45001]
├── .gitignore                  // Git忽略文件
├── pom.xml                     // 公共依赖
├── README.md                   // 项目说明文件
```