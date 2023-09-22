# IDEA设置和快捷键

# 1、JDK相关设置

## 1.1 SDK设置

- File-->Project Structure...-->Platform Settings -->SDKs    可以添加sdk
- SDKs全称是Software Development Kit ，这里一定是选择JDK的安装根目录，不是JRE的目录。
- 这里还可以从本地添加多个JDK。使用“+”即可实现。

## 1.2 out目录和编译版本

- File-->Project Structure...-->Project Settings -->Project 
  - SDK 选择JAVA版本
  - Project Language level   项目版本
  - Project compiler output  项目编译输出目录

# 2、IDEA 详细设置

## 2.1 显示工具栏

View->Appearance->Toolbar

## 2.2打开全局设置

- File->Settings
  - Appearnace& Behavior 外观与行为
  - keymap 快捷键
  - Editor 编辑器
  - Plugins 插件
  - Version Control 版本控制
  - Languages & Frameworks 语言和框架
  - Advanced Settrings 高级设置

## 2.3 系统配置

### 2.3.1 默认启动项目设置

- File->Settings->Appearnace& Behavior->System Settings ：Reopen projects on startup 勾选 以及Ask 勾选 表示项目启动时打开最后关闭的项目。去掉后就是IEDA最开始启动的界面。

### 2.3.2 取消自动更新

- File->Settings->Appearnace& Behavior->System Settings->Updates  ：取消勾选Check IDe updates for

## 2.4 设置整体主题、菜单字体和字体大小、背景图片

- File->Settings->Appearnace& Behavior->Appearnace ：Theme 选择主题
- File->Settings->Appearnace& Behavior->Appearnace ：Use custorm font 设置菜单和窗口的字体和大小
- File->Settings->Appearnace& Behavior->Appearnace ：Backgroud Image 设置项目背景图片

## 2.5 设置编辑器主题样式、字体大小和字体、注释字体颜色

- File->Settings->Editor->Color Scheme: 选择Scheme，设置编辑器主题
- File->Settings->Editor->General: Change font size with Contrl+Mouse Wheelin  勾选后可以通过滚轴跳转大小
- File->Settings->Editor->Color Scheme::可以调整控制台颜色字体、Debug相关颜色、Java相关字体颜色等。
- File->Settings->Editor->Color Scheme->Language Defaults:  Comments ->Doc Comment->Text:Foregroud选择颜色
  - Block comment：修改多行注释的字体颜色
  - Doc Comment –> Text：修改文档注释的字体颜色
  - Line comment：修改单行注释的字体颜色

## 2.6 显示行号与分割符

- File->Settings->Editor->General->Appearance:  show line numbers 显示行号   show method separators 显示i方法之间分割符

## 2.7 代码智能提示功能

- File->Settings->Editor->General->Code Completion: Match case 取消勾选，可以忽略大小写匹配进行代码提示

## 2.8 自动导包配置

* File->Settings->Editor->General->Auto Import：勾选Add unambiguous imports on the fly和和Optimize imports on the fly
  * 动态导入明确的包：Add unambiguous imports on the fly，该设置具有全局性；
  * 优化动态导入的包：Optimize imports on the fly，该设置只对当前项目有效；

## 2.9设置项目文件编码

- File->Settings->Editor->File Encodings:Transparent native-to-ascii conversion主要用于转换ascii，显式原生内容。一般都要勾选。Global Encoding 全局项目文件编码。Porject Encoding项目文件编码

## 2.10 设置控制台的字符编码

File->Settings->Editor->General->Console：defalult Encoding 选择UTF-8

## 2.11 修改类头的文档注释信息

File->Settings->Editor->File and Code Templates:includes

