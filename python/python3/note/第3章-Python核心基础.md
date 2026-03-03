# 第 3 章 Python 核心基础

## 3.1 字面量

### 3.1.1 概述 

来看这样一个场景：老师让学生把：姓名、年龄、体重写在纸上，纸上的文字，就是学生想要表达的内容，这些内容不需要计算、也不需要转换，就是字面上的含义，一看就能理解。
![](D:\workspace\IdeaProjects\learn\python\python3\image\1746586662422-1232a3b9-69df-4fee-8f4b-5f28974bbfe9.png) <!-- 
![](D:\workspace\IdeaProjects\learn\python\python3\image\1747966924322-0759f53e-74c9-41e2-83d1-1d29a4885591.png) 
![](D:\workspace\IdeaProjects\learn\python\python3\image\1747966935103-3e14aaeb-2f53-40b1-a9e6-ec7ace4c060d.png)

在程序中，也有上述这些“写出来就能被理解”的内容，这些内容在程序中叫做**<font style="color:#AD1A2B;">字面量</font>**，即：字面量就是直接写在代码中的“具体值”。

### 3.1.2 写法

下面代码中的内容，都是字面量。

```python
'张三'
18
65.2

'李四'
22
74.6

'王五'
25
80
```


以上代码中的 `'张三'`、`'李四'`、`'王五'` 均为字符串。所谓字符串，就是由“字符”组成的“串”。例如，字符串 `'张三'` 由 `'张`' 和 `'三'` 两个字符构成。

从本质上看，字符串属于文本类型，可以由任意数量的字符组成——无论是中文、英文、数字，还是各种符号。此处我们只需对字符串的概念有初步认识，后续课程中将对其进行详细讲解。


📢**<font style="color:#AD1A2B;">注意：</font>**字符串必须要放到引号中，使用：单引号、双引号、三个单引号、三个双引号都可以(超过3个报错，2个也报错)，但必须是英文的引号。

**📋备注：**写在 Python 文件头部的字符串，会被自动识别成 docstring（文档字符串），文档字符串的主要作用是：对当前 Python 文件进行说明，且文档字符串必须用三个双引号。

```python
"""这是我写的第一个Python文件"""

'张三'
18
65.2
```

## 3.2 变量与常量

### 3.2.1 **变量**

<details class="lake-collapse"><summary id="uf8b5f87e"><span class="ne-text">1️⃣</span><strong><span class="ne-text">前情回顾</span></strong></summary><p id="ud6485664" class="ne-p"><span class="ne-text">在上一节中，我们通过字面量的形式，记录了张三的体重，例如：</span></p><pre data-language="python" id="cwYQE" class="ne-codeblock language-python"><code>65.2</code></pre><p id="ucbd2f2a2" class="ne-p"><span class="ne-text">现在需要打印一些体重相关的内容，代码如下：</span></p><pre data-language="python" id="zQVsF" class="ne-codeblock language-python"><code>print('张三的体重是', 65.2)
print('对于', 65.2, '这个体重，张三觉得不满意')
print('张三决定开始减肥，希望体重比', 65.2, '还要小')</code></pre><div data-type="info" class="ne-alert"><p id="ub2bbea8e" class="ne-p"><strong><span class="ne-text">📌</span></strong><strong><span class="ne-text">小贴士：</span></strong></p><ol class="ne-ol"><li id="u9236dc1e" data-lake-index-type="0"><span class="ne-text">使用</span><code class="ne-code"><span class="ne-text">print(内容)</span></code><span class="ne-text">可以输出内容（也叫：打印内容）这里说的“打印”不是打印在纸上，而是指：把内容呈现在控制台上。</span></li><li id="ubd70ce30" data-lake-index-type="0"><span class="ne-text">使用</span><code class="ne-code"><span class="ne-text">print(内容1, 内容2, 内容3)</span></code><span class="ne-text">可以输出多个内容，不同内容之间用逗号做分隔，输出的多个内容默认会在同一行，且输出的多个内容之间会有一个空格。</span></li></ol></div><div data-type="tips" class="ne-alert"><p id="uaf38fabe" class="ne-p"><span class="ne-text">📋</span><strong><span class="ne-text">备注：</span></strong><code class="ne-code"><span class="ne-text">print()</span></code><span class="ne-text">还有很多使用细节和技巧，后面会逐步介绍。</span></p></div><p id="u82c0b596" class="ne-p"><span class="ne-text">我们会发现，代码中的</span><code class="ne-code"><span class="ne-text">65.2</span></code><span class="ne-text">被使用了 3 次，当要修改张三的体重为</span><code class="ne-code"><span class="ne-text">64.2</span></code><span class="ne-text">时，就需要手动修改 3 个地方，修改起来会很麻烦，就像下面这样：</span></p><pre data-language="python" id="k5GmH" class="ne-codeblock language-python"><code>print('张三的体重是', 64.2)
print('对于', 64.2, '这个体重，张三觉得不满意')
print('张三决定开始减肥，希望体重比', 64.2, '还要小')</code></pre></details>
<details class="lake-collapse"><summary id="udbaa343f"><strong><span class="ne-text">2️⃣</span></strong><strong><span class="ne-text">什么是变量？</span></strong></summary><p id="u2769ee5a" class="ne-p"><span class="ne-text">变量是数据的“代号”它可以和数据建立绑定关系，通过变量可以使用数据，或更新数据，之所以叫变量，是因为：它和某个值的绑定关系，可以随时改变。</span></p><p id="u2a4cdd43" class="ne-p"><span class="ne-text">例如：在上述代码中，我们可以把体重值和某个『</span><strong><span class="ne-text" style="color: #AD1A2B">变量</span></strong><span class="ne-text">』建立一个『</span><strong><span class="ne-text" style="color: #AD1A2B">绑定关系</span></strong><strong><span class="ne-text">』</span></strong><span class="ne-text">，以后用到体重的时候，直接“呼唤”这个变量就可以了。</span></p></details>
<details class="lake-collapse"><summary id="u953a2d22"><span class="ne-text">3️⃣</span><strong><span class="ne-text">具体语法</span></strong></summary><p id="u23315327" class="ne-p"><span class="ne-text">语法为：</span><code class="ne-code"><span class="ne-text">变量名 = 值</span></code><span class="ne-text">，例如下面代码中的：</span><code class="ne-code"><span class="ne-text">name</span></code><span class="ne-text">、</span><code class="ne-code"><span class="ne-text">age</span></code><span class="ne-text">、</span><code class="ne-code"><span class="ne-text">weight</span></code><span class="ne-text">都是变量。</span></p><pre data-language="python" id="zS4o7" class="ne-codeblock language-python"><code>name = '张三'
age = 18
weight = 65.2</code></pre><div data-type="color4" class="ne-alert"><p id="uf8817712" class="ne-p"><span class="ne-text">📢</span><strong><span class="ne-text" style="color: #AD1A2B">注意：</span></strong><span class="ne-text">变量名不需要加引号！</span></p></div></details>
<details class="lake-collapse"><summary id="u375e440e"><span class="ne-text">4️⃣</span><strong><span class="ne-text">示例代码</span></strong></summary><p id="uaf98c2dc" class="ne-p"><span class="ne-text">使用</span><code class="ne-code"><span class="ne-text">weight</span></code><span class="ne-text">变量存储体重值，并在后续代码中，多次使用</span><code class="ne-code"><span class="ne-text">weigth</span></code><span class="ne-text">变量。</span></p><pre data-language="python" id="HIxGR" class="ne-codeblock language-python"><code>weight = 65.2
print('张三的体重是', weight)
print('对于', weight, '这个体重，张三觉得不满意')
print('张三决定开始减肥，希望体重比', weight, '还要小')

