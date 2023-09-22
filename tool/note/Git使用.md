# Git使用

# 1章 Git概述

Git是一个免费的、开源的 分布式版本控制系统 ，可以快速高效地处理从小型到大型的各种。

## 1.1 版本控制工具分类

* 集中式版本控制工具：CSV、SVN(Subversion)、VSS
* 分布式版本控制工具：Git、Mercurial、Bazaar、Darcs

## 1.2 Git工作机制

工作区--> (git add)-->暂存区--> (git commit)-->本地库

## 1.3 Git和代码托管中心

局域网：GitLab

互联网：GitHub(外网)、Gitee(国内网站)

# 2章 Git安装

* 官网：https://git-scm.com/

* 安装步骤：
  * Select Compents界面功能
    * Addtioncal icons  创建快捷方式 ，On the Desktop 创建桌面菜单
    * Windows Exploer integration 右键菜单(建议勾选)
    * Git LFS 大文件支持(建议勾选)
    * Associate .git* 配置文件默认编辑器(建议勾选)
    * Associate .sh 关联.sh格式文件(建议勾选)
    * Use a TrueType 控制台字体
    * Check daily 检查更新
  * Choosing the default editor userd by Git
    * 建议选择Vim为默认的Git控制台编辑器
  * Adjusting the name of the initial brache in new repositories
    * Let Git decide 让Git决定分支名，默认为Master（建议选择这个）
  * Adjusting  your PATH environment
    * Use git from Git Bash only 只在Git Bash中使用git，不修改环境变量
  * Choosing HTTPS transport backend 选择后台客户端连接协议
    * Use the OpenSSL library 使用OpenSSL 协议进行连接（建议选择这个）
    * Use the native Windows Sercure Channel library 使用windows凭证连接
  * Configuring the line ending conversions 配置换号符
    * Checkout Windows-style,commit Unix-style line endings 自动检查windows换号符，替换为unix换号符
  * Configuring  the terminal emulator to user with Git Bash 选择Git Bash 终端类型
    * Use MINTTY 默认Git Bash 终端（建议选择这个）
    * Use Windows default console window 使用windows cmd命令行为终端。
  * Choose the default behavior of git pull 选择pull合并的模式
    * Default 默认模式（建议选择这个）
    * Rebase
    * only ever  fast-forward
  * Choose a credential helper 选择凭据管理器
    * Git credential Manager Core （建议选择这个）
    * Git credential Manager  windows 凭证管理器
    * None
  * Configuring extra options 其他配置
    * Enable file systemn caching 文件缓存机制，默认勾选
    * Enable symbolic links 使用符号连接，默认勾选

# 3章 常用命令

进到自己想要操作的目录，右键->git bash here 打开git base操作界面

* git --version 查看git版本
* git config global user.name 设置全局用户签名用户名
* git config global user.email 设置全局用户签名邮箱
* git init 初始化本地库
* git status 查看本地库状态
* git add 文件名   添加到暂存区
* git commit m " 日志信息 " 文件名  提交到本地库
* git reflog 查看简略历史记录
* git log 查看详细历史记录
* git reset hard 版本号    切换本地库到指定版本

## 3.1 设置用户签名

git config --global user.name 用户名
git config --global user.email 邮箱

```shell
$ git config --global user.name yuebuqun
$ git config --global user.email yuebuqun@163.com
$ cat ~/.gitconfig
[user]
        name = yuebuqun
        email = yuebuqun@163.com
```

*  Git首次安装必须设置一下用户签名，否则无法提交代码。
* 这里设置用户签名和将来登录 GitHub（或其他代码托管中心）的账号没有任何关系。

## 3.2 初始化本地库

git init

```
$ git init
$ ll -a
total 4
```

## 3.2 查看本地库状态

git status

```
$ git status
On branch master
No commits yet
nothing to commit (create/copy files and use "git add" to track)
```

