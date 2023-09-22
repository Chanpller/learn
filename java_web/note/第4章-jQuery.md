# 第4章-jQuery

## 4.1 jQuery 介绍

* 什么是jQuery ?

  jQuery，顾名思义，也就是JavaScript 和查询（Query），它就是辅助JavaScript 开发的js 类库。

* jQuery 核心思想！！！

  它的核心思想是write less,do more(写得更少,做得更多)，所以它实现了很多浏览器的兼容问题。

* jQuery 流行程度

  jQuery 现在已经成为最流行的JavaScript 库，在世界前10000 个访问最多的网站中，有超过55%在使用jQuery。

* jQuery 好处！！！

  jQuery 是免费、开源的，jQuery 的语法设计可以使开发更加便捷，例如操作文档对象、选择DOM 元素、制作动画效果、事件处理、使用Ajax 以及其他功能

## 4.2 jQuery 核心函数

* $ 是jQuery 的核心函数，能完成jQuery 的很多功能。$()就是调用$这个函数
* 传入参数为[ 函数] 时：表示页面加载完成之后。相当于window.onload = function(){}
* 传入参数为[ HTML 字符串] 时：会对我们创建这个html 标签对象
* 传入参数为[ 选择器字符串] 时：
  * $(“#id 属性值”); id 选择器，根据id 查询标签对象
  * $(“标签名”); 标签名选择器，根据指定的标签名查询标签对象
  * $(“.class 属性值”); 类型选择器，可以根据class 属性查询标签对象
* 传入参数为[ DOM 对象] 时：会把这个dom 对象转换为jQuery 对象

## 4.3 jQuery 对象和dom 对象区分

* 什么是jQuery 对象，什么是dom 对象

  * Dom 对象

    * 通过getElementById()查询出来的标签对象是Dom 对象

    * 通过getElementsByName()查询出来的标签对象是Dom 对象

    * 通过getElementsByTagName()查询出来的标签对象是Dom 对象

    * 通过createElement() 方法创建的对象，是Dom 对象

      ```
      DOM 对象Alert 出来的效果是：[object HTML 标签名Element]
      ```

  * jQuery 对象

    * 通过JQuery 提供的API 创建的对象，是JQuery 对象

    * 通过JQuery 包装的Dom 对象，也是JQuery 对象

    * 通过JQuery 提供的API 查询到的对象，是JQuery 对象

      ```
      jQuery 对象Alert 出来的效果是：[object Object]
      ```

* jQuery 对象的本质是什么？
  
  * jQuery 对象是dom 对象的数组+ jQuery 提供的一系列功能函数。
* jQuery 对象和Dom 对象使用区别
  * jQuery 对象不能使用DOM 对象的属性和方法
  * DOM 对象也不能使用jQuery 对象的属性和方法
* Dom 对象和jQuery 对象互转
  * $( DOM 对象) 就可以转换成为jQuery 对象
  * jQuery 对象[下标]取出相应的DOM 对象

## 4.4 jQuery 选择器

### 4.4.1 基本选择器

- #ID 选择器：根据id 查找标签对象
- .class 选择器：根据class 查找标签对象
- element 选择器：根据标签名查找标签对象
- `*`：表示任意的，所有的元素
- selector1，selector2 组合选择器：合并选择器1，选择器2 的结果并返回

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function (){
            //id选择器
            alert($("#notMe").text())
            //组合选择器
            alert($("#notMe,#myDiv").text())
        });
    </script>
</head>
<body>
<div id="notMe"><p>id="notMe"</p></div>
<div id="myDiv">id="myDiv"</div>
</body>
</html>
```



### 4.4.2 层级选择器

- ancestor descendant 后代选择器：在给定的祖先元素下匹配所有的后代元素
- parent > child 子元素选择器：在给定的父元素下匹配所有的子元素
- prev + next 相邻元素选择器：匹配所有紧接在prev 元素后的next 元素
- prev ~ sibings 之后的兄弟元素选择器：匹配prev 元素之后的所有siblings 元素

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function (){
            //层级选择器器
            alert($("form > input")[0].name)
        });
    </script>
</head>
<body>
<form>
    <label>Name:</label>
    <input name="name" />
    <fieldset>
        <label>Newsletter:</label>
        <input name="newsletter" />
    </fieldset>
</form>
<input name="none" />

</body>
</html>
```



### 4.4.3 过滤选择器

#### 4.4.3.1 基本过滤器

