# tinyWeb
=======

A tiny framework of Java For Web devlopment.

## 应用类型
1. 最简单的应用，基本是对于数据库的CRUD，占90%以上，很少用到事务
2. 略微复杂点的应用，大部分CRUD，25%左右的业务逻辑，需要事务支持
3. 企业级应用，75%含有业务逻辑，25%以下的CRUD，大量用到事务
4. 互联网应用，需要服务化

## 解决方案
1. Controller + Spring事务注解 + Ebean
2. Controller + 部分Biz层 + Spring事务注解 + Ebean
3. DDD
4. RPC

## 技术选型
### 后端
* 核心框架：Spring Framework
* MVC框架：Spring MVC
* 持久层框架：Ebean
### 前端
* JS框架：jQuery
* CSS框架：Bootstrap AdminLTE

## 计划
* 自动打包
* 单元测试
* 开发不用重启

## 使用方法
Controller使用方法：
在web.xml中配置com.tinyweb.mvc.Dispatcher扫描的包名：
<servlet>
	<servlet-name>dispatcher</servlet-name>
	<servlet-class>com.tinyweb.mvc.Dispatcher</servlet-class>
	<init-param>
		<param-name>scanPackage</param-name>
		<param-value>com.sample.controllers</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>dispatcher</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>

写一个Controller类，继承com.tinyweb.mvc.Controller类
其中类名后缀必须是_Controller或Controller，action为所有的public方法
如MemberRelation_Controller有个叫addMembers的方法，则映射的路径为member_relation/add_members


未来支持：
扫描多个包，支持通配符
路径可配置
视图路径支持绝对路径


存在问题：
对象转成JSON时，循环依赖的问题