* 需要在.git目录同级目录下执行才行。
* 未添加的文件是红色
* 添加了未提交的是绿色
* 提交了就不会显示了

## 3.3 添加暂存区

git add 文件名

```
$ git add hello.txt
warning: LF will be replaced by CRLF in hello.txt.（将windows换号符替换为unix的）
The file will have its original line endings in your working
directory.
```

## 3.4 提交本地库

git commit -m "日志信息 " 文件名

```
$ git commit -m "第一次提交" hello.txt
warning: LF will be replaced by CRLF in hello.txt.
The file will have its original line endings in your working directory
[master (root-commit) 542bee1] 第一次提交
 1 file changed, 15 insertions(+)
 create mode 100644 hello.txt

```

## 3.5 修改后提交

修改后，按照git add 文件名，然后git commit -m "日志信息" 文件名，再提交到服务器

修改后查看状态，会有modified标记，再次提交后就没有了。

* add过后的文件如果修改，文件可以直接commit，直接提交
* 未add的文件，不能直接提交。

```
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   hello.txt

no changes added to commit (use "git add" and/or "git commit -a")

```

## 3.6 查看历史版本日志

git reflog 查看版本信息
git log 查看版本详细信息

```shell

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git reflog
4dc2908 (HEAD -> master) HEAD@{0}: reset: moving to 4dc2908
b669d12 HEAD@{1}: commit: 第三次提交\n添加test2.txt文件
23397fb HEAD@{2}: commit: 第二次提交
4dc2908 (HEAD -> master) HEAD@{3}: commit (initial): 第一次提交

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git log
commit 4dc2908558b3e646a102de27b43ead2a1af88895 (HEAD -> master)
Author: Chanpller <2856838704@qq.com>
Date:   Fri Mar 17 20:42:14 2023 +0800

    第一次提交

```

## 3.7 版本切换（版本穿梭）

git reset --hard 版本号

版本号可以是全部的，也可以是省略的，只要能确定是唯一的就行。

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git reset --hard 4dc2908
HEAD is now at 4dc2908 第一次提交

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git reflog
4dc2908 (HEAD -> master) HEAD@{0}: reset: moving to 4dc2908
b669d12 HEAD@{1}: commit: 第三次提交\n添加test2.txt文件
23397fb HEAD@{2}: commit: 第二次提交
4dc2908 (HEAD -> master) HEAD@{3}: commit (initial): 第一次提交

```

# 4、分支

多个版本，代码。

## 4.1 分支命令

- git branch 分支名     创建分支
- git branch v    查看分支
- git checkout 分支名      切换分支
- git merge分支名       把指定的分支合并到当前分支上、

## 4.2 查看分支

git branch v  

```
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git branch -v
* master 4dc2908 第一次提交

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git branch
* master

```

## 4.3 创建分支

git branch 分支名

```
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git branch  hot-fix

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git branch
  hot-fix
* master

```

## 4.4 切换分支

git checkout 分支名

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git checkout hot-fix
Switched to branch 'hot-fix'

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (hot-fix)
$ git status
On branch hot-fix
nothing to commit, working tree clean

```

## 4.5 合并分支

git merge 分支名     当前分支合并分支

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git merge hot-fix
Already up to date.


Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git checkout hot-fix
Switched to branch 'hot-fix'

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (hot-fix)
$ git merge master
Updating 4dc2908..b669d12
Fast-forward
 hello.txt | 4 ++--
 test2.txt | 1 +
 2 files changed, 3 insertions(+), 2 deletions(-)
 create mode 100644 test2.txt

