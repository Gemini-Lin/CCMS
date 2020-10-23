# 项目介绍
本项目是一个综合课设管理系统，使用前后端分离开发。主要采用 Vue.js 和 SpringBoot 技术栈开发。
# 主要技术栈
## 前端
1. Vue.js
## 后端
1. SpringBoot
2. Mybatis Plus
3. Swagger2
4. Shrio
5. Redis
## 数据库
1. Mysql 8.0.19
# 项目部署
1. JDK版本为 1.8
2. 数据库版本为 8.0.19 
    * 目前数据库部署在本地端，需要创建一个 CCMS 的数据库，然后执行文件里面的 db.sql 语句，根据自己的数据库修改 yml 文件.
    * 数据库这边使用了Mybatis Generator 自动代码生成，后续可以考虑一下Mybatis Plus.(已更改)
    * 可以使用 Navicat 进行库表管理
3. 使用 spring-boot-starter-swagger 生成 API 文档 (url: http://localhost:8080/swagger-ui.html)
# ToDo List
1. 详细的需求分析
2. 数据库表设计
    * 模块设计---每个模块的功能点
    * 分析表的个数？
    * 分析表之间的关联关系？
    * 分析表中的字段
3. 编码
    * 项目环境的搭建
    * 功能点模块编码
# 功能模块图

![image-20201023200702232](https://i.loli.net/2020/10/23/do1lAtILqyYDp9K.png)

![image-20201023200754464](https://i.loli.net/2020/10/23/5NnYDtMOxbfdpRV.png)

# 项目开发进展
1. 完成基本 SpringBoot Mybatis 项目环境搭建 2020-10-07 林俊
2. 技术栈更新                             2020-10-08 林俊
    * 将 Mybatis 更改为 Mybatis Plus 代码自动生成更加方便，后续待完善分页处理和多表处理
    * 使用开源项目 spring-boot-starter-swagger 生成了正式的API文档，持续更新中
    * 引入了 Lombok 插件，大量简化代码编写
    * 导入 shiro-redis-spring-boot-starter 开源项目Jar包 
        1. 加入 Shiro 安全框架，方便后续处理身份认证、授权、加密以及会话管理
        2. 加入 Redis 缓存数据
    * 加入 JSON Web Token 用于认证过程
3. 功能点实现1.0                              2020-10-08 林俊
    * 编写 AccountController 类，完成用户登录和退出模块
    * 编写 UserController 类，实现图片验证码获取
    * 编写 Result 类，用于异步统一返回的结果封装  
    * 编写 GlobalExceptionHandler 类，用于项目异常处理
    * 将数据传输中的 password 字段用 md5 加密(Ps：目前遇到点问题未解决)
4. 功能点实现2.0                                 2020-10-19 林俊
    * 根据数据库设计，新增了六个是实体类
    * 解决了 password 加密传输问题
    * 编写 CorsConfig 
5. 功能点实现3.0                               2020-10-23 林俊、林燕玲
    * 完成了数据库的 mapper、Service、Impl 的自动生成
    * 明确了根据实际需求，开发面向两个角色的功能，学生和教师的使用场景不同（类似于美团的用户版和店家版）
    * 实现了功能迁移，编写完成 Teacher、Student 的登录和退出模块