需要修改体重时，通过weight就可以修改，修改后再去使用weight时，就是修改后的值了。

weight = 65.2
weight = 64.2

print('张三的体重是', weight)
print('对于', weight, '这个体重，张三觉得不满意')
print('张三决定开始减肥，希望体重比', weight, '还要小')

<details class="lake-collapse"><summary id="u41357e05"><span class="ne-text">5️⃣</span><strong><span class="ne-text">几个关键点</span></strong></summary><div data-type="info" class="ne-alert"><ol class="ne-ol"><li id="u1a5c4ca6" data-lake-index-type="0"><span class="ne-text">在数学中，像 </span><code class="ne-code"><span class="ne-text">1 + 1 = 2</span></code><span class="ne-text">这样的等式表示：等号左边的</span><code class="ne-code"><span class="ne-text">1 + 1</span></code><span class="ne-text">是具体的运算过程，等号右边的</span><code class="ne-code"><span class="ne-text">2</span></code><span class="ne-text">是该运算的结果。</span></li><li id="ubac9f55b" data-lake-index-type="0"><span class="ne-text">在代码</span><code class="ne-code"><span class="ne-text">age = 18</span></code><span class="ne-text">中，等号表示：将等号</span><strong><span class="ne-text" style="color: #117CEE">右侧的值</span></strong><span class="ne-text">与</span><strong><span class="ne-text" style="color: #117CEE">左侧的变量</span></strong><span class="ne-text">建立</span><strong><span class="ne-text" style="color: #117CEE">绑定关系</span></strong><span class="ne-text">。因此，当程序中需要表示年龄 </span><code class="ne-code"><span class="ne-text">18</span></code><span class="ne-text">时，可以使用变量</span><code class="ne-code"><span class="ne-text">age</span></code><span class="ne-text">；同样，也可以通过</span><code class="ne-code"><span class="ne-text">age</span></code><span class="ne-text">来修改该数值。</span></li><li id="u22cebfb5" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">age = 18</span></code><span class="ne-text">这一行代码也被称为“赋值语句”，意思是将右侧的</span><code class="ne-code"><span class="ne-text">18</span></code><span class="ne-text">赋给变量</span><code class="ne-code"><span class="ne-text">age</span></code><span class="ne-text">。</span></li><li id="ua444b03a" data-lake-index-type="0"><span class="ne-text">在 Python 中，变量的创建与赋值是同时完成的。也就是说，当程序中出现一个变量时，它必须立即与某个值建立绑定关系。</span></li><li id="ud004dd35" data-lake-index-type="0"><span class="ne-text">变量名不应过于随意，命名时需要遵守一定的规则（具体命名规则将在下一小节讲解）。</span></li></ol></div></details>

### **3.2.2 标识符命名规则**