```
/**
* ClassName: ${NAME}
* Package: ${PACKAGE_NAME}
* Description:
* @Author zuozhe
* @Create ${DATE} ${TIME}
* @Version 1.0
*/

${PACKAGE_NAME} - the name of the target package where the new class or interface will
be created.
${PROJECT_NAME} - the name of the current project.
${FILE_NAME} - the name of the PHP file that will be created.
${NAME} - the name of the new file which you specify in the New File dialog box during
the file creation.
${USER} - the login name of the current user.
${DATE} - the current system date.
${TIME} - the current system time.
${YEAR} - the current year.
${MONTH} - the current month.
${DAY} - the current day of the month.
${HOUR} - the current hour.
${MINUTE} - the current minute.
${PRODUCT_NAME} - the name of the IDE in which the file will be created.
${MONTH_NAME_SHORT} - the first 3 letters of the month name. Example: Jan, Feb, etc.
${MONTH_NAME_FULL} - full name of a month. Example: January
```

## 2.12 设置自动编译

File->Settings->Build,Execution,Deployment-->Compiler: Build project automatically 和 Compile independent modules in parallel 勾选上就自动编译了

## 2.13 设置为省电模式 (可忽略)

File->Power Save Mode

## 2.14 取消双击shift搜索

File->Settings->Advanced Settings:User Inferface ->Disable double modifier key shortcuts 勾选上即可

## 2.15是否在单行显式编辑器选项卡（建议去掉勾选）

File->Settings->Editor->General->Editor Tabs: show tabs in one row 

## 2.16 设置import显示"*"时的个数

File->Settings->Editor->Code Style->Java: Class count to use import with "*" 选择个数 ，Names count to user static import with "*"选择个数

# 3、模块

project -- Module--package 

## 3.1 创建模块

选中项目右键-> new ->module

## 3.2 删除模块

选中模块右键-> remove module。然后再右键选中项目删除

## 3.3 导入模块

被模块复制到项目路径下，然后File->Project Settings->Modules：+号->import module。

## 3.4 模块单独设置编码

File->Settings->Editor->File Encodings:+号，选择对应的模块目录，设置编码。

# 4、代码模板使用（自动补齐代码）

- File->Settings->Editor->General->Postfix Completion 里面有预定义模板，可以看到java的。
- File->Settings->Editor->Live Templates实时模板。

## 4.1常用模板

### 4.1.1非空判断

- 变量.null：if(变量 == null)
- 变量.nn：if(变量 != null)
- 变量.notnull：if(变量 != null)
- ifn：if(xx == null)
- inn：if(xx != null)

### 4.1.2遍历数组和集合

- 数组或集合变量.fori：for循环
- 数组或集合变量.for：增强for循环
- 数组或集合变量.forr：反向for循环
- 数组或集合变量.iter：增强for循环遍历数组或集合

### 4.1.3输出语句

- sout：相当于System.out.println
- soutm：打印当前方法的名称
- soutp：打印当前方法的形参及形参对应的实参值
- soutv：打印方法中声明的最近的变量的值
- 变量.sout：打印当前变量值
- 变量.soutv：打印当前变量名及变量值

### 4.1.4对象操作

- 创建对象
  - Xxx.new .var ：创建Xxx类的对象，并赋给相应的变量
  - Xxx.new .field：会将方法内刚创建的Xxx对象抽取为一个属性
- 强转
  - 对象.cast：将对象进行强转
  - 对象.castvar：将对象强转后，并赋给一个变量

### 4.1.5静态常量声明

- psf：public static final
- psfi：public static final int
- psfs：public static final String
- prsf：private static final

## 4.2自定义代码模板

File->Settings->Editor->General->Postfix Completion ： +号->java  编辑关键字和需要补全的代码。

4.3 自定义Live Templates

File->Settings->Editor->Live Templates： +号->TemplateGroup添加一个模板组，然后再创建Live Template 放在模板组里面管理。

```
Abbreviation：模板的缩略名称
Description：模板的描述
Template text：模板的代码片段
模板应用范围。比如点击Define。选择如下：应用在java代码中。
```

# 5、快捷键的使用

File->Settings->keymap  查看快捷键、自定义快捷键

## 5.1常用快捷键

