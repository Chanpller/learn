

# 第3章-JavaScript

## 3.1 JavaScript 介绍

* Javascript 语言诞生主要是完成页面的数据验证。因此它运行在客户端，需要运行浏览器来解析执行JavaScript 代码。JS 是Netscape 网景公司的产品，最早取名为LiveScript;为了吸引更多java 程序员。更名为JavaScript。JS 是弱类型，Java 是强类型。

* 特点：

1. 交互性（它可以做的就是信息的动态交互）
2. 安全性（不允许直接访问本地硬盘）
3. 跨平台性（只要是可以解释JS 的浏览器都可以执行，和平台无关）

## 3.2 JavaScript 和html 代码的结合方式

### 3.2.1 第一种方式

* 只需要在head 标签中，或者在body 标签中， 使用script 标签来书写JavaScript 代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
// alert 是JavaScript 语言提供的一个警告框函数。
// 它可以接收任意类型的参数，这个参数就是警告框的提示信息
alert("hello javaScript!");
</script>
</head>
<body>
</body>
</html>
```

### 3.2.2 第二种方式

* 使用script 标签引入单独的JavaScript 代码文件

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<!--
现在需要使用script 引入外部的js 文件来执行
src 属性专门用来引入js 文件路径（可以是相对路径，也可以是绝对路径）
script 标签可以用来定义js 代码，也可以用来引入js 文件
但是，两个功能二选一使用。不能同时使用两个功能
-->
<script type="text/javascript" src="1.js"></script>
<script type="text/javascript">
alert("国哥现在可以帅了");
</script>
</head>
<body>
</body>
</html>
```

## 3.3 变量

* JavaScript 的变量类型：
  * 数值类型： number
  * 字符串类型： string
  * 对象类型： object
  * 布尔类型： boolean
  * 函数类型： function
* JavaScript 里特殊的值：
  * undefined 未定义，所有js 变量未赋于初始值的时候，默认值都是undefined
  * null 空值
  * NaN 全称是：Not a Number。非数字。非数值。

* JS 中的定义变量格式：
  * var 变量名;
  * var 变量名= 值;

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
var i;
// alert(i); // undefined
i = 12;
// typeof()是JavaScript 语言提供的一个函数。
// alert( typeof(i) ); // number
i = "abc";
// 它可以取变量的数据类型返回
// alert( typeof(i) ); // String
var a = 12;
var b = "abc";
alert( a * b ); // NaN 是非数字，非数值。
</script>
</head>
<body>
</body>
</html>
```

## 3.4关系（比较）运算

* 等于： == 等于是简单的做字面值的比较
* 全等于： === 除了做字面值的比较之外，还会比较两个变量的数据类型

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
var a = "12";
var b = 12;
alert( a == b ); // true
alert( a === b ); // false
</script>
</head>
<body>
</body>
</html>
```

## 3.5 逻辑运算

