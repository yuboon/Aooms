
<p align="center">
    <img src="https://images.gitee.com/uploads/images/2019/0312/213325_af8c5db2_385692.png" width="300">
    <br>      
    <p align="center">
        <b>Aooms —— 极速微服务开发平台</b>
        <br> 
        <br>
        <a href="#">
            <img src="https://img.shields.io/badge/springboot-2.0.1-green.svg" >
        </a>  
        <a href="#">
            <img src="https://img.shields.io/badge/springcloud-Finchley.RELEASE-blue.svg">
        </a>  
        <a href="#">
            <img src="https://img.shields.io/badge/D2admin%20-1.4.5-red.svg">
        </a>
        <a href="#">
            <img src="https://img.shields.io/badge/hutool--all-4.0.9-brightgreen.svg">
        </a>  
        <a href="#">
            <img src="https://img.shields.io/badge/mybatis-3.4.6-orange.svg">
        </a>
        <br> 
        <a href="#">
            <img src="https://img.shields.io/badge/fastjson-1.2.47-yellow.svg">
        </a>    
        <a href="#">
            <img src="https://img.shields.io/badge/j2cache-2.7.2-red.svg">
        </a>   
        <a href="#">
            <img src="https://img.shields.io/badge/sharding--jdbc-3.0.0.M2-brightgreen.svg">
        </a>   
        <a href="#">
            <img src="https://img.shields.io/badge/jsoup-1.11.3-blue.svg">
        </a> 
    </p>
</p>

------

### 简介

Aooms是基于SpringCloud生态的微服务开发平台，不止于简单的框架集成。

 - 组件轻薄封装
 - 极速开发体验
 - 完整解决方案

 **[ Aooms-在线体验 ] <a href="https://www.yuboon.com/Aooms/" target="_blank">(https://www.yuboon.com/Aooms/)</a>** 

-----------------------------------------------------------------------------------------------

### 工程结构

 **- aooms-core**

> 框架核心包，其他工程均依赖此包，核心特性如下


- 极简Controller
- 基于sharding-sphere的多数据源、分库分表支持
- 基于Mybatis 实现的 Db + Record 极简模式，附带物理分页实现
- 基于Consul的服务注册、发现
- 服务熔断、限流、降级
- 服务客户端、http客户端
- 内置各种ID生成器（UUID、snowflake）
- 穿透一切的数据对象DataBoss
- 基于J2Cache的缓存
- 分布式锁
- 分布式事物
- 服务链路监控

更多...........
<br>
<br>

 **- aooms-rbac**

> 后台权限管理系统，包含如下功能

- 机构管理
- 角色管理
- 用户管理
- 资源管理
- 日志管理


<img src="https://images.gitee.com/uploads/images/2018/1116/130232_92a39175_385692.png" width="50%" /><img src="https://images.gitee.com/uploads/images/2018/1116/130245_916e85f4_385692.png" width="50%"  />

后台管理界面基于D2admin

<a href="https://github.com/d2-projects/d2-admin" target="_blank"><img src="https://images.gitee.com/uploads/images/2019/0218/203814_d8f924a2_385692.png" width="200"></a>
<br>
<br>

 **- aooms-community（功能规划中）**
> 技术小社群系统，微服务实战项目。

<br>
-----------------------------------------------------------------------------------------------

### 代码示例


[简单代码示例查看
](https://gitee.com/cyb-javaer/Aooms/blob/master/docs/Example.md)

-----------------------------------------------------------------------------------------------

### 快速开始

1. 安装npm
2. 导入工程
3. 导入数据库脚本
4. 启动前端
5. 启动quick-start

-----------------------------------------------------------------------------------------------

### 作者寄语

该项目定位是学习性质的摸索、尝试，一个造轮子的过程，给想学习微服务的技术人一些学习上的帮助同时也是作者自我提升、总结的过程，参与、学习该项目你或许可以得到以下几方面的提升：


- 项目工程中命名规范和标准，包括数据库、代码等各个层面
- SpringCloud及其它相关主流技术组件的使用
- 掌握一些基本的封装思想和实用编码技巧
- 部分设计模式在项目中的具体应用
- 微服务架构中的典型技术问题解决方案经验
- 完整项目和全栈技能的微服务开发经验

> 暂无建群计划，联系作者可扫下方二维码，添加时请备注：Aooms技术交流

<img src="https://images.gitee.com/uploads/images/2019/0523/111229_7ce5046c_385692.png" width="150" height="150" />