### 5.1.1通用型
| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
|复制代码-copy|ctrl + c|
|粘贴-paste|ctrl + v|
| 剪切-cut|ctrl + x|
|撤销-undo|ctrl + z|
|反撤销-redo|ctrl + shift + z|
|保存-save all|ctrl + s|
| 全选-select all|ctrl + a|

### 5.1.2 提高编写速度
| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
|智能提示-edit|alt + enter||
|提示代码模板-insert live template|ctrl+j|
|使用xx块环绕-surround with ... |ctrl+alt+t|
|调出生成getter/setter/构造器等结构-generate ... |alt+insert|
|自动生成返回值变量-introduce variable ...|ctrl+alt+v|
|复制指定行的代码-duplicate line or selection |ctrl+d|
|删除指定行的代码-delete line|ctrl+y|
|切换到下一行代码空位-start new line |shift + enter|
|切换到上一行代码空位-start new line before current | ctrl +alt+ enter|
|向上移动代码-move statement up（不能移出方法） |ctrl+shift+↑|
|向下移动代码-move statement down （不能移出方法）|ctrl+shift+↓|
|向上移动一行-move line up （可以移动到任何位置）|alt+shift+↑|
|向下移动一行-move line down （可以移动到任何位置）| alt+shift+↓|
|方法的形参列表提醒-parameter info |ctrl+p|
|批量修改指定的变量名、方法名、类名等-rename （选中引用了的命，可以批量修改）|shift+f6|
|抽取代码重构方法-extract method ...|ctrl+alt+m|
|重写父类的方法-override methods ... |ctrl+o|
|实现接口的方法-implements methods ... |ctrl+i|
|选中的结构的大小写的切换-toggle case|ctrl+shift+u|
|批量导包-optimize imports |ctrl+alt+o|

### 5.1.3类结构、查找和查看源码

| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
| 如何查看源码-go to class...                               | ctrl + 选中指定的结构 或 ctrl+n |
| 显示当前类结构，支持搜索指定的方法、属性等-file structure | ctrl+f12                        |
| 退回到前一个编辑的页面-back                               | ctrl+alt+←                      |
| 进入到下一个编辑的页面-forward                            | ctrl+alt+→                      |
| 打开的类文件之间切换-select previous/next tab             | alt+←/→                         |
| 光标选中指定的类，查看继承树结构-Type Hierarchy           | ctrl+h                          |
| 查看方法文档-quick documentation                          | ctrl+q                          |
| 类的UML关系图-show uml popup                              | ctrl+alt+u                      |
| 定位某行-go to line/column                                | ctrl+g                          |
| 回溯变量或方法的来源-go to implementation(s)              | ctrl+alt+b                      |
| 折叠方法实现-collapse all                                 | ctrl+shift+ -                   |
| 展开方法实现-expand all                                   | ctrl+shift+ +                   |

### 5.1.4 查找、替换与关闭

| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
|查找指定的结构|ctlr+f|
|快速查找：选中的Word快速定位到下一个-find next |ctrl+l|
|查找与替换-replace |ctrl+r|
|直接定位到当前行的首位-move caret to line start |home|
|直接定位到当前行的末位 -move caret to line end |end|
|查询当前元素在当前文件中的引用，然后按 F3 可以选择|ctrl+f7|
|全项目搜索文本-find in path ... |ctrl+shift+f|
|关闭当前窗口-close |ctrl+f4|

### 5.1.5 调整格式

| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
|格式化代码-reformat code |ctrl+alt+l|
|使用单行注释-comment with line comment |ctrl + /|
|使用/取消多行注释-comment with block comment |ctrl + shift + /|
|选中数行，整体往后移动-tab |tab|
|选中数行，整体往前移动-prev tab |shift + tab|

## 5.2 debug快捷键

