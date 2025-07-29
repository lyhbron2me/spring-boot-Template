# Spring Boot快速使用框架
组件介绍

| 组件名称 | 版本号                        | 说明 |
|----------|----------------------------|------|
| **Spring Boot** | 3.5.3                      | 项目核心框架，提供自动配置和起步依赖 |
| **Java** | 17                         | 运行环境和编译版本 |
| **MySQL** | 8.0.0                      |  MySQL 数据库 |
| **Spring Security** | 3.5.3                      | 提供身份验证和授权功能 |
| **MyBatis-Plus** | 3.5.7                      | 增强型 MyBatis 框架，简化数据库操作 |
| **Lombok** | 1.18.x                     | 简化 Java Bean 开发 |
| **Redis** | 3.5.3                      | 提供缓存支持 |

项目特点
----------
- 快速启动模板
- 集成 Spring Security 实现基础安全控制
- 支持 Redis 数据访问
- 支持 MySQL 数据库连接

快速开始
----------
1. 确保已安装 Java 17 和 Maven 3.x
2. 克隆项目到本地：`git clone <项目地址>`
3. 进入项目目录：`cd lyh-tetmplate-project`
4. 构建项目：`mvn clean package`
5. 启动项目：`java -jar target/lyh-tetmplate-project-0.0.1-SNAPSHOT.jar`

项目结构
----------
项目采用标准的 Spring Boot 目录结构，主要目录说明如下：

- `src/main/java`：Java 源代码目录
- `src/main/resources`：资源文件目录，包含配置文件和 MyBatis 映射文件
- `src/test/java`：测试代码目录
- `db`：数据库相关文件目录
- `pom.xml`：Maven 项目配置文件

贡献指南
----------
欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建新分支：`git checkout -b feature/new-feature`
3. 提交更改：`git commit -am 'Add new feature'`
4. 推送分支：`git push origin feature/new-feature`
5. 提交 Pull Request




git文件坏了！！！！