```

## 4.6 解决冲突

- 两个分支都有修改同一个文件，同一行文件，才会造成冲突。
- 编辑有冲突的文件，删除特殊符号，决定要使用的内容
- 特殊符号：<<<<<<< HEAD 当前分支的代码 ======= 合并过来的代码 >>>>>>> hot-fix
- 修改有冲突的文件：删除调特殊符号。
- 添加到暂存区（需要手动修改文件后，添加到暂存区才能提交，直接提交会报错）
- git commit -m "日志信息" 不带文件名

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git merge hot-fix
Auto-merging hello.txt
CONFLICT (content): Merge conflict in hello.txt
Automatic merge failed; fix conflicts and then commit the result.

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge hot-fix to master"
error: Committing is not possible because you have unmerged files.
hint: Fix them up in the work tree, and then use 'git add/rm <file>'
hint: as appropriate to mark resolution and make a commit.
fatal: Exiting because of an unresolved conflict.
U       hello.txt

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git add hello.txt

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge hot-fix to master"
[master 48df159] merge hot-fix to master

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ cat hello.txt
hello git! hello word! 11111 22222
hello git! hello word! 22222222222
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word! matser modified!
hello git! hello word!
hello git! hello word! master modified this is hot-fix modified，modified new hot-fix


Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ vim hello.txt

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge commit"
error: Committing is not possible because you have unmerged files.
hint: Fix them up in the work tree, and then use 'git add/rm <file>'
hint: as appropriate to mark resolution and make a commit.
fatal: Exiting because of an unresolved conflict.
U       hello.txt

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ cat hello.txt
hello git! hello word! 11111 22222
hello git! hello word! 22222222222
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word!
hello git! hello word! 444444
hello git! hello word
hello git! hello word! matser modified!
hello git! hello word! 2
hello git! hello word! hot-fix modify

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge commit" hello.txt
fatal: cannot do a partial commit during a merge.

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git add hello.txt

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge commit" hello.txt
fatal: cannot do a partial commit during a merge.

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master|MERGING)
$ git commit -m "merge commit"
[master 5a57d10] merge commit

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git status
On branch master
nothing to commit, working tree clean

```

# 5、GitHub操作

## 5.1 创建远程仓库

在github服务器创建repository

## 5.2 远程仓库操作

* git remote -v 查看当前所有远程地址别名

* git remote add 别名 远程地址   给远程仓库起别名
* git push 别名 分支  推送本地分支上的内容到远程仓库
* git clone 远程地址   将远程仓库的内容克隆到本地
* git pull 远程库地址别名 远程分支名   将远程仓库对于分支最新内容拉下来后与当前本地分支直接合并

## 5.3 创建远程仓库别名

* 2021年8月过后，不支持https推送了，建议使用ssh推送。

* git remote -v 查看当前所有远程地址别名

* git remote add 别名 远程地址   给远程仓库起别名

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git remote -v

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git remote add github-remote-git-test git@github.com:Chanpller/git-test.git

Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git remote -v
github-remote-git-test  git@github.com:Chanpller/git-test.git (fetch)
github-remote-git-test  git@github.com:Chanpller/git-test.git (push)
```

## 5.4 推送本地分支内容到远程仓库

* git push 别名 分支  推送本地分支上的内容到远程仓库
* push是将本地库代码推送到远程库，如果本地库代码跟远程库代码版本不一致，push的操作是会被拒绝的。也就是说， 要想 push成功，一定要保证 本地 库的版本要比远程库的版本高！ 因此一个成熟的程序员在动手改本地代码之前，一定会先检查下远程库跟本地代码的区别！如果本地的代码版本已经落后，切记要先 pull拉取一下远程库的代码，将本地代码更新到最新以后，然后再修改，提交，推送！

```shell
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/git-test (master)
$ git push github-remote-git-test  master
The authenticity of host 'github.com (140.82.113.4)' can't be established.
ECDSA key fingerprint is SHA256:p2QAMXNIC1TJYWeIOttrVc98/R1BUFWu3/LiyKgUfQM.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'github.com,140.82.113.4' (ECDSA) to the list of known hosts.
Enumerating objects: 36, done.
Counting objects: 100% (36/36), done.
Delta compression using up to 4 threads
Compressing objects: 100% (33/33), done.
Writing objects: 100% (36/36), 3.28 KiB | 560.00 KiB/s, done.
Total 36 (delta 11), reused 0 (delta 0)
remote: Resolving deltas: 100% (11/11), done.
To github.com:Chanpller/git-test.git
 * [new branch]      master -> master

