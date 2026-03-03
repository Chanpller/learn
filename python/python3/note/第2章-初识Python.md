
# 第 2 章-初识Python
## 2.1 Python 概述
### 2.1.1 Python 的起源

![](../image/1749354931023-cc95dc03-1eb9-492d-9964-0034196647d3.png)


Python 的作者 Guido van Rossum 来自荷兰（国内爱称：龟叔），拥有数学与计算机背景，他发现用： C、Fortran 等语言写程序太费劲，而 Shell 虽然轻松，但功能却很有限。

1989 年圣诞节，龟叔开始动手编写一种既能像 C 那样全面操控系统，又能像 Shell 一样好上手的解释器，并以他喜爱的喜剧《Monty Python’s Flying Circus》为灵感，命名为“Python”。


Python 的设计哲学是“优雅、明确、简单”，Python 提倡：最好只有一种方法来做一件事，它的第一个公开版本于 1991 年问世，如今已成为全球最受欢迎的编程语言之一。

### 2.1.2 Python 的特点
**Python 的优点：**![](../image/1749354989559-df9c7897-f852-4394-b7c9-bb8963636451.png)

**Python 的缺点：**
![](../image/1749355002062-73624e17-279c-45dd-85e4-8ad739a9dd58.png)

### 2.1.3 为何 AI 领域广泛使用 Python ？
主要原因是 Python 具备如下的特点：


1. 简洁直观的开发体验。
2. 丰富强大的框架生态。
3. 与底层语言高效协作。
4. 社区活跃且人才充足。
5. 业内大厂 + 主流推动。

### 2.1.4 Python 的版本
+ 1991年：`Python 0.9.0`发布。
+ 1994年：`Python 1.0`正式发布（进入正式版阶段）。  
+ 2000年：`Python 2.0`发布。  
+ 2008年：`**<font style="color:#AD1A2B;">Python 3.0</font>**`**<font style="color:#AD1A2B;">发布，与</font>**`**<font style="color:#AD1A2B;">Python 2</font>**`**<font style="color:#AD1A2B;">不兼容。</font>**
+ 2010年：`Python 2.7`发布，作为`Python 2.x`的最后主版本，被广泛使用多年。  

......

+ 2020年：`Python 2`官方停止维护，同时`Python 3.9`发布。
+ 2021年：`Python 3.10`发布。  
+ 2022年：`Python 3.11`发布，平均性能提升`10%-60%`。  
+ 2023年：`Python 3.12`发布，进一步优化性能和类型提示。  
+ 2024年：`Python 3.13`发布。
+ 2025年：持续迭代。

## 2.2 搭建 Python 开发环境
📢本小节涉及很多设置和操作，建议各位参考视频，来完成配置。