<details class="lake-collapse"><summary id="ue686e2e1"><span class="ne-text">1️⃣</span><strong><span class="ne-text">什么是标识符？</span></strong></summary><p id="u602551a7" class="ne-p"><span class="ne-text">在程序中我们给： 变量、函数、类.....所起的</span><strong><span class="ne-text" style="color: #117CEE">名字</span></strong><span class="ne-text">，统称为</span><strong><span class="ne-text" style="color: #117CEE">标识符</span></strong><span class="ne-text">，即：在程序中所有我们可以自己起的名字，都是标识符。</span></p></details>
<details class="lake-collapse"><summary id="u2cb016cb"><strong><span class="ne-text">2️⃣</span></strong><strong><span class="ne-text">标识符命名规则如下：</span></strong></summary><div data-type="info" class="ne-alert"><ol class="ne-ol"><li id="uc06150f2" data-lake-index-type="0"><span class="ne-text">只能包含：数字、字母、下划线，且</span><strong><span class="ne-text" style="color: #AD1A2B">不能</span></strong><span class="ne-text">以数字开头，</span><strong><span class="ne-text" style="color: #AD1A2B">不能</span></strong><span class="ne-text">包含空格。</span></li><li id="u13e1d263" data-lake-index-type="0" style="text-align: justify"><span class="ne-text">区分大小写，即</span><code class="ne-code"><span class="ne-text">Name</span></code><span class="ne-text">和</span><code class="ne-code"><span class="ne-text">name</span></code><span class="ne-text">是两个不同的标识符。</span></li><li id="u2fca3be8" data-lake-index-type="0" style="text-align: justify"><strong><span class="ne-text" style="color: #AD1A2B">不能</span></strong><span class="ne-text">使用关键字（关键字的解释在下面</span><span class="ne-text">⬇️</span><span class="ne-text">）。</span></li><li id="u52fedc9e" data-lake-index-type="0" style="text-align: justify"><span class="ne-text">标识符尽量</span><strong><span class="ne-text" style="color: #AD1A2B">不要</span></strong><span class="ne-text">与内置函数同名。</span></li><li id="u304cfb28" data-lake-index-type="0" style="text-align: justify"><span class="ne-text">标识符虽然没有长度限制，但应追求：简洁清晰，具有描述性。</span></li></ol></div></details>
<details class="lake-collapse"><summary id="ud44f93f2"><strong><span class="ne-text">3️⃣</span></strong><strong><span class="ne-text">Python 中的关键字</span></strong></summary><p id="uaf73d4ee" class="ne-p"><span class="ne-text">所谓“关键字”，是指那些：已被 Python 语言预先保留、具有特定含义和功能的标识符。这些关键字被系统征用，因而不能再作为变量名、函数名或其他标识符使用。</span></p><pre data-language="python" id="V97s9" class="ne-codeblock language-python"><code>False     	None      	True      	and       	as
assert    	async     	await     	break     	class
continue  	def       	del       	elif      	else
except    	finally   	for       	from      	global
if        	import    	in        	is        	lambda
nonlocal  	not       	or        	pass      	raise
return    	try       	while     	with      	yield</code></pre><div data-type="tips" class="ne-alert"><p id="u866aa617" class="ne-p"><span class="ne-text">📋</span><strong><span class="ne-text">备注：</span></strong><span class="ne-text">上述关键字暂不作详细说明。随着课程的推进，我们会在实际讲解中逐步接触并使用这些关键字，届时再进行深入解释。初学者无需在此阶段强行记忆（这也并不现实），随着使用频率的增加，便会在后续学习中自然掌握。</span></p></div></details>
<details class="lake-collapse"><summary id="u25d1a38a"><strong><span class="ne-text">4️⃣</span></strong><strong><span class="ne-text">常见的三种命名风格</span></strong><span class="ne-text"></span></summary><div data-type="info" class="ne-alert"><ol class="ne-ol"><li id="uf709aa20" data-lake-index-type="0" style="text-align: justify"><strong><span class="ne-text" style="font-size: 15px">大驼峰</span></strong><span class="ne-text" style="font-size: 15px">（UpperCamelCase）: 每个单词的首字母大写，例如：</span><code class="ne-code"><span class="ne-text" style="font-size: 15px">UserName</span></code></li><li id="u512b7a5e" data-lake-index-type="0" style="text-align: justify"><strong><span class="ne-text" style="font-size: 15px">小驼峰</span></strong><span class="ne-text" style="font-size: 15px">（lowerCamelCase）: 首词的首字母小写，后面单词首字母大写，例如：</span><code class="ne-code"><span class="ne-text" style="font-size: 15px">userName</span></code></li><li id="u829b6d94" data-lake-index-type="0" style="text-align: justify"><strong><span class="ne-text" style="font-size: 15px">蛇形</span></strong><span class="ne-text" style="font-size: 15px">（snake_case）：单词间用下划线连接，例如：</span><code class="ne-code"><span class="ne-text" style="font-size: 15px">user_name</span></code></li></ol><p id="ub1e87c46" class="ne-p"><span class="ne-text">💡</span><span class="ne-text" style="font-size: 15px">Python 中推荐使用『蛇形（snake_case）』写法。</span></p></div><p id="u9a4f372c" class="ne-p"><span class="ne-text">举几个例子：</span></p><p id="u0a33102a" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1747039497952-2b73656f-739b-4735-b758-db370ffecbc5.png" width="614.7000122070312" title="" crop="0,0,1,1" id="c7HWd" class="ne-image"></p></details>

### 3.2.3 常量

<details class="lake-collapse"><summary id="ub0530e0d"><span class="ne-text">1️⃣</span><strong><span class="ne-text">什么是常量？</span></strong></summary><p id="u3c6dba3b" class="ne-p"><span class="ne-text">在程序中一旦被赋值，就</span><strong><span class="ne-text" style="color: #ad1a2b">不希望</span></strong><span class="ne-text">被修改的量（区别于变量）。</span></p></details>
<details class="lake-collapse"><summary id="u57ad8e45"><span class="ne-text">2️⃣</span><strong><span class="ne-text">具体语法</span></strong></summary><p id="uf5b8a08b" class="ne-p"><span class="ne-text">Python 中一般约定使用</span><strong><span class="ne-text" style="color: #ad1a2b">全大写</span></strong><span class="ne-text">变量名来表示常量，涉及到多个单词时，用下划线做分隔。</span></p><pre data-language="python" id="LWdRU" class="ne-codeblock language-python"><code>ADULT_AGE = 18
MONTHS_IN_YEAR = 12
MAX_USERS = 1200
PASSING_SCORE = 60
MAX_USERS = 1300</code></pre></details>
<details class="lake-collapse"><summary id="u83ca1765"><strong><span class="ne-text">3️⃣</span></strong><strong><span class="ne-text">Python 中没有强制的常量机制</span></strong></summary><p id="ubb33dd09" class="ne-p"><span class="ne-text">当强制对常量进行修改时，最终也能改掉，但要自觉不改，这是 Python 程序员之间的约定。</span></p><pre data-language="python" id="hHRin" class="ne-codeblock language-python"><code>MONTHS_IN_YEAR = 12
print(MONTHS_IN_YEAR)
MONTHS_IN_YEAR = 13
print(MONTHS_IN_YEAR)

## 3.3 **注释**

### 3.3.1 概述

注释是对代码的**<font style="color:#AD1A2B;">备注</font>**和**<font style="color:#AD1A2B;">解释</font>**，在代码执行的时，通常不起任何作用。

### 3.3.2 **注释的作用**

注释的核心作用如下：


1. 提高代码的可读性，通常用来辅助程序员快速理解代码的逻辑。
2. 屏蔽掉暂时不需要的代码。


📢**<font style="color:#AD1A2B;">注意：</font>**在代码中编写清晰易懂的注释，是程序员的基本素养之一！

### 3.3.3 **单行注释**

在 Python 中`#`后的一行内内容，会被视为注释。

```python
# name 是张三的名字
name = '张三'
# age 是张三的年龄
age = 18
# weight 是张三的体重（单位：kg）
weight = 65.2

print(name, age, weight)  # 这是一句打印
```

关于注释的书写格式：


1. Python官方建议：在`**#**`和注释的内容之间加一个空格，在代码和`**#**`之间加两个空格。
2. 上述的规则属于 Python 编码规范，规范的具体内容，我们会在课程中逐渐给各位渗透。



### **3.3.4 多行注释**

多行注释又称“块注释” ，Python 中的多行注释使用的是一组三引号（单引号，双引号都可以）**。**

