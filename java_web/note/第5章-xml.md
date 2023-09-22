# 第5章-xml

## 5.1XML简介

* xml 是可扩展的标记性语言

## 5.2 xml 的作用

- 用来保存数据，而且这些数据具有自我描述性
- 它还可以做为项目或者模块的配置文件
- 还可以做为网络传输数据的格式（现在 JSON 为主）。

## 5.3 xml 语法

- 文档声明。
- xml 注释
- 元素（标签）
- xml 属性
- 文本区域（CDATA 区）

### 5.3.1 文档声明

```
<?xml version=*"1.0"* encoding=*"UTF-8"?>` xml 声明。

<!-- xml 声明 version 是版本的意思 encoding 是编码 -->

而且这个<?xml 要连在一起写，否则会有报错
```

**属性**

- version 是版本号
- encoding 是 xml 的文件编码
- standalone="yes/no" 表示这个 xml 文件是否是独立的 xml 文件

### 5.3.2 xml 注释

* html 和 XML 注释 一样 : <!-- html 注释 -->

### 5.3.3 元素（标签）

* 元素是指从开始标签到结束标签的内容。

  ​	例如：<title>java 编程思想</title>

* 元素 我们可以简单的理解为是 标签。

* Element 翻译为元素

**XML 命名规则**

* 名称可以含字母、数字以及其他的字符
* 名称不能以数字或者标点符号开始
* 名称不能包含空格

**xml 中的元素（标签）也 分成 单标签和双标签：**

* 单标签格式： <标签名 属性=”值” 属性=”值” ...... />

* 双标签格式：< 标签名 属性=”值” 属性=”值” ......>文本数据或子标签</标签名>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- xml 声明 version 是版本的意思 encoding 是编码 -->
<books> <!-- 这是 xml 注释 -->
    <book id="SN123123413241"> <!-- book 标签描述一本图书 id 属性描述 的是图书 的编号 -->
        <name>java 编程思想</name> <!-- name 标签描述 的是图书 的信息 -->
        <author>华仔</author> <!-- author 单词是作者的意思 ，描述图书作者 -->
        <price>9.9</price> <!-- price 单词是价格，描述的是图书 的价格 -->
    </book>
    <book id="SN12341235123"> <!-- book 标签描述一本图书 id 属性描述 的是图书 的编号 -->
        <name>葵花宝典</name> <!-- name 标签描述 的是图书 的信息 -->
        <author>班长</author> <!-- author 单词是作者的意思 ，描述图书作者 -->
        <price>5.5</price><!-- price 单词是价格，描述的是图书 的价格 -->
    </book>
</books>
```

### 5.3.4 xml 属性

* xml 的标签属性和 html 的标签属性是非常类似的，**属性可以提供元素的额外信息**

* 在标签上可以书写属性：
  * 一个标签上可以书写多个属性。**每个属性的值必须使用 引号 引起来**。
  * 属性的规则和标签的书写规则一致。

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!-- xml声明 version是版本的意思   encoding是编码  -->
<books> <!-- 这是xml注释 -->
    <book id="SN123123413241"> <!-- book标签描述一本图书   id属性描述 的是图书 的编号  -->
        <name>java编程思想</name> <!-- name标签描述 的是图书 的信息 -->
        <author>华仔</author>		<!-- author单词是作者的意思 ，描述图书作者 -->
        <price>9.9</price>		<!-- price单词是价格，描述的是图书 的价格 -->
    </book>
    <book id="SN12341235123">	<!-- book标签描述一本图书   id属性描述 的是图书 的编号  -->
        <name>葵花宝典</name>	<!-- name标签描述 的是图书 的信息 -->
        <author>班长</author>	<!-- author单词是作者的意思 ，描述图书作者 -->
        <price>5.5</price>	<!-- price单词是价格，描述的是图书 的价格 -->
    </book>
</books>
```

### 5.3.5 文本区域（CDATA 区）

* CDATA 语法可以告诉 xml 解析器，我 CDATA 里的文本内容，只是纯文本，不需要 xml 语法解析

*  CDATA 格式：

```
<![CDATA[ 这里可以把你输入的字符原样显示，不会解析 xml ]]>
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!-- xml声明 version是版本的意思   encoding是编码  -->
<books> <!-- 这是xml注释 -->
    <book id="SN123123413241"> <!-- book标签描述一本图书   id属性描述 的是图书 的编号  -->
        <name>java编程思想</name> <!-- name标签描述 的是图书 的信息 -->
        <author><![CDATA[<<<>>>华仔]]></author>		<!-- author单词是作者的意思 ，描述图书作者 -->
        <price>9.9</price>		<!-- price单词是价格，描述的是图书 的价格 -->
    </book>
    <book id="SN12341235123">	<!-- book标签描述一本图书   id属性描述 的是图书 的编号  -->
        <name>葵花宝典</name>	<!-- name标签描述 的是图书 的信息 -->
        <author>班长</author>	<!-- author单词是作者的意思 ，描述图书作者 -->
        <price>5.5</price>	<!-- price单词是价格，描述的是图书 的价格 -->
    </book>