```

## 5.5 远程仓库克隆到本地

* git clone 远程地址   将远程仓库的内容克隆到本地
* 用https或ssh都可以克隆

```
Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/clone-test
$ git clone git@github.com:Chanpller/git-test.git
Cloning into 'git-test'...
remote: Enumerating objects: 36, done.
remote: Counting objects: 100% (36/36), done.
remote: Compressing objects: 100% (22/22), done.
remote: Total 36 (delta 11), reused 36 (delta 11), pack-reused 0
Receiving objects: 100% (36/36), done.
Resolving deltas: 100% (11/11), done.


Administrator@DESKTOP-UPTV06G MINGW64 /e/ideaprojects/clone-test
$ git clone https://github.com/Chanpller/git-test.git

```

## 5.6 远程仓库拉去

* git pull 远程库地址别名 远程分支名 
* 有别名才能通过别名拉去，没有别名可以直接用地址拉取
* 注意：pull是拉取远端仓库代码到本地，如果远程库代码和本地库代码不一致，会自动合并，如果自动合并失败，还会涉及到手动解决冲突的问题。

```shell
$ git pull github-remote-git-test  master
remote: Enumerating objects: 5, done.
remote: Counting objects: 100% (5/5), done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
Unpacking objects: 100% (3/3), done.
From github.com:Chanpller/git-test
 * branch            master     -> FETCH_HEAD
   5a57d10..9e4526e  master     -> github-remote-git-test/master
Updating 5a57d10..9e4526e
Fast-forward
 test2.txt | 1 +
 1 file changed, 1 insertion(+)

```

## 5.7 邀请加入团队

* 进到服务仓库settings（不是用户settings）->Collaborators->Manage access->add people
* 生成邀请链接后，发给别人，点解接受就可以加入代码开发了。

## 5.8 跨团队协作

* 使用Fork将项目叉到自己的仓库中。
* 然后使用pull requests 可以发送给源项目作者，源项目作者可以通过pull requests查看到这个代码，选择需不需要合并。
* 如果没问题，选择Merge pull request 合并代码。

## 5.9 SSH免密登陆

* 复制远程仓库中SSH的地址，

* ssh-keygen -t rsa -C 用户名或邮箱        创建ssh的公钥和私钥
* 提交时需要使用ssh的提交
* 2021年8月过后，不支持https推送了，建议使用ssh推送。

```
进入到用户的根目录
C:\Users\Administrator\
先删除.ssh目录
执行
ssh-keygen -t rsa -C 登陆邮箱名
一直回车就行
进到
.ssh目录
id_rsa.pub是公钥
id_rsa是私钥目录
复制id_rsa.pub文件内容，登录 GitHub，点击用户头像 Settings->SSH and GPG keys->new SSH keys
把id_rsa.pub内容复制进去。然后随便取个名字

提交时选择ssh，git@这种进行提交。
```

# 6、IDEA使用git

## 6.1配置 Git忽略文件

* 创建忽略规则文件 xxxx.ignore（前缀名随便起 ，建议是 git.ignore)

* 文件放在都行

* 在 .gitconfig文件中引用忽略配置文件（此文件在 Windows的用户根目录中）

  xxxx.ignore文件

```
HELP.md
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

```

.gitconfig文件

```
[user]
	name = xx
	email = xx@qq.com
[core]
	excludesfile = C:/Users/asus/git.ignore