- :first 获取第一个元素
- :last 获取最后个元素
- :not(selector) 去除所有与给定选择器匹配的元素
- :even 匹配所有索引值为偶数的元素，从0 开始计数
- :odd 匹配所有索引值为奇数的元素，从0 开始计数
- :eq(index) 匹配一个给定索引值的元素
- :gt(index) 匹配所有大于给定索引值的元素
- :lt(index) 匹配所有小于给定索引值的元素
- :header 匹配如h1, h2, h3 之类的标题元素
- :animated 匹配所有正在执行动画效果的元素

#### 4.4.3.2 内容过滤器

- :contains(text) 匹配包含给定文本的元素
- :empty 匹配所有不包含子元素或者文本的空元素
- :parent 匹配含有子元素或者文本的元素
- :has(selector) 匹配含有选择器所匹配的元素的元素

#### 4.4.3.3 属性过滤器

- [attribute] 匹配包含给定属性的元素。
- [attribute=value] 匹配给定的属性是某个特定值的元素
- [attribute!=value] 匹配所有不含有指定的属性，或者属性不等于特定值的元素。
- [attribute^=value] 匹配给定的属性是以某些值开始的元素
- [attribute$=value] 匹配给定的属性是以某些值结尾的元素
- [attribute*=value] 匹配给定的属性是以包含某些值的元素
- `[attrSel1][attrSel2][attrSelN]` 复合属性选择器，需要同时满足多个条件时使用。

#### 4.4.3.4 表单过滤器

- :input 匹配所有input, textarea, select 和button 元素
- :text 匹配所有文本输入框
- :password 匹配所有的密码输入框
- :radio 匹配所有的单选框
- :checkbox 匹配所有的复选框
- :submit 匹配所有提交按钮
- :image 匹配所有img 标签
- :reset 匹配所有重置按钮
- :button 匹配所有input type=button <button>按钮
- :file 匹配所有input type=file 文件上传
- :hidden 匹配所有不可见元素display:none 或input type=hidden

#### 4.4.3.5 表单对象属性过滤器

- :enabled 匹配所有可用元素
- :disabled 匹配所有不可用元素
- :checked 匹配所有选中的单选，复选，和下拉列表中选中的option 标签对象
- :selected 匹配所有选中的option\

**过滤器demo**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function (){
            //过滤选择器器
            alert($('li:first').text())
        });
    </script>
</head>
<body>
<form>
    <label>Name:</label>
    <input name="name" />
    <fieldset>
        <label>Newsletter:</label>
        <input name="newsletter" />
    </fieldset>
</form>
<ul>
    <li>list item 1</li>
    <li>list item 2</li>
    <li>list item 3</li>
    <li>list item 4</li>
    <li>list item 5</li>
</ul>
</body>
</html>
```



## 4.5 jQuery 元素筛选

- eq() 获取给定索引的元素功能跟:eq() 一样
- first() 获取第一个元素功能跟:first 一样
- last() 获取最后一个元素功能跟:last 一样
- filter(exp) 留下匹配的元素
- is(exp) 判断是否匹配给定的选择器，只要有一个匹配就返回，true
- has(exp) 返回包含有匹配选择器的元素的元素功能跟:has 一样
- not(exp) 删除匹配选择器的元素功能跟:not 一样
- children(exp) 返回匹配给定选择器的子元素功能跟parent>child 一样
- find(exp) 返回匹配给定选择器的后代元素功能跟ancestor descendant 一样
- next() 返回当前元素的下一个兄弟元素功能跟prev + next 功能一样
- nextAll() 返回当前元素后面所有的兄弟元素功能跟prev ~ siblings 功能一样
- nextUntil() 返回当前元素到指定匹配的元素为止的后面元素
- parent() 返回父元素
- prev(exp) 返回当前元素的上一个兄弟元素
- prevAll() 返回当前元素前面所有的兄弟元素
- prevUnit(exp) 返回当前元素到指定匹配的元素为止的前面元素
- siblings(exp) 返回所有兄弟元素
- add() 把add 匹配的选择器的元素添加到当前jquery 对象中

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function (){
            //元素过滤器使用
            alert($("p").eq(1).text())
        });
    </script>
</head>
<body>
<p> This is just a test.</p> <p> So is this</p>
</body>
</html>
```

## 4.6 jQuery 的属性操作

- html() 它可以设置和获取起始标签和结束标签中的内容。跟dom 属性innerHTML 一样。
- text() 它可以设置和获取起始标签和结束标签中的文本。跟dom 属性innerText 一样。
- val() 它可以设置和获取表单项的value 属性值,val 方法同时设置多个表单项的选中状态。跟dom 属性value 一样
- attr() 可以设置和获取属性的值
  - 不推荐操作checked、readOnly、selected、disabled 等等，如果没有该属性，获取的是undefined
  - attr 方法还可以操作非标准的属性。比如自定义属性：abc,bbj