| 说明                                                      | 快捷键                          |
| --------------------------------------------------------- | ------------------------------- |
|单步调试（不进入函数内部）step over| F8 |
|单步调试（进入函数内部）step into| F7 |
|强制单步调试（进入函数内部）force step into| alt+shift+f7 |
|选择要进入的函数 - smart step into |shift + F7|
|跳出函数 - step out |shift + F8|
|运行到断点 （跳到光标处）- run to cursor |alt + F9|
|继续执行，进入下一个断点或执行完程序 - resume program| F9|
|停止 - stop |Ctrl+F2|
|查看断点 - view breakpoints |Ctrl+Shift+F8|
|关闭 - close| Ctrl+F4|

# 6、Debug

## 6.1 行断点

断点打在代码所在的行上。执行到此行时，会停下来。

## 6.2方法断点

- 断点设置在方法的签名上，默认当进入时，断点可以被唤醒。
- 也可以设置在方法退出时，断点也被唤醒。断点的点图标处右键打开，可以设置Method exit时进入断点。

## 6.3字段断点

* 在类的属性声明上打断点，默认对属性的修改操作进行监控。断点的点图标处右键打开，可以设置Field modification（修改）时进入断点。

## 6.4 条件断点

- 断点的点图标处右键打开，可以设置Condition，满足什么条件时进入断点。

## 6.5异常断点

- View Breakpoints(debug菜单里面的有重叠的双红点图标进去)，可以+号，添加Exception报什么错异常时进入断点。

## 6.6线程调试

- 断点的点图标处右键打开，选择Thread，添加condition，比如当是某个线程名时进入断点。

## 6.7强制结束

- debug菜单里面，选择执行的断点，右键Force Return  强制返回。

## 6.8自定义调试数据视图

- debug菜单里面，选择执行的断点，最右边显示当前值的位置，右键 Customize Data Views ，可以设置数组显示数据和集合的null值，是否显示静态变量等。

## 6.9debug的常见问题

问题：使用Step Into时，会出现无法进入源码的情况。如何解决？
方案1：使用 force step into 即可
方案2：点击Setting -> Build,Execution,Deployment -> Debugger -> Stepping
把Do not step into the classess中的java.* 、javax.* 取消勾选即可。

# 7、创建不同类型工程

## 7.1创建Java工程

- File->new->Project：输入项目名，选择java版本，就可以创建一个java工程了。
- File->new->Module：输入项目名，选择java版本，就可以创建一个java工程的Module了。

## 7.2创建Java Web工程

- File->Setting -> Build,Execution,Deployment->Application Servers：+号，可以添加tomcat服务器。

- File->new->Project：输入项目名，选择java版本，创建好java工程。然后右键选择JAVA工程->Add Frameworks Support，选择：Web Application，选择Create web.xml。
- File->new->Module：输入项目名，选择java版本，创建好java工程。然后右键选择JAVA工程->Add Frameworks Support，选择：Web Application，选择Create web.xml。

* 配置Java Web工程运行tomcat。Run->Edit Configurations：+号->Tomcat Server，添加配置好的tomcat，右侧菜单添加部署应用。

* 乱码的解决

  * 点击Help => Edit custom VM Options，在最后面添加

    ```
    -Dfile.encoding=UTF-8
    ```

  * 在当前Tomcat实例中配置 VM option，添加

    ```
    -Dfile.encoding=UTF-8
    ```

  * 在第二步的Startup/Connection页签的Run和Debug添加一个key为JAVA_TOOL_OPTIONS ， value为“ -Dfile.encoding=UTF-8 ”的环境变量

  * 保存后重启IDEA，可以发现控制台中文乱码显示正常了。

## 7.3创建Maven工程

### 7.3.1 配置maven

File->Setting -> Build,Execution,Deployment->maven：配置maven的home

- File->new->Project：选择Maven Archetype，右侧输入名字，选择maven-archetype-quickstart。创建好后，在main下面创建resources的文件目录，然后右键resources，Mark Directory as ->Resources root
- File->new->Module：选择Maven Archetype，右侧输入名字，选择maven-archetype-quickstart。创建好后，在main下面创建resources的文件目录，然后右键resources，Mark Directory as ->Resources root