### 2.2.1 安装 Python 解释器
<details class="lake-collapse"><summary id="uf57998a8"><span class="ne-text">1️⃣</span><span class="ne-text">进入官网，点击 Downloads，选择对应的操作系统。</span></summary><p id="u19c115f7" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1760833595586-a7a7e511-a513-4079-96eb-ee32890310e9.png" width="402.2992248535156" title="" crop="0,0,1,1" id="YzIyl" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="u4db34e61"><span class="ne-text">2️⃣</span><span class="ne-text">选择版本，点击链接下载，我们这里的版本是 3.13.4。</span></summary><p id="u59e95520" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1760833639051-d17a9093-29c8-4875-aaf6-ea6f29ed78e0.png" width="384.21209716796875" title="" crop="0,0,1,1" id="ldtVr" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="u05b936d5"><span class="ne-text">3️⃣</span><span class="ne-text">双击下载好的文件，开始安装（强烈建议以管理员身份运行）。</span></summary><p id="u39c942d8" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1742541858770-9815b1cd-5129-4b55-808e-91beecd24c6c.png" width="301.6590576171875" title="" crop="0,0,1,1" id="afXvz" class="ne-image"></p><p id="ub2918b70" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1760833710377-d7adccb7-9064-456f-be82-5a2b3ef90de6.png" width="496.5738220214844" title="" crop="0,0,1,1" id="MsP60" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="u744b6261"><span class="ne-text">4️⃣</span><span class="ne-text">保持默认，点击 Next。</span></summary><p id="u5788b828" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1742540099784-55de0374-aee9-4d9a-a15e-bfc14b3d844a.png" width="494.9942626953125" title="" crop="0,0,1,1" id="Yqj1n" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="ub3032350"><span class="ne-text">5️⃣</span><span class="ne-text">修改安装路径，其他保持默认，点击 Install 开始安装。</span></summary><p id="u06826bd4" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1742541721115-082f6187-47db-4526-a362-9b053ce71dbf.png" width="499.3314208984375" title="" crop="0,0,1,1" id="CmzwN" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="u91efc479"><span class="ne-text">6️⃣</span><span class="ne-text">禁用系统路径长度限制</span></summary><p id="u07cc6330" class="ne-p"><span class="ne-text">建议点击 Disable path length limit，这样可以禁用系统的路径长度限制，以避免因路径过长而导致的错误，随后点击 Close，完成安装。</span></p><p id="u286e9c9c" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1742277727299-8937b744-f807-444a-bb6b-c4ee9e056f83.png" width="502.32763671875" title="" crop="0,0,1,1" id="VM0uk" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="uc6cf89f7"><span class="ne-text">7️⃣</span><span class="ne-text">检查是否安装成功，同时按下 Win 键和 R ，输入 cmd ，点击确定，进入命令提示符。</span></summary><p id="ub9935957" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1742277743809-5e9eeef2-244e-4761-9685-4b85bd88aea1.png" width="305.32574462890625" title="" crop="0,0,1,1" id="grSaa" class="ne-image"></p></details>
<details class="lake-collapse"><summary id="ua456a184"><span class="ne-text">8️⃣</span><span class="ne-text">输入 </span><span class="ne-text" style="background-color: #D8DAD9">python --version</span><span class="ne-text">，若能打印出 Python 版本，则表示安装成功。</span></summary><p id="ucecd908a" class="ne-p"><img src="https://cdn.nlark.com/yuque/0/2025/png/35780599/1760833556271-5894aaaf-347e-4919-9a21-3b01f69cea45.png" width="350.4772644042969" title="" crop="0,0,1,1" id="H0ybz" class="ne-image"></p></details>
### 2.2.2 一个简单的打印效果
**① **在终端中输入`python`并回车


![](../image/1760672944490-8c843101-e6e8-4114-b6cb-ed1be3c65cdb.png)

**② **随后输入：`print(100)`，随后回车，终端中呈现`100`


![](../image/1760673790705-62a5fc7c-ceb4-491b-af3a-0dce34d5bc77.png)

**📋备注：**作为初学者，各位暂时不用纠结上述代码的含义，先跟着操作就可以，后面会仔细讲解。



### 2.2.3 **安装 PyCharm**
> 集成开发环境（简称：IDE；英文名：Integrated Development Environment ）是用于提供程序开发环境的应用程序，一般包括代码编辑器、编译器、调试器和图形用户界面等工具。集成了代码编写功能、分析功能、编译功能、调试功能等多种功能，本课程中 Python 的 IDE 我们使用主流的工具： PyCharm。
>