* 且运算： &&
* 或运算： ||
* 取反运算： !
* 在JavaScript 语言中，所有的变量，都可以做为一个boolean 类型的变量去使用。
* 0 、null、undefined、””(空串) 都认为是false；
* && 且运算。
  有两种情况：
  第一种：当表达式全为真的时候。返回最后一个表达式的值。
  第二种：当表达式中，有一个为假的时候。返回第一个为假的表达式的值
  || 或运算
  第一种情况：当表达式全为假时，返回最后一个表达式的值
  第二种情况：只要有一个表达式为真。就会把回第一个为真的表达式的值
  并且&& 与运算和||或运算有短路。
  短路就是说，当这个&&或||运算有结果了之后。后面的表达式不再执行
  var a = "abc";
  var b = true;
  var d = false;
  var c = null;

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
/* 在JavaScript 语言中，所有的变量，都可以做为一个boolean 类型的变量去使用。
0 、null、undefined、””(空串) 都认为是false；*/
// var a = 0;
// if (a) {
// alert("零为真");
// } else {
// alert("零为假");
// }
// var b = null;
// if (b) {
// alert("null 为真");
// } else {
// alert("null 为假");
// }
// var c = undefined;
// if (c) {
// alert("undefined 为真");
// } else {
// alert("undefined 为假");
// }
// var d = "";
// if (d) {
// alert("空串为真");
// } else {
// alert("空串为假");
// }
/* && 且运算。
有两种情况：
第一种：当表达式全为真的时候。返回最后一个表达式的值。
第二种：当表达式中，有一个为假的时候。返回第一个为假的表达式的值*/
var a = "abc";
var b = true;
var d = false;
var c = null;
// alert( a && b );//true
// alert( b && a );//true
// alert( a && d ); // false
// alert( a && c ); // null
/* || 或运算
第一种情况：当表达式全为假时，返回最后一个表达式的值
第二种情况：只要有一个表达式为真。就会把回第一个为真的表达式的值*/
// alert( d || c ); // null
// alert( c|| d ); //false
// alert( a || c ); //abc
// alert( b || c ); //true
</script>
</head>
<body>
</body>
</html>
```

## 3.6 数组

JS 中数组的定义，格式：

var 数组名= []; // 空数组

var 数组名= [1 , ’abc’ , true]; // 定义数组同时赋值元素

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
var arr = [true,1]; // 定义一个空数组
// alert( arr.length ); // 0
arr[0] = 12;
// alert( arr[0] );//12
// alert( arr.length ); // 0
// javaScript 语言中的数组，只要我们通过数组下标赋值，那么最大的下标值，就会自动的给数组做扩容操作。
arr[2] = "abc";
alert(arr.length); //3
// alert(arr[1]);// undefined
// 数组的遍历
for (var i = 0; i < arr.length; i++){
alert(arr[i]);
}
</script>
</head>
<body>
</body>
</html>
```

## 3.7 函数

### 3.7.1 函数的定义

* 函数的二种定义方式

  * 使用function 关键字来定义函数

    ```
    使用的格式如下:
    function 函数名(形参列表){
    函数体
    }
    在JavaScript 语言中，如何定义带有返回值的函数？
    只需要在函数体内直接使用return 语句返回值即可！
    ```

  * 使用var方式

    ```
    var 函数名= function(形参列表) { 函数体}
    
    
    ```

* 注：在Java 中函数允许重载。但是在JS 中函数的重载会直接覆盖掉上一次的定义

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
// 定义一个无参函数
function fun(){
alert("无参函数fun()被调用了");
}
// 函数调用===才会执行
// fun();
function fun2(a ,b) {
alert("有参函数fun2()被调用了a=>" + a + ",b=>"+b);
}
// fun2(12,"abc");
// 定义带有返回值的函数
function sum(num1,num2) {
var result = num1 + num2;
return result;
}
alert( sum(100,50) );
    
var fun = function () {
alert("无参函数");
}
// fun();
var fun2 = function (a,b) {
alert("有参函数a=" + a + ",b=" + b);
}
// fun2(1,2);
var fun3 = function (num1,num2) {
return num1 + num2;
}
alert( fun3(100,200) );
</script>
</head>
<body>
</body>
</html>
```

### 3.7.2 函数的arguments 隐形参数（只在function 函数内）

* 在function 函数中不需要定义，但却可以直接用来获取所有参数的变量。我们管它叫隐形参数。
* 隐形参数特别像java 基础的可变长参数一样。
* public void fun( Object ... args );可变长参数其他是一个数组。
* 那么js 中的隐形参数也跟java 的可变长参数一样。操作类似数组。对象是arguments

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
function fun(a) {
alert( arguments.length );//可看参数个数
alert( arguments[0] );
alert( arguments[1] );
alert( arguments[2] );
alert("a = " + a);
for (var i = 0; i < arguments.length; i++){
alert( arguments[i] );
}
alert("无参函数fun()");
}
// fun(1,"ad",true);
// 需求：要求编写一个函数。用于计算所有参数相加的和并返回
function sum(num1,num2) {
var result = 0;
for (var i = 0; i < arguments.length; i++) {
if (typeof(arguments[i]) == "number") {
result += arguments[i];
}
}
return result;
}
alert( sum(1,2,3,4,"abc",5,6,7,8,9) );
</script>
</head>
<body>
</body>
</html>
```

## 3.8 JS 中的自定义对象

### 3.8.1 方式一new Obejct