- prop() 可以设置和获取属性的值,只推荐操作checked、readOnly、selected、disabled 等等，是attr的补充，当没有有属性时，返回的不是undefined而是false。
- removeAttr删除一个属性
- removeProp用来删除由.prop()方法设置的属性集
- addClass添加样式一个或多个要添加到元素中的CSS类名，请用空格分开
- removeClass删除全部或者指定的类
- toggleClass如果存在（不存在）就删除（添加）一个类。

```html
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8">
<title>Title</title>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function () {
/*
// 批量操作单选
$(":radio").val(["radio2"]);
// 批量操作筛选框的选中状态
$(":checkbox").val(["checkbox3","checkbox2"]);
// 批量操作多选的下拉框选中状态
$("#multiple").val(["mul2","mul3","mul4"]);
// 操作单选的下拉框选中状态
$("#single").val(["sin2"]);
*/
$("#multiple,#single,:radio,:checkbox").val(["radio2","checkbox1","checkbox3","mul1","mul4","sin3"]
);
});
</script>
</head>
<body>
<body>
单选：
<input name="radio" type="radio" value="radio1" />radio1
<input name="radio" type="radio" value="radio2" />radio2
<br/>
多选：
<input name="checkbox" type="checkbox" value="checkbox1" />checkbox1
<input name="checkbox" type="checkbox" value="checkbox2" />checkbox2
<input name="checkbox" type="checkbox" value="checkbox3" />checkbox3
<br/>
下拉多选：
<select id="multiple" multiple="multiple" size="4">
<option value="mul1">mul1</option>
<option value="mul2">mul2</option>
<option value="mul3">mul3</option>
<option value="mul4">mul4</option>
</select>
<br/>
下拉单选：
<select id="single">
<option value="sin1">sin1</option>
<option value="sin2">sin2</option>
<option value="sin3">sin3</option>
</select>
</body>
```

## 4.7 DOM 的增删改

* 内部插入：
  * appendTo()       a.appendTo(b) 把a 插入到b 子元素末尾，成为最后一个子元素
  * prependTo()     a.prependTo(b) 把a 插到b 所有子元素前面，成为第一个子元素
* 外部插入：
  * insertAfter()       a.insertAfter(b) 得到ba
  * insertBefore()       a.insertBefore(b) 得到ab
* 替换:
  * replaceWith()      a.replaceWith(b) 用b 替换掉a
  * replaceAll()        a.replaceAll(b) 用a 替换掉所有b
* 删除：
  * remove()    a.remove(); 删除a 标签
  * empty()      a.empty(); 清空a 标签里的内容

```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link rel="stylesheet" type="text/css" href="styleB/css.css" />
<script type="text/javascript" src="../../script/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function () {
// 创建一个用于复用的删除的 function 函数
var deleteFun = function(){
// alert("删除 操作 的 function : " + this);
// 在事件响应的 function 函数中，有一个 this 对象。这个 this 对象是当前正在响应事件的 dom 对象。
var $trObj = $(this).parent().parent();
var name = $trObj.find("td:first").text();
/**
* confirm 是 JavaScript 语言提供的一个确认提示框函数。你给它传什么，它就提示什么<br/>
* 当用户点击了确定，就返回 true。当用户点击了取消，就返回 false
*/
if( confirm("你确定要删除[" + name + "]吗？") ){
$trObj.remove();
}
// return false; 可以阻止 元素的默认行为。
return false;
};
// 给【Submit】按钮绑定单击事件
$("#addEmpButton").click(function () {
// 获取输入框，姓名，邮箱，工资的内容
var name = $("#empName").val();
var email = $("#email").val();
var salary = $("#salary").val();
// 创建一个行标签对象，添加到显示数据的表格中
var $trObj = $("<tr>" +
"<td>" + name + "</td>" +
"<td>" + email + "</td>" +
"<td>" + salary + "</td>" +
"<td><a href=\"deleteEmp?id=002\">Delete</a></td>" +
"</tr>");
// 添加到显示数据的表格中
$trObj.appendTo( $("#employeeTable") );
// 给添加的行的 a 标签绑上事件
$trObj.find("a").click( deleteFun );
});
// 给删除的 a 标签绑定单击事件
$("a").click( deleteFun );
});
</script>
</head>
<body>
<table id="employeeTable">
<tr>
<th>Name</th>
<th>Email</th>
<th>Salary</th>
<th>&nbsp;</th>
</tr>
<tr>
<td>Tom</td>
<td>tom@tom.com</td>
<td>5000</td>
<td><a href="deleteEmp?id=001">Delete</a></td>
</tr>
<tr>
<td>Jerry</td>
<td>jerry@sohu.com</td>
<td>8000</td>
<td><a href="deleteEmp?id=002">Delete</a></td>
</tr>
<tr>
<td>Bob</td>
<td>bob@tom.com</td>
<td>10000</td>
<td><a href="deleteEmp?id=003">Delete</a></td>
</tr>
</table>
<div id="formDiv">
<h4>添加新员工</h4>
<table>
<tr>
<td class="word">name: </td>
<td class="inp">
<input type="text" name="empName" id="empName" />
</td>
</tr>
<tr>
<td class="word">email: </td>
<td class="inp">
<input type="text" name="email" id="email" />
</td>
</tr>
<tr>
<td class="word">salary: </td>
<td class="inp">
<input type="text" name="salary" id="salary" />
</td>
</tr>
<tr>
<td colspan="2" align="center">
<button id="addEmpButton" value="abc">
Submit
</button>
</td>
</tr>
</table>
</div>
</body>
</html>
```

