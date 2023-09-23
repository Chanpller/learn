# 第14章-Session

## 14.1 什么是Session

1、Session 就一个接口（HttpSession）。

2、Session 就是会话。它是用来维护一个客户端和服务器之间关联的一种技术。

3、每个客户端都有自己的一个Session 会话。

4、Session 会话中，我们经常用来保存用户登录之后的信息。

## 14.2 创建Session和获取

* request.getSession()
  * 第一次调用是：创建Session 会话
  * 之后调用都是：获取前面创建好的Session 会话对象。
* isNew(); 判断到底是不是刚创建出来的（新的）
  * true 表示刚创建
  * false 表示获取之前创建
* 每个会话都有一个身份证号。也就是ID 值。而且这个ID 是唯一的。getId() 得到Session 的会话id 值。

## 14.3 Session 域数据的存取

* Session .setAttribute("key", "value");设置值
* 通过request.getSession().getAttribute("key")获取值

## 14.4 Session 生命周期控制

* setMaxInactiveInterval(int interval)设置Session 的超时时间（以秒为单位），超过指定的时长，Session就会被销毁。

  * 值为正数的时候，设定Session 的超时时长，单位为秒
  * 负数表示永不超时（极少使用），不会这样设置，因为服务器的Session会越来越多，最后崩溃掉。

* getMaxInactiveInterval()获取Session 的超时时间

* invalidate() 让当前Session 会话马上超时无效。

* Session 默认的超时时长是多少

  * Session 默认的超时时间长为30 分钟。在Tomcat 服务器的配置文件web.xml 中默认有以下的配置

    ```xml
    <session-config>
    	<session-timeout>30</session-timeout>
    </session-config>
    ```

  * 可以在自己的工程中的web.xml配置session-timeout，session就以你的为准。

* Session的超时是连续多少时间没有调用、请求时，服务器清理掉Session。而不是生成时间超过多少时间后清理掉。

## 14.5 Session保持原理

* Session 技术，底层其实是基于Cookie 技术来实现的。
  * 浏览器第一次请求时Cookie中没有带JSESSIONID，服务器就创建一个Session，并将Session的ID写回到Cookie中的JSESSIONID。下次请求时Cookie就携带上了JSESSIONID，服务器根据ID找到Session，从而实现了会话保持。
  * 客户端清理掉了Cookie就找不到原来的Session了。