* Object 形式的自定义对象

* 对象的定义：
  * var 变量名= new Object(); // 对象实例（空对象）
  * 变量名.属性名= 值; // 定义一个属性
  * 变量名.函数名= function(){} // 定义一个函数
* 对象的访问：
  * 变量名.属性/ 函数名();

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
// 对象的定义：
// var 变量名= new Object(); // 对象实例（空对象）
// 变量名.属性名= 值; // 定义一个属性
// 变量名.函数名= function(){} // 定义一个函数
var obj = new Object();
obj.name = "华仔";
obj.age = 18;
obj.fun = function () {
alert("姓名：" + this.name + " , 年龄：" + this.age);
}
// 对象的访问：
// 变量名.属性/ 函数名();
// alert( obj.age );
obj.fun();
</script>
</head>
<body>
</body>
</html>
```

### 3.8.2 使用{}

* {}花括号形式的自定义对象

* 对象的定义：

  var 变量名= { // 空对象
  属性名：值, // 定义一个属性
  属性名：值, // 定义一个属性
  函数名：function(){} // 定义一个函数
  };

* 对象的访问：

  变量名.属性/ 函数名();

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
// 对象的定义：
// var 变量名= { // 空对象
// 属性名：值, // 定义一个属性
// 属性名：值, // 定义一个属性
// 函数名：function(){} // 定义一个函数
// };
var obj = {
name:"国哥",
age:18,
fun : function () {
alert("姓名：" + this.name + " , 年龄：" + this.age);
}
};
// 对象的访问：
// 变量名.属性/ 函数名();
alert(obj.name);
obj.fun();
</script>
</head>
<body>
</body>
</html>
```

## 3.9 js 中的事件

* 什么是事件？事件是电脑输入设备与页面进行交互的响应。我们称之为事件。
* 常用的事件：
  * onload 加载完成事件： 页面加载完成之后，常用于做页面js 代码初始化操作
  * onclick 单击事件： 常用于按钮的点击响应操作。
  * onblur 失去焦点事件： 常用用于输入框失去焦点后验证其输入内容是否合法。
  * onchange 内容发生改变事件： 常用于下拉列表和输入框内容发生改变后操作
  * onsubmit 表单提交事件： 常用于表单提交前，验证所有表单项是否合法。
* 事件的注册又分为静态注册和动态注册两种：
  * 什么是事件的注册（绑定）？其实就是告诉浏览器，当事件响应后要执行哪些操作代码，叫事件注册或事件绑定。
  * 静态注册事件：通过html 标签的事件属性直接赋于事件响应后的代码，这种方式我们叫静态注册。
  * 动态注册事件：是指先通过js 代码得到标签的dom 对象，然后再通过dom 对象.事件名= function(){} 这种形式赋于事件响应后的代码，叫动态注册，需要等页面加载完成后才能绑定，否则找不到元素，window.onload完成之后。
    * 动态注册基本步骤：
      1、获取标签对象
      2、标签对象.事件名= fucntion(){}

```html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
// onload 事件的方法
function onloadFun() {
alert('静态注册onload 事件，所有代码');
}
// onload 事件动态注册。是固定写法
window.onload = function () {
alert("动态注册的onload 事件");
}
</script>
</head>
<!--静态注册onload 事件
onload 事件是浏览器解析完页面之后就会自动触发的事件
<body onload="onloadFun();">
-->
<body>
</body>
</html>
```

## 3.10 DOM 模型

* DOM 全称是Document Object Model 文档对象模型
* 就是把文档中的标签，属性，文本，转换成为对象来管理。

### 3.10.1 Document 对象

* Document 对象的理解：

  第一点：Document 它管理了所有的HTML 文档内容。

  第二点：document 它是一种树结构的文档。有层级关系。

  第三点：它让我们把所有的标签都对象化

  第四点：我们可以通过document 访问所有的标签对象。

### 3.10.2 Document 对象中的方法介绍

* document.getElementById(elementId)

  通过标签的id 属性查找标签dom 对象，elementId 是标签的id 属性值

* document.getElementsByName(elementName)

  通过标签的name 属性查找标签dom 对象，elementName 标签的name 属性值

