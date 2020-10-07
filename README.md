# 项目介绍
本项目是一个综合课设管理系统，使用前后端分离开发。主要采用 Vue.js 和 SpringBoot 技术栈开发。
# 主要技术栈
## 前端
1. Vue.js
## 后端
1. SpringBoot
2. Mybatis
3. Mybatis Generator
4. Swagger2
## 数据库
1. Mysql 8.0.19
# 项目部署
1. JDK版本为 1.8
2. 数据库版本为 8.0.19 
    * 目前数据库部署在本地端，需要创建一个 CCMS 的数据库，然后执行文件里面的 db.sql 语句，根据自己的数据库修改 yml 文件.
    * 数据库这边使用了Mybatis Generator 自动代码生成，后续可以考虑一下Mybatis Plus.
3. 使用 spring-boot-starter-swagger 生成 API 文档
# ToDo List
1. 详细的需求分析
2. 数据库表设计
3. 功能点明细