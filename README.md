tinyWeb
=======

A tiny framework of Java For Web devlopment.

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