* document.getElementsByTagName(tagname)
  通过标签名查找标签dom 对象。tagname 是标签名

* document.createElement( tagName)

  方法，通过给定的标签名，创建一个标签对象。tagName 是要创建的标签名

* 一定要在页面加载完成之后执行，才能查询到标签对象。
* createElement举例

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript">
window.onload = function () {
// 现在需要我们使用js 代码来创建html 标签，并显示在页面上
// 标签的内容就是：<div>国哥，我爱你</div>
var divObj = document.createElement("div"); // 在内存中<div></div>
var textNodeObj = document.createTextNode("国哥，我爱你"); // 有一个文本节点对象#国哥，我
爱你
divObj.appendChild(textNodeObj); // <div>国哥，我爱你</div>
// divObj.innerHTML = "国哥，我爱你"; // <div>国哥，我爱你</div>,但，还只是在内存中
// 添加子元素
document.body.appendChild(divObj);
}
</script>
</head>
<body>
</body>
</html>
```

### 3.10.3 节点的常用属性和方法

* 节点就是标签对象
  * 方法，通过具体的元素节点调用
    * getElementsByTagName()方法，获取当前节点的指定标签名孩子节点
    * appendChild( oChildNode )方法，可以添加一个子节点，oChildNode 是要添加的孩子节点
  * 属性
    * childNodes
      属性，获取当前节点的所有子节点
    * firstChild
      属性，获取当前节点的第一个子节点
    * lastChild
      属性，获取当前节点的最后一个子节点
    * parentNode
      属性，获取当前节点的父节点
    * nextSibling
      属性，获取当前节点的下一个节点
    * previousSibling
      属性，获取当前节点的上一个节点
    * className
      用于获取或设置标签的class 属性值
    * innerHTML
      属性，表示获取/设置起始标签和结束标签中的内容
    * innerText
      属性，表示获取/设置起始标签和结束标签中的文本

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dom 查询</title>
<link rel="stylesheet" type="text/css" href="style/css.css" />
<script type="text/javascript">
window.onload = function(){
//1.查找#bj 节点
document.getElementById("btn01").onclick = function () {
var bjObj = document.getElementById("bj");
alert(bjObj.innerHTML);
}
//2.查找所有li 节点
var btn02Ele = document.getElementById("btn02");
btn02Ele.onclick = function(){
var lis = document.getElementsByTagName("li");
alert(lis.length)
};
//3.查找name=gender 的所有节点
var btn03Ele = document.getElementById("btn03");
btn03Ele.onclick = function(){
var genders = document.getElementsByName("gender");
alert(genders.length)
};
//4.查找#city 下所有li 节点
var btn04Ele = document.getElementById("btn04");
btn04Ele.onclick = function(){
//1 获取id 为city 的节点
//2 通过city 节点.getElementsByTagName 按标签名查子节点
var lis = document.getElementById("city").getElementsByTagName("li");
alert(lis.length)
};
//5.返回#city 的所有子节点
var btn05Ele = document.getElementById("btn05");
btn05Ele.onclick = function(){
//1 获取id 为city 的节点
//2 通过city 获取所有子节点
alert(document.getElementById("city").childNodes.length);
};
//6.返回#phone 的第一个子节点
var btn06Ele = document.getElementById("btn06");
btn06Ele.onclick = function(){
// 查询id 为phone 的节点
alert( document.getElementById("phone").firstChild.innerHTML );
};
//7.返回#bj 的父节点
var btn07Ele = document.getElementById("btn07");
btn07Ele.onclick = function(){
//1 查询id 为bj 的节点
var bjObj = document.getElementById("bj");
//2 bj 节点获取父节点
alert( bjObj.parentNode.innerHTML );
};
//8.返回#android 的前一个兄弟节点
var btn08Ele = document.getElementById("btn08");
btn08Ele.onclick = function(){
// 获取id 为android 的节点
// 通过android 节点获取前面兄弟节点
alert( document.getElementById("android").previousSibling.innerHTML );
};
//9.读取#username 的value 属性值
var btn09Ele = document.getElementById("btn09");
btn09Ele.onclick = function(){
alert(document.getElementById("username").value);
};
//10.设置#username 的value 属性值
var btn10Ele = document.getElementById("btn10");
btn10Ele.onclick = function(){
document.getElementById("username").value = "国哥你真牛逼";
};
//11.返回#bj 的文本值
var btn11Ele = document.getElementById("btn11");
btn11Ele.onclick = function(){
alert(document.getElementById("city").innerHTML);
// alert(document.getElementById("city").innerText);
};
};
</script>
</head>
<body>
<div id="total">
<div class="inner">
<p>
你喜欢哪个城市?
</p>
<ul id="city">
<li id="bj">北京</li>
<li>上海</li>
<li>东京</li>
<li>首尔</li>
</ul>
<br>
<br>
<p>
你喜欢哪款单机游戏?
</p>
<ul id="game">
<li id="rl">红警</li>
<li>实况</li>
<li>极品飞车</li>
<li>魔兽</li>
</ul>
<br />
<br />
<p>
你手机的操作系统是?
</p>
<ul id="phone"><li>IOS</li><li id="android">Android</li><li>Windows Phone</li></ul>
</div>
<div class="inner">
gender:
<input type="radio" name="gender" value="male"/>
Male
<input type="radio" name="gender" value="female"/>
Female
<br>
<br>
name:
<input type="text" name="name" id="username" value="abcde"/>
</div>
</div>
<div id="btnList">
<div><button id="btn01">查找#bj 节点</button></div>
<div><button id="btn02">查找所有li 节点</button></div>
<div><button id="btn03">查找name=gender 的所有节点</button></div>
<div><button id="btn04">查找#city 下所有li 节点</button></div>
<div><button id="btn05">返回#city 的所有子节点</button></div>
<div><button id="btn06">返回#phone 的第一个子节点</button></div>
<div><button id="btn07">返回#bj 的父节点</button></div>
<div><button id="btn08">返回#android 的前一个兄弟节点</button></div>
<div><button id="btn09">返回#username 的value 属性值</button></div>
<div><button id="btn10">设置#username 的value 属性值</button></div>
<div><button id="btn11">返回#bj 的文本值</button></div>
</div>
</body>
</html>
```