PyCharm 官方地址：[https://www.jetbrains.com/pycharm/download](https://www.jetbrains.com/pycharm/download)



**具体安装步骤如下：**

1️⃣进入官网，点击左下角 Download 下载 PyCharm 安装包（此处下载的是完整版安装包）。


![](../image/1760673132249-c8981f8f-00b3-4cd0-9f50-004e21b81f94.png)


**📋备注：**<font style="color:#585A5A;">Pycharm 已经没有专业版了，现在的叫：完整版（也叫：统一版），完整版中包含：付费功能+ 免费功能，付费功能可以免费试用 30 天，到期不付费的话，软件依然可以打开，并且免费的功能也都能正常使用，所以此处推荐各位下载完整版。如果不想使用完整版，也可以下载社区版，具体下载方式，请参考视频教程。</font>



2️⃣以管理员身份运行安装包文件，点击下一步进行安装
![](../image/1742542228520-a9798475-aa93-4062-af5e-d0436552a89f.png)

3️⃣修改安装目录，点击下一步。


![](../image/1760833980187-a3a54548-c0c7-44f1-b7f1-ee9f5dde4fa7.png)

4️⃣勾选对应的安装选项，之后点击下一步。


![](../image/1760834150340-9d15e6da-8b3d-4302-8cda-fceb343affb7.png)

5️⃣点击安装。
![](../image/1742277950805-35ef46b5-7f39-4920-8190-272c33cbda72.png)

6️⃣安装完成。
![](../image/1742277967701-231b0b0b-084c-4dfb-a4ba-04b0b2e01534.png)

### 2.2.4 设置 PyCharm
#### 2.2.4.1 设置中文UI
初次运行会弹出语言选择框，选择中文语言包即可
![](../image/1760834246129-371d9d41-5754-450f-85b9-822c05ee84de.png)

提示是否共享数据，若共享就会将部分使用数据发送给 `Jetbrains`公司优化产品，我这里选择不共享。
![](../image/1760834270696-b1d3e2fc-12b8-486a-b560-e896130ea204.png)

#### **2.2.4.2 创建项目**
1️⃣点击新建项目
![](../image/1742543151230-e8c9b732-bebc-4c65-a4a7-77937c915291.png)

2️⃣设置项目名称，项目路径，解释器类型，Python版本。

**<font style="color:#AD1A2B;">📢</font><font style="color:#AD1A2B;">注意</font>**：不同的 pycharm 版本，这里看到的界面可能会略有不同。
![](../image/1742543322562-c43add7a-ec8d-4ca7-b8b8-b6347109803b.png)

3️⃣一个 Python 项目创建成功。
![](../image/1742278061716-b337a9dd-9f5b-489d-895c-05cef6f3f04d.png)

4️⃣若出现如下提示，点击【排除文件夹】即可
![](../image/1742545188878-51b057f4-8ef4-4914-afa1-cf13b96719a4.png)

#### 2.2.4.3 字体设置 
1️⃣参考下图调整编辑器字体
![](../image/1742546578242-f8ea39b4-53c8-4b34-b63c-4a431ffa130f.png)

2️⃣若出现如下提示，则表示当前字体遵循主题设置，需要点击蓝色文字，跳转到配色方案中进行调整。
![](../image/1742546820219-3b00444f-557b-4eef-a8b7-55cbba268819.png)
![](../image/1742547062405-40e8b087-85e7-4a5f-b038-6a6d30e864af.png)

#### 2.2.4.4 主题设置
1️⃣打开设置面板。
![](../image/1742545378455-633dfa41-893d-454f-8844-acdb2790962a.png)

2️⃣依次选择：外观 → 主题。
![](../image/1742545456161-36373959-89ac-4f58-9e8b-d11bc5cff6b6.png)

3️⃣滑动到最后，点击获取更多主题，可以从主题商店中安装新主题。
![](../image/1742545537081-ea92559d-f362-4429-a1e6-67e79986368c.png)
![](../image/1742545567773-a94c7130-0ad8-49da-bfde-2d102935338b.png)	

#### **2.2.4.5 默认快捷键**
PyCharm 中常用的默认快捷键如下：

| **快捷键** | **对应操作** |
| --- | --- |
| **<font style="color:black;">Ctrl + /</font>** | <font style="color:black;">行注释（可选中多行）</font> |
| **Ctrl + Alt + L** | 代码格式化 |
| **<font style="color:black;">Ctrl + C</font>** | <font style="color:black;">复制当前行  /  复制选定的代码</font> |
| **Ctrl + D** | 重复当前行  /  重复<font style="color:black;">选定的代码</font> |
| **<font style="color:black;">Ctrl + Z</font>** | <font style="color:black;">撤销</font> |
| **Ctrl + Y** | 删除当前行   /   反撤销(重做) |
| **<font style="color:black;">Ctrl + X</font>** | <font style="color:black;">复制当前行  /  剪切选定的代码</font> |
| **<font style="color:black;">Shift + Enter</font>** | <font style="color:black;">换行（光标不在结尾处也可换行）</font> |


#### **2.2.4.6 自定义快捷键**
除了默认的快捷键，我们还可以配置自己喜欢的快捷键，例如：我们可以设置`ctrl`+ 鼠标滚轮，来快速调整字体大小，具体设置步骤如下：

1️⃣按照图示方式，找到对应设置：
![](../image/1742548047638-4d6d9f37-ccc0-4a66-9fb4-3f51eda182da.png)

2️⃣选择：添加鼠标快捷方式
![](../image/1742548078593-311c9c31-2589-405a-b7cb-9c68b7a69699.png)

3️⃣弹出如下窗口后，按住`ctrl`键的同时，将鼠标滚轮向下滚动（当然向上也可以，根据个人习惯来）
![](../image/1742548212088-c80778eb-9f50-44cb-9e92-e2502676b7aa.png)

4️⃣随后窗口中会自动识别当前按下的按键和鼠标动作，随后点击确定即可。
![](../image/1742548327461-8de42853-3a2a-47bc-b4ec-c0d6830bdacb.png)

5️⃣再以同样的方式，设置让字体变大的快捷键
![](../image/1742548409297-06292bc5-4a17-4039-adc6-8bd7ee3521c3.png)

**📋备注：**大家可以根据自己的喜好，设置其他功能的快捷键，具体设置方式和注意点，请看视频教程。

## **2.3 运行 Python 程序的几种方式总结**
运行 Python 程序，有常见的以下三种方式：

+ 第一种方式：命令行（终端）模式
+ 第二种方式：脚本模式
+ 第三种方式：集成开发环境（IDE）模式

> 备注：第三种方式，其实是第二种方式的图形化操作，本质上算是一种模式。
>

### 2.3.1 **命令行模式**
1. 同时按下 Win 键和 R ，随后输入`cmd` ，打开终端（命令行）。
2. 在终端（命令行）中输入`python`，进入 Python 交互模式。
3. 输入`print(100)`，按下回车，控制台会打印：`100`。


![](../image/1760673838371-46429e51-9272-4248-be18-6ae99e14271c.png)	

### 2.3.2 **脚本模式	**
1. 在桌面上新建一个`code`文件夹，随后新建一个文本档，将其重命名为`test.py` 

![](../image/1760673889230-c02eb559-04a1-4131-a3bd-cbddb21ae12e.png)
![](../image/1760673904937-a78ad76b-e624-43a7-a089-11678fdd32cb.png)

2. 使用记事本打开`test.py`，在其中写好代码并保存。


![](../image/1760673951056-a4fee5a7-ae4a-4e44-98a4-5f56130baf07.png)

3. 找到`test.py`所在的文件夹


![](../image/1760674012698-8f1970c9-be45-423d-9322-dc4ce773a844.png)

4. 在资源管理器上方输入`cmd`并回车，就会打开命令提示符并进入当前路径。
   ![](../image/1743992947226-ccaf1cb2-65c0-4b72-8732-14e315b21845.png)


![](../image/1760674047849-6057e947-edd4-41d9-8eac-5888f8036d45.png)

5. 在命令提示符中输入`python test.py`执行程序，就会看到打印的内容。
   ![](../image/1760674060718-92780452-da6f-4f35-a1f7-59070bbc958c.png)

### **2.3.3 IDE模式**
1. 鼠标右键工程文件夹，选择新建 python 文件。
   ![](../image/1742610007397-53f7c6cf-a757-45c4-b78c-1200c36db0f3.png)

2. 输入文件名，确认后按下回车
   ![](../image/1760674327271-426b784d-8264-4325-a7d7-8a4e130bb694.png)

3. 输入`print(100)`，随后在文件空白处点击鼠标右键，选择：`运行test`。
   ![](../image/1760674349158-06fa6a55-3214-4542-bc9d-03fafbf53696.png)