<details class="lake-collapse"><summary id="uf914a509"><span class="ne-text">1️⃣</span><span class="ne-text">多行注释可以换行，但不能嵌套。</span></summary><pre data-language="python" id="JfTkw" class="ne-codeblock language-python"><code>&quot;&quot;&quot;
我是一些注释
我还是一些注释
&quot;&quot;&quot;</code></pre></details>
<details class="lake-collapse"><summary id="u4f524ffa"><span class="ne-text">2️⃣</span><span class="ne-text">多行注释本质是一个多行字符串。</span></summary><div data-type="color4" class="ne-alert"><p id="u84703909" class="ne-p"><strong><span class="ne-text" style="color: #AD1A2B">📢</span></strong><strong><span class="ne-text" style="color: #AD1A2B">注意：</span></strong><span class="ne-text">Python 中并没有真正的多行注释语法，所谓多行注释的本质其实还是字符串。</span></p></div><pre data-language="python" id="zooRv" class="ne-codeblock language-python"><code>print(
    &quot;&quot;&quot;
    Hello World
    Hello world
    &quot;&quot;&quot;
)</code></pre></details>

###  3.3.5 文件编码注释

文件编码又称“字符编码”，文件编码注释写在 Python 文件的首行，是一种特殊的注释。

它的作用是：指定当前文件的字符编码。  

```python
# coding=utf-8
print('你好啊！')
```

## 3.4 字符编码

### 3.4.1 概述

计算机对数据会进行两个常见的操作，分别是：存储数据、读取数据。

- 存储数据时，计算机会进行编码
- 读取数据时，计算机会进行解码
  ![](D:\workspace\IdeaProjects\learn\python\python3\image\1760676361078-7e14538b-66e1-4d50-a783-b89394156146.png)


编码与解码，会遵循一定的规范，这个规范就是**<font style="color:#AD1A2B;">字符编码</font>**，并且编码与解码，必须遵循相同的编码规范，若所用的规范不一致，就会出现乱码。

```python
# coding=iso-8859-1
print('你好啊！')  # ä½ å¥½åï¼
```

### 3.4.2 常见编码方式

1. `ASCII`：大写字母、小写字母、数字、一些符号，共计 28个字符。
2. `ISO 8859-1`：在`ASCII`基础上扩展，支持西欧语言，共计 256 个字符。
3. `GB2312`：中国国家编码标准，收录约 6763 个简体中文常用汉字和符号。(GB 国标的意思)
4. `GBK`：兼容`GB2312`，进一步扩展，支持简繁体中文和其他汉字，共收录 2 万多个字符。(GBK 国标扩展的意思)
5. UTF-8：国际通用的编码格式，也叫“**万国码**”，支持世界所有语言的字符，包括：中文、英文、阿拉伯文、日文、韩文等，向下兼容`ASCII`，是现代互联网最常用的编码格式。

✅**最佳实践：**实际开发中，几乎都采用`UTF-8`编码保存文件。


**📋备注**：在 Python3 中，可以不写文件编码声明，因为 Python3 默认就使用`UTF-8`编码。



## 数据类型

### 概述

就像生活中的物品，都有自己所属的分类一样，数据也有自己所属的**『数据类型』**。

例如之前写过的这段代码：

```python
'张三'
18
65.2

"李四"
22
74.6
```

在上述代码中：


✅`'张三'`、`"李四"`这两个字面量，属于『**<font style="color:#AD1A2B;">字符串</font>**』类型。

✅`18`、`22` 这两个字面量，属于『**<font style="color:#AD1A2B;">整数</font>**』类型。

✅`65.2`、`74.6`这两个字面量，属『**<font style="color:#AD1A2B;">浮点数</font>**』类型。



三种最常见的数据类型：

|  类型名称  |                      英文名                      | 举例                   | 说明                   |
| :--------: | :----------------------------------------------: | ---------------------- | ---------------------- |
|  **整型**  |                      `int`                       | `5`, `-3`, `0`, `2025` | 整数（不带小数点的数） |
| **浮点型** |                     `float`                      | `3.14`, `-0.01`        | 带小数点的数           |
| **字符串** | `**<font style="color:#DF2A3F;">str</font>**ing` | `"Hello"`, `'Python'`  | 文本，要用引号包起来   |



📋**备注**：数据类型不只上述的这三种，还有很多种，我们暂且先知道以上这三种即可，其他数据类型会在后续章节中逐步讲解。



### 查看数据类型

通过`type()`可以查看数据类型，`type()`会返回当前数据的具体类型。

```python
# 使用变量接收 type() 返回的类型
result1 = type('张三')
result2 = type(18)
result3 = type(72.5)

print(result1) # <class 'str'> 注意此处返回的不是string，是 string 的简写：str
print(result2) # <class 'int'>
print(result3) # <class 'float'>
```


📢**<font style="color:#AD1A2B;">注意：</font>**在**<font style="color:#DF2A3F;"> </font>**Python 中：变量无类型，数据有类型。

例如`a = 10`，其中`a`是没有类型的，但`a`所关联的数据`10`是有类型的，`10`是整型，我们经常说`a`是整型，其实是一种不太严谨的表述，严谨的表述应该是：`a`所对应的数据`10`是整型。



也可以把变量交给`type()`，最终返回的是：变量所对应的数据的类型。

```python
name = '张三'
age = 18
weight = 72.5

# 使用变量接收type()返回的类型
result1 = type(name)
result2 = type(age)
result3 = type(weight)

# 打印这三个数据类型
print(result1)  # <class 'str'>
print(result2)  # <class 'str'>
print(result3)  # <class 'float'>
```

当然也可以不使用变量接收，直接打印`type()`的结果

```python
name = '张三'
age = 18
weight = 72.5

# 打印这三个数据类型
print(type(name))  # <class 'str'>
print(type(age))  # <class 'str'>
print(type(weight))  # <class 'float'>
```

### 整型

