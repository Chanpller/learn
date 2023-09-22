1、引入jar包

		<dependency org="org.springframework" name="spring-web" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-bean" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-core" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-webmvc" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-aop" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-context" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-expression" rev="5.0.5.RELEASE" />
			<dependency org="org.springframework" name="spring-tx" rev="5.0.5.RELEASE" />
			<!--AOP需要-->
			<dependency org="org.aspectj" name="aspectjweaver" rev="1.8.9" />
			<!--开启文件上传需要-->
			<dependency org="apache" name="commons-fileupload" rev="1.2">
			<!--日志需要，必须的-->
			<dependency org="apache" name="commons-logging" rev="1.0.3">
2、web.xml中添加

```
<servlet>
   		<servlet-name>springmvc</servlet-name>
   		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
   		<init-param>
   			<param-name>contextConfigLocation</param-name>
   			<param-value>/WEB-INF/spring-mvc.xml</param-value>
   		</init-param>
   	</servlet>
   	<servlet-mapping>
   		<servlet-name>springmvc</servlet-name>
   		<url-pattern>/</url-pattern>
   	</servlet-mapping>
```

或者 按照官网配置也可以



3、spring-mvc.xml

```
<?xml version="1.0" encoding="GB2312" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 包扫描路径  -->
    <context:component-scan base-package="com.pingan.www.imadmin"/>
    <!-- servlet处理器 -->
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    	    	<property name="prefix" value="/jsps/" />
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

4、RestController与Controller区别，RestController是Controller+ResponseBody