注意：这里要使用“正斜线（ （//）”，不要使用“反斜线
```

## 6.2 定位 Git程序

* File->Settings->Version Control->Git  可以配置安装目录

## 6.3 初始化本地库

* VCS->Create Git Repository-> 选择需要添加的项目

## 6.4 添加到暂存区

* 右键点击项目选择Git -> Add将项目添加到暂存区 。

## 6.5 提交到本地库

* 右键点击项目选择Git ->commit将项目提交到本地库。

## 6.6 切换版本

* 在IDEA的左下角，点击 Version Control，然后点击 Log查看版本。右键选择要切换的版本，然后在菜单里点击Checkout Revision。

## 6.7 创建分支

* 选择Git ，点击 Branches按钮，在弹出的Git Branches框里 点击 New Branch按钮。填写分支名称，创建分支。

## 6.8 切换分支

* 右下角，可以看到分支，选择对应分支 Checkout

## 6.9 合并分支

* 右键点击项目选择Git -> Merge..->选择需要合并进来的分支。如果代码没有冲突，分支 直接合并成功，分支合并成功以后，代码自动提交，无需手动提交本地库 。

## 6.10 解决冲突

* 点击 Conflicts框里的 Merge按钮，进行手动合并代码。
* 左边是当前分支代码，中间是合并后的代码，右边是需要合进来分支的代码。
* X符号表示不要这个代码。
* 左右》《两个符号表示，将左边代码插入，将右边代码也插入。
* 手动合并完代码以后，点击右下角的 Apply按钮。
* 代码冲突解决，自动提交本地库。

# 7、IDEA集成GitHub

## 7.1 配置github

* File->Settings->Version Control->Github 设置github账号，使用token方式登陆，通过github服务器生成token
* github官网：Settings->Developer settings->Personal access tokens->Tokens (classic)->创建，全选。复制下token字符串。然后配置到IDEA中

## 7.2 分享工程到 GitHub

* VCS(git)->找到share Project on github

## 7.3 管理不同的仓库地址remotes

* Git(VCS)->Manage Remotes  可以设置不同的远程仓库别名，push时可以自主选择需要提交到哪个库。

## 7.4 push、pull、clone

* 项目右键->Git->可以进行push、pull、clone操作

# 8、码云Gitee

码云是开源中国推出的基于 Git的代码托管服务中心， 网址是 https://gitee.com/ ，使用方式跟 GitHub一样，而且它还是一个 中文网站，如果你英文不是很好它是最好的选择。

## 8.1 IDEA集成Gitee

* Idea默认不带码云插件，我们第一步要安装 Gitee插件。在Idea插件商店搜索 Gitee，然后点击右侧的 Install按钮。
* 其他配置和Github一样

## 8.2 其他操作

类似于github

## 8.3 码云复制 GitHub项目

* 码云提供了直接复制GitHub项目的功能，方便我们做项目的迁移和下载 。

* 新建仓库时----->选择   在其他网站已经有仓库了吗？ 点击导入。 可以通过url导入进来。

# 9、自建代码仓库GitLab

## 9.1 概述

* GitLab是由 GitLabInc.开发，使用 MIT许可证 的基于 网络 的 Git仓库 管理工具，且具有wiki和 issue跟踪功能。使用 Git作为代码管理工具，并在此基础上搭建起来的 web服务。GitLab由乌克兰程序员 DmitriyZaporozhets和 ValerySizov开发，它使用 Ruby语言 写成。后来，一些部分用 Go语言 重写。截止 2018年 5月，该公司约有 290名团队成员，以及 2000多名开源贡献者。 GitLab被 IBM Sony JülichResearchCenter NASA Alibaba Invincea O’ReillyMedia Leibniz-Rechenzentrum( CERN SpaceX等组织使用 。

## 9.2 官网地址

* 官网地址https://about.gitlab.com/，中文换成了https://gitlab.cn/
* 安装说明：https://about.gitlab.com/installation/

## 9.3 GitLab安装

* 错误解决

```
Failed to download metadata for repo 'appstream': Cannot prepare internal mirrorlist: No URLs in mirrorlist