<details class="lake-collapse"><summary id="u29e28f94"><span class="ne-text">1️⃣</span><strong><span class="ne-text">什么是整型？</span></strong></summary><p id="u67b2dde4" class="ne-p"><span class="ne-text">所谓整型就是没有小数点的数字， Python 中的整型，可以是</span><strong><span class="ne-text" style="color: #AD1A2B">任意大小</span></strong><span class="ne-text">的整数，包括负整数。</span></p></details>
<details class="lake-collapse"><summary id="uea08bdd6"><span class="ne-text">2️⃣</span><strong><span class="ne-text">分隔符</span></strong></summary><p id="u1d7e118f" class="ne-p"><span class="ne-text">当书写很大的数时，可使用下划线将数字分组，使其更清晰易读；Python 自动忽略数字之间的下划线，并且这种写法也适用于浮点数，但要注意：此种写法只有 Python3.6 及以上版本才支持。</span></p><pre data-language="python" id="cEmft" class="ne-codeblock language-python"><code>num1 = 10_000_000
print(num1)</code></pre></details>
<details class="lake-collapse"><summary id="u3cfc84b4"><span class="ne-text">3️⃣</span><strong><span class="ne-text">整型上限值</span></strong></summary><p id="u40f82dea" class="ne-p"><span class="ne-text">Python 中存储整数上限值的大小取决于：计算机的内存和处理能力，我们先来认识一下『幂运算符』，代码如下：</span></p><pre data-language="python" id="pPdt5" class="ne-codeblock language-python"><code>a = 3 ** 2  # 表示3的平方
b = 2 ** 3  # 表示2的3次方


print(a)  # 9
print(b)  # 8</code></pre><p id="u555ec46f" class="ne-p"><span class="ne-text">通过幂运算，构建一个很大的数，随后打印它，我们会发现：代码报错了。</span></p><pre data-language="python" id="lEaMc" class="ne-codeblock language-python"><code>a = 9 ** 9999  # 9的9999次方
print(a)  # 打印x</code></pre><p id="ude34927c" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1760848408483-b9e815b6-3bcf-4ae1-99d5-d7653b784433.png" width="1189.6969009343406" title="" crop="0,0,1,1" id="z7m4V" class="ne-image"></p><p id="u533d2353" class="ne-p"><span class="ne-text">上面报错中提及了</span><code class="ne-code"><span class="ne-text" style="color: #DF2A3F">&quot;Exceeds the limit (4300 digits)&quot;</span></code><span class="ne-text">，但这并不代表 Python 最大只能表示</span><code class="ne-code"><span class="ne-text">4300</span></code><span class="ne-text">位的数，比如我们把</span><code class="ne-code"><span class="ne-text">print</span></code><span class="ne-text">删掉，会发现代码正常运行，并且此时的</span><code class="ne-code"><span class="ne-text">a</span></code><span class="ne-text">也是可以正常参与数学运算的。</span></p><pre data-language="python" id="Xp8Ur" class="ne-codeblock language-python"><code>a = 9 ** 9999  # 9的9999次方
b = a + 100</code></pre><p id="u90c54e8e" class="ne-p"><span class="ne-text">那加上了</span><code class="ne-code"><span class="ne-text">print(a)</span></code><span class="ne-text">为什么报错呢？原因如下：</span></p><div data-type="info" class="ne-alert"><p id="u2c7cd3f9" class="ne-p"><span class="ne-text">调用</span><code class="ne-code"><span class="ne-text">print(a)</span></code><span class="ne-text">时，Python 底层会把</span><code class="ne-code"><span class="ne-text">a</span></code><span class="ne-text">的类型转换成『字符串类型』再输出，而从 Python3.11 起，Python 对超大整数转换字符串的长度进行了限制，默认位数是</span><code class="ne-code"><span class="ne-text">4300</span></code><span class="ne-text">位。</span></p></div><p id="u26b7c149" class="ne-p"><strong><span class="ne-text">💡</span></strong><strong><span class="ne-text">扩展知识（了解即可）</span></strong><span class="ne-text">：</span></p><p id="u16d98930" class="ne-p"><span class="ne-text">通过如下代码，可以解除字符串转换时的</span><code class="ne-code"><span class="ne-text">4300</span></code><span class="ne-text">位限制，如下代码中包含模块相关内容，我们还没有讲到，所以不必纠结下面代码的具体含义，只需要先知道：</span><code class="ne-code"><span class="ne-text">4300</span></code><span class="ne-text">位的限制可以修改即可。</span></p><pre data-language="python" id="wUMcD" class="ne-codeblock language-python"><code>import sys
sys.set_int_max_str_digits(0) # 设置为0表示不作任何限制

x = 9 ** 9999  # 9的9999次方
print(x)  # 打印x</code></pre></details>

### 浮点型 

<details class="lake-collapse"><summary id="ucb8cc8af"><span class="ne-text">1️⃣</span><strong><span class="ne-text">什么是浮点型？</span></strong></summary><p id="ub0adf038" class="ne-p"><span class="ne-text">所谓浮点型，就是带小数点的数字，比如：</span><code class="ne-code"><span class="ne-text">3.14</span></code><span class="ne-text">、</span><code class="ne-code"><span class="ne-text">-0.5</span></code><span class="ne-text">、</span><code class="ne-code"><span class="ne-text">2.0</span></code><span class="ne-text">都是浮点数。</span></p></details>
<details class="lake-collapse"><summary id="u6222721e"><span class="ne-text">2️⃣</span><strong><span class="ne-text">浮点型的表示方式</span></strong></summary><ol class="ne-ol"><li id="u7c277a13" data-lake-index-type="0"><span class="ne-text">直接写</span></li></ol><pre data-language="python" id="tZEhv" class="ne-codeblock language-python"><code># 浮点型就是带有小数点的数字。
weight = 65.2
balance = 1425.58
out_temp = -25.2
price = 120.0</code></pre><ol start="2" class="ne-ol"><li id="ub4d2488b" data-lake-index-type="0"><span class="ne-text">科学计数法</span></li></ol><pre data-language="python" id="Y1lYF" class="ne-codeblock language-python"><code># 浮点型的科学计数法表示。
speed_of_sound = 3.4e+2  # 3.4乘以10的2次方。
world_population = 7.8e9  # 7.8乘以10的9次方。
distance_sun_earth = 1.496E8  # 1.496乘以10的8次方。
speed_of_light = 2.998E+8  # 2.998乘以10的8次方。


one_ml = 1e-3  # 1乘以10的-3次方。
one_mg = 1E-3  # 1乘以10的-3次方。</code></pre></details>

### 字符串

#### 1️⃣**字符的<font style="color:#AD1A2B;">四种</font>定义方式**

| 写法       | 示例                  | 适用场景                                       |
| ---------- | --------------------- | ---------------------------------------------- |
| 单引号     | `'你好，尚硅谷'`      | 单行字符串（不能直接换行，换行需要使用圆括号） |
| 双引号     | `"你好，尚硅谷"`      |                                                |
| 三个单引号 | `'''你好，尚硅谷'''`  | 多行字符串（可以直接换行）                     |
| 三个双引号 | `"""你好，尚硅谷""""` |                                                |