</books>
```

## 5.4 xml语法规则

* 所有 XML 元素都须有关闭标签（也就是闭合）
* XML 标签对大小写敏感
* XML 必须正确地嵌套
* XML 文档必须有根元素
  * 根元素就是顶级元素
  * 没有父标签的元素，叫顶级元素。
  * 根元素是没有父标签的顶级元素，而且是唯一一个才行。

* XML 的属性值须加引号
* XML 中的特殊字符需要进行转义
* 文本区域（CDATA 区）中可以写特殊字符，不会进行解析，当作纯文本解析，弥补上面的特殊字符需要转义。

## 5.5 xml 解析技术

* xml 可扩展的标记语言。不管是 html 文件还是 xml 文件它们都是标记型文档，都可以使用 w3c 组织制定的 dom 技术来解析
* 早期 JDK 为我们提供了两种 xml 解析技术 DOM 和 Sax 简介（已经过时，但我们需要知道这两种技术）
* dom 解析技术是 W3C 组织制定的，而所有的编程语言都对这个解析技术使用了自己语言的特点进行实现。Java 对 dom 技术解析标记也做了实现。
* sun 公司在 JDK5 版本对 dom 解析技术进行升级：SAX（ Simple API for XML ）SAX 解析，它跟 W3C 制定的解析不太一样。它是以类似事件机制通过回调告诉用户当前正在解析的内容。它是一行一行的读取 xml 文件进行解析的。不会创建大量的 dom 对象。所以它在解析 xml 的时候，在内存的使用上。和性能上。都优于 Dom 解析

* 第三方的解析：
  * jdom 在 dom 基础上进行了封装 、
  * dom4j 又对 jdom 进行了封装,dom4j 它不是 sun 公司的技术，而属于第三方公司的技术。
  * pull 主要用在 Android 手机开发，是在跟 sax 非常类似都是事件机制解析 xml 文件。

## 5.6 dom4j解析xml

* 引入dom4j的jar包
* 导入xml文件
* 加载 xml 文件创建 Document 对象
* 通过 Document 对象拿到根元素对象
* 通过根元素.elelemts(标签名); 可以返回一个集合，这个集合里放着。所有你指定的标签名的元素对象
* 找到你想要修改、删除的子元素，进行相应在的操作
* 保存到硬盘上

```xml
<?xml version="1.0" encoding="UTF-8"?>
<books>
    <book sn="SN12341232">
        <name>辟邪剑谱</name>
        <price>9.9</price>
        <author>班主任</author>
    </book>
    <book sn="SN12341231">
        <name>葵花宝典</name>
        <price>99.99</price>
        <author>班长</author>
    </book>
</books>
```

```java
/* 读取 xml 文件中的内容
*/
@Test
public void readXML() throws DocumentException {
// 需要分四步操作:
// 第一步，通过创建 SAXReader 对象。来读取 xml 文件，获取 Document 对象
// 第二步，通过 Document 对象。拿到 XML 的根元素对象
// 第三步，通过根元素对象。获取所有的 book 标签对象
// 第四小，遍历每个 book 标签对象。然后获取到 book 标签对象内的每一个元素，再通过 getText() 方法拿到
起始标签和结束标签之间的文本内容
// 第一步，通过创建 SAXReader 对象。来读取 xml 文件，获取 Document 对象
SAXReader reader = new SAXReader();
Document document = reader.read("src/books.xml");
// 第二步，通过 Document 对象。拿到 XML 的根元素对象
Element root = document.getRootElement();
// 打印测试
// Element.asXML() 它将当前元素转换成为 String 对象
// System.out.println( root.asXML() );
// 第三步，通过根元素对象。获取所有的 book 标签对象
// Element.elements(标签名)它可以拿到当前元素下的指定的子元素的集合
List<Element> books = root.elements("book");
// 第四小，遍历每个 book 标签对象。然后获取到 book 标签对象内的每一个元素，
for (Element book : books) {
// 测试
// System.out.println(book.asXML());
// 拿到 book 下面的 name 元素对象
Element nameElement = book.element("name");
// 拿到 book 下面的 price 元素对象
Element priceElement = book.element("price");
// 拿到 book 下面的 author 元素对象
Element authorElement = book.element("author");
// 再通过 getText() 方法拿到起始标签和结束标签之间的文本内容
System.out.println("书名" + nameElement.getText() + " , 价格:"
+ priceElement.getText() + ", 作者：" + authorElement.getText());
}
}
```