cd /etc/yum.repos.d/
sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
yum clean all
yum makecache
yum install wget –y
```

```
Error: Unable to find a match: policycoreutils-python

policycoreutils-python是CentOS 7的，CentOS 8改为了policycoreutils-python-utilson
The package policycoreutils-python is for CentOS 7 and can’t be installed on CentOS 8. It has been renamed to policycoreutils-python-utilson CentOS 8. 

```

* 安装ssh

```
#安装 centeros7 可能需要安装的东西
#查看是否安装ssh 连接
rpm -qa|grep openssh
 
yum list installed |grep openssh

#如果显示没有安装可以使用yum install -y openssh-server或yum install -y openssh-clients进行安装 
#编辑配置
vi /etc/ssh/sshd_config

#打开这些内容
Port 22
ListenAddrres 0.0.0.0
PermitRootLogin yes
PasswordAuthentication yes

#然后开启sshd服务，查看ssh进程和端口号
sudo service sshd start   #开启sshd服务
 
ps -ef |grep sshd         #检查sshd进程
 
netstat -an |grep 22      #检查22端口号是否开启监听
 
systemctl status sshd     #查看ssh服务状态是否开启

#设置开机自启动ssh服务
systemctl enable sshd.service    #设置开机自启动服务
 
systemctl list-unit-files |grep sshd    #查看是否开启sshd服务自启动
 
chkconfig sshd on  #设置ssh服务为开机启动命令

#设置ssh服务禁止开机自启动命令
chkconfig sshd off  
 
service sshd stop   #停止ssh服务命令

#查看主机ip，可以用ifconfig命令，但有可能ifconfig命令没装。
ip add

```

* 网络ip分配

```
#输入命令: 
ip addr
#没有看到ens33 intent

#进入到
cd /etc/sysconfig/network-scripts

#编辑，centeros 没有vim命令，就用vi
vim /etc/sysconfig/network-scripts/ifcfg-ens33

#修改 ONBOOT=yes

#重启网络
sudo service network restart

#再查看
ip addr

#ens33 中有intent 就是ip地址

```



* 防火墙操作

```
#防火墙操作
1、命令行界面输入命令“systemctl status firewalld.service”并按下回车键。

2、然后在下方可度以查看得到“active（running）”，此时说明防火墙已经被打开了。

3、在命令行中输入systemctl stop firewalld.service命令，进行关闭防火墙。

4、然后再使用命令systemctl status firewalld.service，在下方出现disavtive（dead），这权样就说明防火墙已经关闭。

5、再在命令行中输入命令“systemctl disable firewalld.service”命令，即可永久关闭防火墙。
```

* 安装gitlab

```
#安装依赖
sudo yum install -y curl policycoreutils-python openssh-server cronie

sudo rpm -ivh /opt/module/gitlab-ce-13.10.2-ce.0.el7.x86_64.rpm


# 如果报错 sudo: lokkit: command not found
# 安装 yum install lokkit
# 如果报错 ERROR: FirewallD is active, please use firewall-cmd.
# 先关闭防火墙 systemctl stop firewalld.service
sudo lokkit -s http -s ssh

sudo yum install -y postfix

sudo service postfix start

sudo chkconfig postfix on

curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.rpm.sh | sudo bash

sudo EXTERNAL_URL="http://自己的域名或ip(192.168.15.131)" yum -y install gitlab-ce

#初始化
gitlab-ctl reconfigure

#启动服务
gitlab-ctl start

```

## 9.4 gitlab访问

* 安装好后可以通过，主机ip进行访问。
* 首次访问需要重设密码，登陆用户为root
* 后面操作就和githu、gitee差不多了。

## 9.5 IDEA集成gitlab

* 插件中找到gitlba project 2020，安装好之后在版本控制里面，找到gtilab添加http地址，切换为https连接。输入用户和密码。
* 如果太慢，可能是gitee导致的，先disable gitee，再添加。
* 提交到gitlab：在提交时，新建romete，添加gitlab地址。