## 3.11 正则表达式

**RegExp 对象**

* 正则表达式是描述字符模式的对象。

* 正则表达式用于对字符串模式匹配及检索替换，是对字符串执行模式匹配的强大工具。

**语法**

```
var patt=new RegExp(pattern,modifiers);

或者更简单的方式:
var patt=/pattern/modifiers; 
```

- pattern（模式） 描述了表达式的模式 
- modifiers(修饰符) 用于指定全局匹配、区分大小写的匹配和多行匹配 

**修饰符**

* 修饰符用于执行区分大小写和全局匹配:

| 修饰符 | 描述                                                     |
| ------ | -------------------------------------------------------- |
| i      | 执行对大小写不敏感的匹配。                               |
| g      | 执行全局匹配（查找所有匹配而非在找到第一个匹配后停止）。 |
| m      | 执行多行匹配。                                           |

**方括号**

* 方括号用于查找某个范围内的字符：

| 表达式             | 描述                               |
| ------------------ | ---------------------------------- |
| [abc\]             | 查找方括号之间的任何字符。         |
| [^abc\]            | 查找任何不在方括号之间的字符。     |
| [0-9]              | 查找任何从 0 至 9 的数字。         |
| [a-z]              | 查找任何从小写 a 到小写 z 的字符。 |
| [A-Z]              | 查找任何从大写 A 到大写 Z 的字符。 |
| [A-z]              | 查找任何从大写 A 到小写 z 的字符。 |
| [adgk]             | 查找给定集合内的任何字符。         |
| [^adgk]            | 查找给定集合外的任何字符。         |
| (red\|blue\|green) | 查找任何指定的选项。               |

**元字符**

* 元字符（Metacharacter）是拥有特殊含义的字符：

