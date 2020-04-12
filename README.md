# 编码不易，加星鼓励！
# Coding is hard, star to my heart ！

***
# vehicle-shop-admin note
a mirco-service framework admin system for vehicle shop

自己业余全栈开发的一个汽修店铺系统，前后分离，不为功能完善酷炫，只为技术整合。

演示地址：http://47.115.127.19:8088/  账号密码请联系我

安卓APP下载地址：https://biao-aliyun-oss-pic-bucket.oss-cn-shenzhen.aliyuncs.com/0412105148.apk

## Framework架构图:

![Image text](https://github.com/xiexiaobiao/vehicle-shop-admin/blob/master/devDocument/framework.PNG)

***

## 后端技术列表

| 名称      | 组件     | 用途     |进度     | 
| ---------- | :-----------  | :----------- | :----------- |
| SpringbootMVC     | 微服务     | 微服务开发    | 已整合
| Dubbo     | RPC     | 微服务架构，服务间调用     | 已整合
| Zookeeper     | 注册中心     | 微服务进行注册     | 已整合
| Nacos     | Config     | 微服务配置中心     | 已整合
| Nginx     | 反向代理     | 统一系统访问入口；对网关负载均衡；系统前端静态服务器     |已整合
| Soul     | 网关     | 路由转发；HTTP访问负载均衡     |已整合
| JWT     | Token     | 登录认证     |
| Shiro     | 鉴权     | 系统API访问权限控制     |
| Seata     | RPC     | 分布式事务方案     |
| Sentinel     | 限流     | 服务访问限流降级     |
| Redis     | 缓存     | 数据库访问缓存；session缓存     |已整合
| RocketMQ     | MQ     | 服务间通信；分布式事务；     |已整合
| Kafka     | MQ     | 日志传输     |
| ElasticSearch     | DB     | 搜索；推荐；日志存储     |
| Logstash     | 日志     | 收集系统日志     |
| Kibana     | 日志     | 日志展现     |
| Druid     | JDBC     | 数据库连接池     |已整合
| Mybatis+     | ORM     | 实体映射和DAO     |已整合
| Lombok     | POJO     | 实体类简化处理和log     |已整合
| Logback     | 日志     | 日志类生成     |已整合
| Mango     | DB     | 文件存储服务器     |
| OSS     | OSS     | 在线文件存储     |已整合
| Mysql     | DB     | 业务数据存储     |已整合
| Junit     | Test     | 单元测试     |已整合
| Pagehelper     | pagination     | 数据分页查询     |已整合
| FineReport     | Report     | 系统报表功能     |已整合

***

## 工具类

| 名称      | 组件     | 用途     |
| ---------- | :-----------  | :----------- |
| Gradle     | Build     | 代码编译     |
| Idea     | IDE     | 编码；HTTP测试     |
| JDK11     | JDK     | 本地运行环境     |
| Git     | VCS     | 代码版本管理     |

***
## 前端技术列表

| 名称      | 组件     | 用途     |
| ---------- | :-----------  | :----------- |
| Vue.js     | JS     | 前端开发框架    |
| ElementUI     | UI     | UI界面组件    |