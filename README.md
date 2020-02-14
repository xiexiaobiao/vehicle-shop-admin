# vehicle-shop-admin
a mirco-service framework admin system for vehicle shop

## Framework架构:
![Image text](https://github.com/xiexiaobiao/vehicle-shop-admin/blob/master/resource/framework.PNG)

***

## 后端技术列表

| 名称      | 组件     | 用途     |
| ---------- | :-----------  | :----------- |
| SpringbootMVC     | 微服务     | 微服务开发    |
| Dubbo     | RPC     | 微服务架构，服务间调用     |
| Zookeeper     | 注册中心     | 微服务进行注册     |
| Nacos     | Config     | 微服务配置中心     |
| Nginx     | 反向代理     | 统一系统访问入口；对网关负载均衡；系统前端静态服务器     |
| Soul     | 网关     | 路由转发；HTTP访问负载均衡     |
| JWT     | Token     | 登录认证     |
| Shiro     | 鉴权     | 系统API访问权限控制     |
| Seata     | RPC     | 分布式事务方案     |
| Sentinel     | 限流     | 服务访问限流降级     |
| Redis     | 缓存     | 数据库访问缓存；session缓存     |
| RocketMQ     | MQ     | 服务间通信     |
| Kafka     | MQ     | 日志传输     |
| ElasticSearch     | DB     | 搜索；推荐；日志存储     |
| Logstash     | 日志     | 收集系统日志     |
| Kibna     | 日志     | 日志展现     |
| Druid     | JDBC     | 数据库连接池     |
| Mybatis+     | ORM     | 实体映射和DAO     |
| Lombok     | POJO     | 实体类简化处理和log     |
| Logback     | 日志     | 日志类生成     |
| Mango     | DB     | 文件存储服务器     |
| Mysql     | DB     | 业务数据存储     |
| Junit     | Test     | 单元测试     |
| Pagehelper     | pagination     | 数据分页查询     |
| FineReport     | Report     | 系统报表功能     |

***

## 工具类

| 名称      | 组件     | 用途     |
| ---------- | :-----------  | :----------- |
| Gradle     | Build     | 代码编译     |
| Idea     | IDE     | 编码；HTTP测试     |

***
## 前端技术列表

| 名称      | 组件     | 用途     |
| ---------- | :-----------  | :----------- |
| Vue.js     | JS     | 前端开发框架    |
| ElementUI     | UI     | UI界面组件    |