| 元字符 | 描述                                        |
| ------ | ------------------------------------------- |
| .      | 查找单个字符，除了换行和行结束符。          |
| \w     | 查找单词字符。                              |
| \W     | 查找非单词字符。                            |
| \d     | 查找数字。                                  |
| \D     | 查找非数字字符。                            |
| \s     | 查找空白字符。                              |
| \S     | 查找非空白字符。                            |
| \b     | 匹配单词边界。                              |
| \B]    | 匹配非单词边界。                            |
| \0     | 查找 NUL 字符。                             |
| \n     | 查找换行符。                                |
| \f     | 查找换页符。                                |
| \r     | 查找回车符。                                |
| \t     | 查找制表符。                                |
| \v     | 查找垂直制表符。                            |
| \xxx   | 查找以八进制数 xxx 规定的字符。             |
| \xdd   | 查找以十六进制数 dd 规定的字符。            |
| \uxxxx | 查找以十六进制数 xxxx 规定的 Unicode 字符。 |

**量词**

| 量词    | 描述                                        |
| ------- | ------------------------------------------- |
| n+      | 匹配任何包含至少一个 n 的字符串。           |
| n*      | 匹配任何包含零个或多个 n 的字符串。         |
| n?      | 匹配任何包含零个或一个 n 的字符串。         |
| n{X}    | 匹配包含 X 个 n 的序列的字符串。            |
| n{X,Y}} | 匹配包含 X 或 Y 个 n 的序列的字符串。       |
| n{X,}   | 匹配包含至少 X 个 n 的序列的字符串。        |
| n$      | 匹配任何结尾为 n 的字符串。                 |
| ^n      | 匹配任何开头为 n 的字符串。                 |
| ?=n     | 匹配任何其后紧接指定字符串 n 的字符串。     |
| ?!n     | 匹配任何其后没有紧接指定字符串 n 的字符串。 |

**RegExp 对象方法**

| 方法    | 描述                                               | FF   | IE   |
| ------- | -------------------------------------------------- | ---- | ---- |
| compile | 正则表达式。                                       | 1    | 4    |
| exec    | 检索字符串中指定的值。返回找到的值，并确定其位置。 | 1    | 4    |
| test    | 检索字符串中指定的值。返回 true 或 false。         | 1    | 4    |

**支持正则表达式的 String 对象的方法**

| 方法    | 描述                             | FF   | IE   |
| ------- | -------------------------------- | ---- | ---- |
| search  | 检索与正则表达式相匹配的值。     | 1    | 4    |
| match   | 找到一个或多个正则表达式的匹配。 | 1    | 4    |
| replace | 替换与正则表达式匹配的子串。     | 1    | 4    |
| split   | 把字符串分割为字符串数组。       | 1    | 4    |

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        // 表示要求字符串中，是否包含字母e
        // var patt = new RegExp("e");
        // var patt = /e/; // 也是正则表达式对象
        // 表示要求字符串中，是否包含字母a或b或c
        // var patt = /[abc]/;
        // 表示要求字符串，是否包含小写字母
        // var patt = /[a-z]/;
        // 表示要求字符串，是否包含任意大写字母
        // var patt = /[A-Z]/;
        // 表示要求字符串，是否包含任意数字
        // var patt = /[0-9]/;
        // 表示要求字符串，是否包含字母，数字，下划线
        // var patt = /\w/;
        // 表示要求 字符串中是否包含至少一个a
        // var patt = /a+/;
        // 表示要求 字符串中是否 *包含* 零个 或 多个a
        // var patt = /a*/;
        // 表示要求 字符串是否包含一个或零个a
        // var patt = /a?/;
        // 表示要求 字符串是否包含连续三个a
        // var patt = /a{3}/;
        // 表示要求 字符串是否包 至少3个连续的a，最多5个连续的a
        // var patt = /a{3,5}/;
        // 表示要求 字符串是否包 至少3个连续的a，
        // var patt = /a{3,}/;
        // 表示要求 字符串必须以a结尾
        // var patt = /a$/;
        // 表示要求 字符串必须以a打头
        // var patt = /^a/;

        // 要求字符串中是否*包含* 至少3个连续的a
        // var patt = /a{3,5}/;
        // 要求字符串，从头到尾都必须完全匹配
        // var patt = /^a{3,5}$/;

        var patt = /^\w{5,12}$/;

        var str = "wzg168[[[";

        alert( patt.test(str) );


    </script>
</head>
<body>

</body>
</html>
```

