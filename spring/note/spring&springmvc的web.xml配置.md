## Web.xml

### spring&spring mvc

在web.xml中定义contextConfigLocation参数，Spring会使用这个参数去加载所有逗号分隔的xml文件，如果没有这个参数，Spring默认加载web-inf/applicationContext.xml文件。

```
    <!-- spring配置 -->
    <!-- Spring加载的xml文件,不配置默认为applicationContext.xml -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/springConfig.xml</param-value>
    </context-param>

    <!--spring mvc配置-->
    <!-- 配置Sping MVC的DispatcherServlet,也可以配置为继承了DispatcherServlet的自定义类,这里配置spring mvc的配置(扫描controller) -->
    <servlet>
        <servlet-name>springmvcservlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- spring MVC的配置文件 -->
        <init-param>
             <param-name>contextConfigLocation</param-name>
             <param-value>/WEB-INF/springmvc.xml</param-value>
        </init-param>
        <!--其他参数-->
        <init-param>
             <param-name>appName</param-name>
             <param-value>authplatform</param-value>
        </init-param>
        <!-- 下面值小一点比较合适，会优先加载 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvcservlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!-- 配置请求过滤器，编码格式设为UTF-8，避免中文乱码 -->
    <filter>
        <filter-name>charsetfilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>charsetfilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 该类作为spring的listener使用，它会在创建时自动查找web.xml配置的applicationContext.xml文件 -->
    <listener>
        <listener-class>
            org.springframework.web.context.ContextLoaderListener
        </listener-class>
    </listener>

    <!-- 此监听器主要用于解决java.beans.Introspector导致的内存泄漏的问题 -->
    <listener>
        <listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>
    </listener>
```

### proxool连接池

```
    <!-- 数据库连接池 -->
    <servlet>
        <servlet-name>proxoolServletConfigurator</servlet-name>
        <servlet-class>
            org.logicalcobwebs.proxool.configuration.ServletConfigurator
        </servlet-class>
        <init-param>
            <param-name>xmlFile</param-name>
            <param-value>WEB-INF/classes/jdbcproxool.xml</param-value>
        </init-param>
        <load-on-startup>2</load-on-startup>
    </servlet>
```

### webAppRootKey

```
<context-param>
    <param-name>webAppRootKey</param-name>
    <param-value>webapp.root</param-value>
</context-param>
```

"webapp.root"这个字符串可以随便写任何字符串。如果不配置默认值是"webapp.root"。
可以用System.getProperty("webapp.root")来动态获项目的运行路径。一般返回结果例如：/usr/local/tomcat6/webapps/项目名。

### log4j

```
    <!-- log4j 配置文件路径及监听器 -->
    <context-param>
        <param-name>log4jConfigLocation</param-name>
        <param-value>classpath:log4j.properties</param-value>
    </context-param>

    <listener>
        <listener-class>
            org.springframework.web.util.Log4jConfigListener
        </listener-class>
    </listener>
```

### 资源文件

spring里面有替代标签resource

```
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.jpg</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.htm</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.html</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.jpeg</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.png</url-pattern>
    </servlet-mapping>
    
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.ico</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.gif</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.js</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.json</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.css</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.bmp</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.swf</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.xml</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.docx</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.doc</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.xls</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.xlsx</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.rar</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.zip</url-pattern>
    </servlet-mapping>
```

## SpringMVC.xml