## 4.8 CSS 样式操作

- addClass() 添加样式
- removeClass() 删除样式
- toggleClass() 有就删除，没有就添加样式。
- offset() 获取和设置元素的坐标。

## 4.8 jQuery 动画

**基本动画**

- show() 将隐藏的元素显示

- hide() 将可见的元素隐藏。

- toggle() 可见就隐藏，不可见就显示。

- 以上动画方法都可以添加参数。

  1、第一个参数是动画 执行的时长，以毫秒为单位

  2、第二个参数是动画的回调函数 (动画完成后自动调用的函数)

**淡入淡出动画**

- fadeIn() 淡入（慢慢可见）
- fadeOut() 淡出（慢慢消失）
- fadeTo() 在指定时长内慢慢的将透明度修改到指定的值。0 透明，1 完成可见，0.5 半透明
- fadeToggle() 淡入/淡出 切换

## 4.9 jQuery 事件操作

* $( function(){} );和window.onload = function(){}的区别？

* 他们分别是在什么时候触发？
  * jQuery 的页面加载完成之后是浏览器的内核解析完页面的标签创建好 DOM 对象之后就会马上执行。
  * 原生 js 的页面加载完成之后，除了要等浏览器内核解析完标签创建好 DOM 对象，还要等标签显示时需要的内容加载完成。

* 他们触发的顺序？
  * jQuery 页面加载完成之后先执行
  * 原生 js 的页面加载完成之后

* 他们执行的次数？
  * 原生 js 的页面加载完成之后，只会执行最后一次的赋值函数。
  * jQuery 的页面加载完成之后是全部把注册的 function 函数，依次顺序全部执行。

## 4.10 jQuery 中其他的事件

- click() 它可以绑定单击事件，以及触发单击事件
- mouseover() 鼠标移入事件
- mouseout() 鼠标移出事件
- bind() 可以给元素一次性绑定一个或多个事件。
- one() 使用上跟 bind 一样。但是 one 方法绑定的事件只会响应一次。
- unbind() 跟 bind 方法相反的操作，解除事件的绑定
- live() 也是用来绑定事件。它可以用来绑定选择器匹配的所有元素的事件。哪怕这个元素是后面动态创建出来的也有效

```js
//3.使用 bind 同时对多个事件绑定同一个函数。怎么获取当前操作是什么事件。
$("#areaDiv").bind("mouseover mouseout",function (event) {
    if (event.type == "mouseover") {
    console.log("鼠标移入");
    } else if (event.type == "mouseout") {
    console.log("鼠标移出");
    }
});
```



## 4.11 事件的冒泡

* 什么是事件的冒泡？
  * 事件的冒泡是指，父子元素同时监听同一个事件。当触发子元素的事件的时候，同一个事件也被传递到了父元素的事件里去响应。

* 如何阻止事件冒泡呢？
  * 在子元素事件函数体内，return false; 可以阻止事件的冒泡传递

## 4.12javaScript 事件对象

* 事件对象，是封装有触发的事件信息的一个 javascript 对象。

* 如何获取呢 javascript 事件对象呢？

* 在给元素绑定事件的时候，在事件的 function( event ) 参数列表中添加一个参数，这个参数名，我们习惯取名为 event。这个 event 就是 javascript 传递参事件处理函数的事件对象。

```js
//使用原生方法获取event
window.onload = function () {
    document.getElementById("areaDiv").onclick = function (event) {
    	console.log(event);
    }
}
//使用jquery获取该对象，event是jquery封装过后的event对象
$(function () {
    $("#areaDiv").click(function (event) {
    	console.log(event);
    });
});

```