下面代码所表示的都是字符串：

```python
# 单引号和双引号的写法是等价的，二者都不能直接换行（要用圆括号才能换行），单引号用的多。
message1 = '尚硅谷，让天下没有难学的技术!'
message2 = "尚硅谷，让天下没有难学的技术!"

# 三个单引号的写法，可以直接换行，并且可以作为多行注释使用。
message3 = '''尚硅谷，让天下没有难学的技术!'''

# 三个双引号的写法，可以直接换行，也可以作为多行注释使用，还能作为文档字符串使用。
message4 = """尚硅谷，让天下没有难学的技术!"""
```

#### **2️⃣字符串的格式化输出**

<details class="lake-collapse"><summary id="u7ff11608"><strong><span class="ne-text">写法 1</span></strong><span class="ne-text">：直接用加号进行拼接，写起来很麻烦，而且只能是字符串之间拼接。</span></summary><pre data-language="python" id="uwtCk" class="ne-codeblock language-python"><code>name = '张三'
gender = '男'
weight = 65.2
age = 12


info1 = '我叫' + name + '，我是' + gender + '生'</code></pre></details>

<details class="lake-collapse"><summary id="u24e35aca"><strong><span class="ne-text">写法 2</span></strong><span class="ne-text">：使用占位符。</span></summary><p id="u4eeed75b" class="ne-p"><span class="ne-text">具体规则：</span></p><ul class="ne-ul"><li id="u55a6cf70" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">%s</span></code><span class="ne-text">占位字符串</span></li><li id="u79f6bc34" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">%f</span></code><span class="ne-text">占位浮点数</span></li><li id="u5a6c8265" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">%i</span></code><span class="ne-text">占位整数</span></li><li id="u6f29446d" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">%d</span></code><span class="ne-text">占位十进制的整数</span></li><li id="u02eddff6" data-lake-index-type="0"><code class="ne-code"><span class="ne-text">%s</span></code><span class="ne-text">是万能的（如果我们提供的数据不是字符串，那 Python 就会把数据转成字符串）。</span></li></ul><pre data-language="python" id="GvI4H" class="ne-codeblock language-python"><code>name = '张三'
gender = '男'
weight = 65.2
age = 12


info2 = '我叫%s，我是%s生，我体重是%f，年龄是%d' % (name, gender, weight, age)</code></pre></details>

<details class="lake-collapse"><summary id="u87bf979d"><strong><span class="ne-text">写法 3</span></strong><span class="ne-text">：使用 f-string，这是目前 Python 最推荐的方式。</span></summary><pre data-language="python" id="I4SRG" class="ne-codeblock language-python"><code>name = '张三'
gender = '男'
weight = 65.2
age = 12


info3 = f'我叫{name}，我是{gender}生，我体重是{weight}，年龄是{age}'</code></pre></details>

#### 3️⃣**占位符精度控**

在占位符前方，可以使用`m.n`的形式来指定精度，具体规则见下图：

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760683740087-1061a968-bbf5-4cb2-8f29-4371145a7656.png)

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760683810967-f13c72b1-b5f4-4484-9cd7-dd5a960a2954.png)

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760683825654-121576f3-7ef3-40e7-bfcf-a1123a89c785.png)

示例代码：

```python
info = '我叫%-4.1s，性别是%3.2s，体重是%-9.3f，年龄是%-6.4d' % (name, gender, weight, age)
```

#### 4️⃣**转义字符**

在字符串中，有些字符不能直接写（换行、制表符、引号等）这时就要使用转义字符。

例如下面的`message`字符串中包含了一个单引号，但如果就这样直接写，就会报错

```python
print('在Python中，可以使用'包裹一个字符串')
```

使用转义字符后，即可正常输出：

```python
print('在Python中，可以使用\'包裹一个字符串')
```

常见的转义字符梳理：

| 转义字符 | 表示的含义                                 |
| -------- | ------------------------------------------ |
| `\'`     | `'`                                        |
| `\"`     | `"`                                        |
| `\n`     | 换行                                       |
| `\\`     | `\`                                        |
| `\b`     | 删除前一个字符                             |
| `\r`     | 使光标回到本行开头，覆盖输出               |
| `\t`     | 表示水平制表符（让光标跳转到下一个制表位） |


测试代码：

```python
# 使用 \' 输出 '
print('在Python中，可以使用\'包裹一个字符串')

# 使用 \" 输出 "
print("在Python中，可以使用\"包裹一个字符串")

# 使用 \n 进行换行
print('注册会员需要以下信息：\n姓名\n年龄\n手机号')

# 使用 \\ 输出 \
print('D:\\nice')

# 使用 \b 删除前一个字符
print('helloo\b')

# 使用 \r 使光标回到本行开头，覆盖输出
print('67%\r68%')

# 使用 \t 表示水平制表符（让光标跳转到下一个制表位）
# 一个制表位到底是几位，是不确定的，但我们可以通过在字符串后面加.expandtabs()来指定位数。
print('1234123412341234')
print('ab\tcd.expandtabs(4)')
print('abc\td.expandtabs(4)')
print('abcd\ta.expandtabs(4)')
print('我是\t中文.expandtabs(4)')