## 7.4创建Maven Web工程

- File->new->Project：选择Maven Archetype，右侧输入名字，选择maven-archetype-webapp。创建的是打war包的web工程
- File->new->Module：选择Maven Archetype，右侧输入名字，选择maven-archetype-webapp。创建的是打war包的web工程

## 7.5 错误及解决办法

- 找不到HttpServlet错误，加入下面依赖

```
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>servlet-api</artifactId>
<version>2.5</version>
<scope>provided</scope>
</dependency>
```

- EL表达式没有提示问题

```
<dependency>
<groupId>javax.servlet.jsp</groupId>
<artifactId>jsp-api</artifactId>
<version>2.1.3-b06</version>
<scope>provided</scope>
</dependency>
```

* jsp加入文件头

```
%@page language="java" pageEncoding="utf-8" contentType="text/html;UTF-8" %>
```

# 8、关联数据库

- View->Tool Windows->database，选择对应数据库，添加链接等。

图标1：同步当前的数据库连接。这个是最重要的操作。配置好连接以后或通过其他工具操作
数据库以后，需要及时同步
图标2：配置当前的连接
图标3：断开当前的连接
图标4：显示相应数据库对象的数据
图标5：编辑修改当前数据库对象

* 选中表->右键->Diagrams->Show Visualization 显示ER图，还可以导出。

# 9、IDEA常用插件

* IDEA插件下载位置：C:\Users\Administrator\AppData\Local\JetBrains\IntelliJIdea2021.2\plugins
* 离线安装：File->Settings->plugins->齿轮图标->Install plugin from disk
* 在线安装：File->Settings->plugins->Marketplace

## 9.1 Alibaba Java Coding Guidelines

阿里巴巴Java编码规范检查插件，检测代码是否存在问题，以及是否符合规范。
使用：在类中，右键，选择编码规约扫描，在下方显示扫描规约和提示。根据提示规范代码，提高代码
质量。

## 9.2 jclasslib bytecode viewer

可视化的字节码查看器。
使用：

1. 在 IDEA 打开想研究的类。
2. 编译该类或者直接编译整个项目（ 如果想研究的类在 jar 包中，此步可略过）。
3. 打开“view” 菜单，选择“Show Bytecode With jclasslib” 选项。
4. 选择上述菜单项后 IDEA 中会弹出 jclasslib 工具窗口。

## 9.3 Translation

注册翻译服务（有道智云、百度翻译开放平台、阿里云机器翻译）帐号，开通翻译服务并获取其应用ID
和密钥 绑定应用ID和密钥：偏好设置（设置） > 工具 > 翻译 > 常规 > 翻译引擎 > 配置…
使用：鼠标选中文本，点击右键即可自动翻译成多国语言。
注：请注意保管好你的应用密钥，防止其泄露。

## 9.4 GenerateAllSetter

实际开发中还有一个非常常见的场景： 我们创建一个对象后，想依次调用 Setter 函数对属性赋值，如果
属性较多很容易遗漏或者重复。

## 9.5 Rainbow Brackets

给括号添加彩虹色，使开发者通过颜色区分括号嵌套层级，便于阅读

## 9.6 CodeGlance Pro

在编辑器右侧生成代码小地图，可以拖拽小地图光标快速定位代码，阅读行数很多的代码文件时非常实
用。

## 9.7Statistic

代码统计工具

## 9.8 Presentation Assistant

显示快捷键操作的按键

## 9.9 Key Promoter X

快捷键提示插件。当你执行鼠标操作时，如果该操作可被快捷键代替，会给出提示，帮助你自然形成使
用快捷键的习惯，告别死记硬背。

## 9.10 JavaDoc

按alt+insert ，执行操作：生成文档

## 9.11LeetCode Editor

在 IDEA 里刷力扣算法题，需要登陆

## 9.12 GsonFormatPlus

根据 json 生成对象。
使用：使用alt + s 或 alt + insert调取。

## 9.13 Material Theme UI

主题，可以切换多种主题