springmvc的配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans  xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:util="http://www.springframework.org/schema/util"
        xmlns:mvc="http://www.springframework.org/schema/mvc"
        xsi:schemaLocation="http://www.springframework.org/schema/util
                            http://www.springframework.org/schema/util/spring-util.xsd
                            http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/context
                            http://www.springframework.org/schema/context/spring-context.xsd
                            http://www.springframework.org/schema/mvc
                            http://www.springframework.org/schema/mvc/spring-mvc-3.1.xsd">
                            
    <!--spring mvc扫描controller不扫描service-->
    <context:component-scan base-package="com.gmtx">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
    </context:component-scan>
        
    <!-- 异常友好展示 -->
    <bean id="exceptionResolver" class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!-- 默认配置,没有配置的异常或者httpcode返回500,指定异常为ex,前端可以用${ex.message}显示异常信息--> 
        <property name="defaultErrorView" value="errorpages/500"/>
        <property name="defaultStatusCode" value="500"/>
        <property name="exceptionAttribute" value="ex"/>
        <property name="warnLogCategory" value="WARN"/>
        <!-- Exception对应的jsp -->
        <property name="exceptionMappings">
           <props>
              <!-- java.lang.RunTimeException异常返回errorpages/500.jsp页面,其他同理 -->
              <prop key="java.lang.RunTimeException">errorpages/500</prop> 
              <prop key="java.sql.SQLException">errorpages/500</prop>
              <prop key="org.springframework.web.multipart.MaxUploadSizeExceededException">errorpages/upLoadFileError</prop>  
           </props>
        </property>
        <!-- httpcode对应的jsp -->
        <property name="statusCodes">
           <props>
                 <!-- 404对应errorpages/404.jsp -->
                 <prop key="errorpages/404">404</prop>
                 <prop key="errorpages/404">400</prop>
           </props>
        </property>
    </bean>
   
    
     <!-- 静态资源映射,可以配置多个,mapping代表访问url,location代表实际地址 --> 
     <mvc:resources mapping="/images/**" location="/WEB-INF/asset/images/" />
     <mvc:resources mapping="/css/**" location="/WEB-INF/asset/css/" />
     <mvc:resources mapping="/js/**" location="/WEB-INF/asset/js/" />
     <mvc:default-servlet-handler />
    
    <!-- Spring MVC视图解析 -->
    <bean id="defaultViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    
    <!-- Spring MVC文件上传配置 -->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="defaultEncoding" value="utf-8"/>
        <property name="maxUploadSize" value="52428800"/>
        <property name="maxInMemorySize" value="50000"/>
    </bean>
    
   <!-- 注解自动扫描，配置convert，数据验证(不推荐,推荐前端) -->
   <mvc:annotation-driven validator="validation" conversion-service="myConversionService"/>
   
    <!--数据验证用,数据验证推荐在前端验证-->
    <bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
        <property name="defaultEncoding" value="utf-8"/>
        <property name="basename" value="classpath:msg"/>
        <property name="useCodeAsDefaultMessage" value="false"/>
        <property name="cacheSeconds" value="200"/>
    </bean>
    
    <!--数据验证用,数据验证推荐在前端验证-->
    <bean id="validation" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
        <property name="providerClass" value="org.hibernate.validator.HibernateValidator"/>
        <property name="validationMessageSource" ref="messageSource"/>
    </bean>
    
    <!--使用Converter来实现对请求参数的处理，比如字符串去空，日期格式化等-->
    <bean id="myConversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">   
         <property name="converters">
             <list>
                 <bean class="com.gmtx.system.convertre.DateConverter"/>
                 <bean class="com.gmtx.system.convertre.NumberConverter"/>
                 <bean class="com.gmtx.system.convertre.BooleanConverter"/>
                 <bean class="com.gmtx.system.convertre.IntegerConverter"/>
                 <bean class="com.gmtx.system.convertre.FloatConverter"/>
                 <bean class="com.gmtx.system.convertre.DoubleConverter"/>
                 <bean class="com.gmtx.system.convertre.LongConverter"/>
                 <bean class="com.gmtx.system.convertre.ShortConverter"/>
                 <bean class="com.gmtx.system.convertre.CharConverter"/>
                 <bean class="com.gmtx.system.convertre.StringConvert"/>
             </list>
         </property>
     </bean>
    
    <!-- WEB-INF目录下面的JSP页面，是不能直接使用URL访问到。需要通过转发的方式 -->
    <mvc:view-controller path="/" view-name="forward:/login"></mvc:view-controller>
    
     <!-- Spring MVC的拦截器 -->
     <mvc:interceptors>
         <mvc:interceptor>
             <mvc:mapping path="/*/*"/>
             <bean class="实现org.springframework.web.servlet.HandlerInterceptor的类(或其他拦截类)"/>
         </mvc:interceptor>
     </mvc:interceptors>
</beans>
```

## SpingConfig.xml

spring的配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:aop="http://www.springframework.org/schema/aop" 
    xmlns:tx="http://www.springframework.org/schema/tx" 
    xmlns:task="http://www.springframework.org/schema/task"
    xmlns:context="http://www.springframework.org/schema/context" 
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
                        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd 
                        http://www.springframework.org/schema/aop 
                        http://www.springframework.org/schema/aop/spring-aop-3.1.xsd 
                        http://www.springframework.org/schema/tx 
                        http://www.springframework.org/schema/tx/spring-tx-3.1.xsd 
                        http://www.springframework.org/schema/task
                        http://www.springframework.org/schema/task/spring-task-3.1.xsd
                        http://www.springframework.org/schema/context 
                        http://www.springframework.org/schema/context/spring-context-3.1.xsd">
    
    <!-- spring容器扫描 -->
    <context:component-scan base-package="com.gmtx">
    <!-- 不扫描spring mvc的controller -->
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    <!-- bean注解 -->
    <context:annotation-config/>
    
    <!-- 数据库 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="org.logicalcobwebs.proxool.ProxoolDriver"/>
        <property name="url" value="proxool.hjzzAuthPlatform"/>
    </bean>
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    
    <!-- 开启AOP -->
    <aop:aspectj-autoproxy expose-proxy="true"/>
  
    <!-- 事务 -->
    <tx:annotation-driven transaction-manager="txManager"/>
    
    <!-- 定时器 -->
    <task:annotation-driven/>
</beans>
```

 