print('12341234123412341234')
print('姓名\t性别\t年龄')
print('张三\t男\t\t18')
print('李四\t女\t\t25')
print('王五\t男\t\t32')
```

## 数据类型转换

### 概述

何为数据类型转换？—— 把一种类型的数据，变成另一种类型。

### 为什么要数据类型转换

例如下面这些场景中，我们得到的数据类型，和最终要用的数据类型是不一致的，那就需要类型转换：


1. 用户输入的内容是都是字符串，若需要进行数学运算，就必须进行数据类型转换。
2. 对文件进行写入操作时，要将其他类型的数据转为字符串。
3. 从数据库中读取出的内容都是字符串若需要进行数学运算，也需要数据类型转换

......



### 具体转换方式

通过以下函数，可以对数据类型进行转换

| **函数** | **说明** | **示例** |
| --- | --- | --- |
| **<font style="color:black;">int(x)</font>** | <font style="color:black;">将x转换为一个整数</font> | <!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760685873454-38c2d74a-8af0-414a-b15d-22bbe4340282.png) |
| **float(x)** | 将x转换为一个浮点数 | <!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760685983761-4fc9e5ee-42b8-4777-adc6-f5d27e540d26.png) |
| **str(x)** | 将对象x转换为一个字符串 | <!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760686026401-a1c44b32-3eab-4357-8638-e36465f34a5f.png) |


## **运算符**

### **算数运算符**

常用的算数运算符如下：

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760841632719-f732761c-8094-47b7-8dc2-003b6c4112ca.png)

测试代码

```python
# 加
print(9 + 7)
# 减
print(7 - 2)
# 乘
print(3 * 4)
# 除
print(9 / 3)
# 取整
print(9 // 6)
# 取余
print(9 % 6)
# 指数
print(2 ** 3)
```

### **赋值运算符**

常用的赋值运算符如下：

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760686233255-90bec728-4fda-4a50-9e1c-338b541b2438.png)

测试代码：

```python
age = 18
print(age)

price = 52
print(price)
```

```python
age = 18

# 加法复合运算符
age += 1  # 等价于：age = age + 1
print(age)

# 减法复合运算符
age = 18
age -= 1  # 等价于：age = age - 1
print(age)

# 乘法复合运算符
price = 100
discount = 0.8
price *= discount  # 等价于：price = price * discount
print(price)

# 除法复合运算符
pay = 100
num = 5
pay /= 5  # 等价于：pay = pay / num
print(pay)

# 取整赋值运算符
apple = 31
num = 14
apple //= num  # 等价于：apple = apple // num
print(apple)

# 取模赋值运算符
seconds = 386
minutes = 60
seconds %= minutes  # 等价于：seconds = seconds % minutes
print(seconds)

# 指数赋值运算符
a = 2
b = 3
a **= b  # 等价于：a = a ** b
print(a)
```

### **比较运算符**

常用的比较运算符如下：

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760841758156-fabfcb66-072e-4a4e-91c4-4c69063c084e.png)


📋**备注**：True 和 False 是布尔类型，会在下一小节讲，暂且先知道：True 表示真，False 表示假。



```python
# 使用==判断左右两侧是否相等
a = 5
b = 7
c = '5'
result = a == c
print(result)

# 使用!=判断左右两侧是否不等
a = 5
b = 7
c = '5'
result = a != c
print(result)

# 使用>判断左侧是否大于右侧
a = 9
b = 7
c = '5'
result = a > b
print(result)

# 使用<判断左侧是否小于右侧
a = 3
b = 7
c = '5'
result = a < b
print(result)

# 使用>=判断左侧是否大于等于右侧
a = 6
b = 7
c = '5'
result = a >= b
print(result)

# 使用<=判断左侧是否小于等于右侧
a = 9
b = 7
c = '5'
result = a <= b
print(result)

# 以上这些比较运算符，同样适用于字符串
msg1 = 'abc'
msg2 = 'abc666'
print(msg1 == msg2)

msg1 = 'abc'
msg2 = 'abc'
print(msg1 != msg2)
```


**📌小贴士：**

+ 字符串进行比较时，是依次比较每个字符的 Unicode 编码。
+ Unicode 编码是一种全球通用的字符编码标准，它会给每个字符都分配一个“身份证号”。  



具体比较规则是：


1. 从左到右，依次比较两个字符串中的字符。
2. 先比较第一个字符：
   - 如果两个字符不相等，就直接根据它们的 Unicode 码值比较大小。
   - 如果相等，则继续下一步。
3. 继续比较下一个字符，依次往后进行，直到遇到不相等的字符为止。
4. 当出现不相等的字符时，比较它们的 Unicode 码大小，后续的字符将不再参与比较。

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760688683540-8b0ab28b-5d77-4128-805b-535f25a816d9.png)



```python
# 使用ord()查看指定字符的Unicode编码
print(ord('a'))
print(ord('我'))

# 使用chr()将Unicode编码转为字符
print(chr(97))
print(chr(25105))

msg1 = 'abc'
msg2 = 'xyz'
msg3 = '我爱你'
msg4 = '中国'
msg5 = 'abc'
msg6 = 'abcdef'
print(msg3 <= msg1)
```

### 布尔类型

我们之前讲的这些类型：字符串、整型、浮点型，这些类型中，每一种类型都有无限多的具体值。

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760689095171-e9774770-38be-4d1b-a117-70ad015b5084.png)

但布尔类型的具体值，只有两个，分别是：`True`和`False`，其中：`True`表示真，`False`表示假。

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760689122751-f2e8c455-920c-464c-8e3e-aaed34394f22.png)

布尔值常用于表示：条件是否成立、事件是否发生、操作是否成功、等逻辑状态。


📢**<font style="color:#AD1A2B;">注意：</font>**`True` 和 `False` 的首字母必须大写。  



```python
# 自己定义的布尔值
a = True
b = False

# 靠程序执行得到的布尔值
c = 5 > 3
d = 7 < 2

print(type(a), a)  # True
print(type(b), b)  # Flase
print(type(c), c)  # True
print(type(d), d)  # Flase
```

布尔类型是`int`类型的子类型，底层的本质是用`1`表示`True`，用`0`表示`False`。

```python
# 布尔类型是int类型的子类型，底层的本质是用1表示True，用0表示False
print(int(True))   # 1
print(int(False))  # 0

print(4 + True)   # 5
print(8 - False)  # 8

print(True + True)   # 2
print(True - False)  # 1

print(7 > True)    # True
print(False <= 0)  # True
```

Python中除`0`以外的任何数，转为布尔值后都为 True

```python
# 使用bool()将指定内容转为布尔类型
print(bool(1))  # True
print(bool(0))  # False

print(bool(300))  	# True
print(bool(25.6))  	# True
print(bool(1.8e3))  # True
print(bool(12_000)) # True
print(bool(-10))  	# True
```

Python中除空字符串以外的任何字符串，转为布尔值都是 True

```python
print(bool('hello')) # True
print(bool('0'))	 # True
print(bool('18.5'))	 # True
print(bool('-9'))	 # True
print(bool(''))	     # False
```

### **逻辑运算符**

常用的逻辑运算符如下：

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760842774737-97fad10e-bf66-4bc6-9918-513b0b2ba8ff.png)

1️⃣**and 运算符：**用于判断其两侧的值，是否都为`True`

```python
print(True and True)   # True
print(True and False)  # False
print(False and True)  # False
print(False and False) # False

print(8 > 7 and 8 > 7) # True
print(8 > 7 and 2 > 3) # False
print(2 > 3 and 8 > 7) # False
print(2 > 3 and 2 > 3) # False
```

`and`具备“逻辑短路”能力，以下代码中包含`3/0`这种错误代码，但最终没有报错。

```python
print(False and 3 / 0)  # False
print(3 > 9 and 3 / 0)  # False
```

`and`返回的不一定是布尔值，它返回的是某个参与计算的值本身，`and`会先看左边，如果左边是“假”，就直接返回左边，否则返回右边；若参与`and`运算的值不是布尔值，那 Python 会自动转为布尔值，然后再进行逻辑操作。

```python
print(2 - 2 and True) # 0
print('' and True)
print(True and 8 / 2)  # 4.0
print(3 + 3 and 3 * 4) # 12
```

2️⃣**or 运算符：**用于判断其两侧，是否至少有一个为True（只要有一个是True，那就返回True）

```python
print(True or True)    # True
print(True or False)   # True
print(False or True)   # True
print(False or False)  # False

print(9 > 2 or 9 > 2)  # True
print(9 > 2 or 3 < 1)  # True
print(3 < 1 or 9 > 2)  # True
print(3 < 1 or 3 < 1)  # False
```

`or`同样具备“逻辑短路”的能力，以下代码中包含`3/0`这种错误代码，但最终没有报错。

```python
print(True or 3 / 0) # True
print(9 > 3 or 3 / 0) # True
```

`or`返回的也不一定是布尔值，它返回的是参与计算的值本身，`or`会先看左边，如果左边为“真”，就直接返回左边，否则返回右边；若参与`or`运算的值不是布尔值，那 Python 会自动转为布尔值，然后再进行逻辑操作。

```python
print(7 - 2 or False)    # 5
print('你好' or '尚硅谷') # 你好
print(False or 8 / 2)	 # 4.0
print(2 - 2 or 3 * 4)    # 12
```

3️⃣**not 运算符：**`not`用于取反，不过要注意：如果参与`not`运算的值不是布尔值，那 Python 会自动将其转为布尔值，然后再进行逻辑操作。

```python
print(not True)  # False
print(not False) # True
print(not 3 > 2) # False
print(not 3 < 2) # True
```

`not`返回的值，一定是布尔值！

```python
print(not 0) 	  # True
print(not 3 > 2)  # False
print(not 9 // 4) # False
print(not 'abc')  # False
```

## 进制

### 概述

进制是指：用多少个符号，来表示数值的一种『记数方式』。比如我们平时使用的『十进制』，就是用`0 ~ 9`这十个符号来表示所有的数，而计算机中存储和运算的数据，都是二进制，常见的进制与规则如下：


**二进制**：`0 ~ 1`，满`2`进`1`。

**八进制**：`0 ~ 7`，满`8`进`1`。

**十进制**：`0 ~ 9`，满`10`进`1`。

**十六进制**：`0 ~ 9`，`A-F`，满`16`进`1`。




**📋备注：** 在十六进制中，除了`0 ~ 9`这十个数字外，还引入了字母，以便表示超过`9`的值，字母`A`对应十进制的`10`，字母`B`对应十进制的`11`，同理字母 `C`、`D`、`E`、`F` 分别对应十进制的：`12`、`13`、`14`、`15`。



各进制的表示如下图：

<!-- 这是一张图片，ocr 内容为： -->
![十进制数：9527](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760689897490-ff8343ae-f99c-4890-a68c-eb7f8efcffa1.png)<!-- 这是一张图片，ocr 内容为： -->
![二进制数：1010](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760689918264-dea18369-a570-45ec-84c7-5df68292e440.png)

<!-- 这是一张图片，ocr 内容为： -->
![八进制数：1034](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760690227879-a8ed7844-3c58-43db-b217-139a31d4154a.png)<!-- 这是一张图片，ocr 内容为： -->
![十六进制数：1CF](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760690187335-20e1de59-45d2-4f50-9b37-c5819de5169a.png)

### **代码中如何表示不同进制**

在 Python 中，不同进制的数，有不同的前缀：


**二进制**：以`0b`或`0B`开头表示。

**八进制**：以`0o`开头表示

**十进制**：无需前缀，正常编写即可。

**十六进制**：以`0x`或`0X`开头表示，此处的`A-F`不区分大小写。



```python
# 0b开头表示二进制
num1 = 0b11001
# 0o开头表示八进制
num2 = 0o1034
# 0x开头表示十六进制
num3 = 0x1cf
```


**📋备注：**Python 中所有的『非十进制』数字，只是代码层面的编写方式，只是给程序员看的，Python 在进行：计算、打印等操作时，会自动将这些『非十进制』数字，转为『十进制』数字。



```python
# 0b开头表示二进制
num1 = 0b11001
# 0o开头表示八进制
num2 = 0o1034
# 0x开头表示十六进制
num3 = 0x1cf

# Python 在对上面的 num1、num2、num3进行计算、打印等操作时，会自动将其转为十进制
print(num1, num2, num3)  # 25  540  463
print(num1 + 1)  # 26
print(str(num2)) # 540
print(num3 > 400) # True
```

### 不同进制之间的转换

1️⃣**手动转换：**使用连除法


十进制转二进制：不断用 2 去除这个数，直到商为 0，然后把每次的余数倒着写即可。

十进制转八进制：不断用 8 去除这个数，直到商为 0，然后把每次的余数倒着写即可。  

十进制转十六进制：不断用 16 去除这个数，直到商为 0，把每次的余数倒着写，若余数 `≥ 10`，则依次用 `A`、`B`、`C`、`D`、`E`、`F` 表示 `10~15`。



<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760691227071-04f27f9d-bbe6-4f20-9abd-2efa5af9bc20.png)

2️⃣借助 Python 提供的内置函数，实现进制转换

<!-- 这是一张图片，ocr 内容为： -->
![](https://cdn.nlark.com/yuque/0/2025/png/35780599/1760847827120-d88d2ba5-bd04-4b4d-9b0e-ba91982d9f0c.png)

## 输入语句

在 Python 中，输入语句用于：从键盘接收用户输入的内容。

```python
# 使用input()获取用户的输入
name = input('请输入你的姓名：')
age = input('请输入你的年龄：')

# input()获取到的内容全都是字符串类型
print(type(age))
```

**📋备注**：程序执行到 `input()` 时，会暂停等待用户的输入，用户输入后敲下回车，程序继续运行。 



`input()`所获取到的内容全都是字符串类型，不过我们可以手动进行数据类型转换。

```python
age = int(input('请输入你的年龄：'))
```

