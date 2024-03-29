# 该项目不再维护，后面直接使用Wordpress。


## 博客系统

### 写在前面

之前我的博客网站是用的github+hexo方式搭建的，最近打算自己通过代码来搭建博客，花了一个多礼拜的时间，把项目的基本功能模块搭建起来了，后续还会持续更新，最近在准备面试以及备战明年春招，争取每周为这个博客添砖加瓦，并且修复一些bug，其实现在也在准备着手思索博客系统2.0怎么实现，不过由于时间关系，应该得明年或者找到工作稳定下来后再去实现大版本更新了，不过在这之前，我会不断地去完善这个博客系统，实现小版本的更新。

### 使用教程
1. 创建名为“blog”的数据库
    
    > 数据库文件位于项目根目录下(仅包含结构，数据为空)，其实也不需要数据库文件，只需要建立一个名为“blog”的空数据库，因为启动项目的时候，检测到表缺失，会自动创建缺失的表，如果没有创建，可以考虑用这个文件手动创建数据库的表。
2. 在`application-run.yml`文件中修改自己的数据库用户名和密码
3. 注册阿里云账号，在OSS对象存储中新建一个Bucket（有折扣套餐可以购买，再创建bucket这样价格就很便宜），并为bucket绑定一个域名，同时需要在域名服务商处设置域名DNS解析到我们的bucket自带的的公网域名中
    
    > 这一步不懂的可以去百度或者谷歌，因为此项目使用了阿里云OSS做存储，而且使用了域名，不绑定域名不能浏览器直接查看图片，只能下载。
4. （可选）创建一个阿里云RAM子账号，并且授权可以访问OSS对象存储，因为主账号权限太大，新建子账号并用子账号的Key安全性高
5. 在`application-run.yml`文件中修改自己的阿里云存储(OSS)配置信息   
    
    > 其中需要填写的keyid和keysecret在创建完成RAM子账号后会直接给出，务必保存下来，否则不会再次显示keyid和keysecret，只能重新创建一个RAM子账号
6. 注意配置文件`application.yml`里面的spring.profiles.active这一项是否有误，我本地是有`application-dev.yml`和`application-pro.yml`这两个文件的，里面是生成环境和开发环境的配置，但是上传到git上的只有`application-run.yml`，我有的时候可能会忘记改回run去，所以如果发现没改的话，注意修正一下

其他有不懂的可以通过邮箱`longfei_xing@foxmail.com`进行咨询，尽量别通过码云或者github私信，可能会一直不看消息




### 1.项目使用的技术
* Springboot
* Spring Data JPA
* Mysql
* Maven
* semanticUI实现


### 2.项目使用到的插件
* [自动生成目录插件：Tocbot](https://tscanlin.github.io/tocbot/)
* [代码高亮插件：prismjs](https://prismjs.com/)
* [中文网页重设与排版插件：typo.css](https://typo.sofi.sh/)
* [动画插件：ANIMATE.CSS](http://www.animate.net.cn/)
* [开源在线 Markdown 编辑器: editor.md](https://pandao.github.io/editor.md/)
* [jQuery定位跳转插件：jquery.scrollto.js](https://github.com/flesler/jquery.scrollTo)
* [明月浩空播放器](https://myhkw.cn/)
* ……

### 3.博客功能
#### 前端展示
* 展示博客分页列表
* 展示最新博客
* 展示博客详情
* 手机端预览博客详情页面
* 展示所有分类
* 展示某分类下的所有博客
* 展示所有标签
* 展示某标签下的所有博客
* 展示博客归档列表
* 展示博客评论
* 展示外链页面
* 展示关于我页面
* 音乐播放器（独立页面）
* 访客进行评论
* 评论后邮件提醒功能

#### 后台管理
* 管理员登录
* 管理员修改密码
* 管理员查看个人资料
* 博客管理（新增、编辑、查找、删除）
* 博客分类管理（新增、编辑、删除）
* 博客标签管理（新增、编辑、删除）
* 友链管理（新增、编辑、查找、删除）
* 友链分类管理（新增、编辑、删除）
* 站点日志展示（管理员操作日志，访问者信息）
* 阿里云OSS文件的上传与删除


### 4.更新日志
> 升级1.3.5版本后，项目路径不支持有中文、空格，否则发送邮件时获取html邮件模板文件时会报错！

#### **V1.4.0(Developing)—————2021/01/10**
* 修改日志记录，过滤爬虫访问的日志
* 上线博客演示Demo
* 重新修改Senamtic默认字体，大幅度优化页面显示细节
* 修复了高度不够时底部footer不能固定在底部的问题


#### **V1.3.5—————2021/01/03**
* 添加评论后多线程邮件提醒功能，如果评论是直接回复博客，会邮件提醒博主，如果评论回复前面的评论，会邮件提醒被评论者。
* 新增随机生成马赛克头像的功能，第一次评论的游客会生成一个像素块马赛克的头像，日后沿用此头像，依据邮箱名称判断是否是同一个人
* 禁用新增博客和新增链接页面的回车触发自动提交表单，防止无意中回车导致提交未完成的表单而报错。
* 博客管理页新增草稿筛选，草稿标红
* 优化评论页面显示

#### **V1.3.0————————2020/12/28**
* 新增删除一些信息时、修改信息返回时、退出登录时的对话框，防止误点删除信息等情况发生
* 新增修改密码，查看个人信息功能
* 新增音乐播放器独立页面 
* 大幅度优化博客详情页显示效果
* 修复之前改为nginx代理https后项目内嵌ssl证书代码忘记删除导致的在本地无法启动项目的问题
* 修复SemanticUI对Google字体的引用导致网络加载变慢的问题

#### **V1.2.5————————2020/12/16**
* 优化博客详情页title为显示博客标题
* 修复了上传文件因阿里云OSS绑定的域名无SSL证书而出错的问题
* 修复了博客归档页博客总数计数出错的问题
* 优化页面代码块显示，并且添加了括号匹配的样式，新增代码高亮的支持语言种类
* 简单重构了博客列表的展示布局，主页加了一些鼠标悬浮动效

#### **V1.2.0(patch)————2020/12/11**
* 修复目录的显示问题
* 修复了博客存放图片的目录每次编辑都会重新产生的问题
* 修复了博客页面手机端二维码查看失效的问题
* 优化了一些页面效果
* 优化了手机端的一些显示效果

#### **V1.2.0————————2020/12/9**
* Readme.md中添加使用教程
* 新增博客页面点击图片后弹窗遮罩展示图片的功能
* 新增博客标签可以添加自定义标签的功能
* 新增了一些鼠标悬浮动效
* 重命名了dao层类名(原类名单词拼写出错)
* 修复了一些页面分页切页错误的问题
* 优化了页面显示，代码风格等等页面效果，代码也可复制到剪贴板

#### **V1.1.5————————2020/12/6**
* 新增博客草稿功能，设置为草稿的博客不会被展示，也无法访问
* 新增博客插入图片功能（图片存储在阿里云OSS对象存储中）和博客插入emoji表情功能
* 修复修改博客时不修改博客内容也会更新博客更新时间的问题
* 优化用户登录逻辑
* 修复友链显示错位的问题

#### **V1.1.0————————2020/12/3**
* 添加友链页面，并可以通过后台管理友链及友链分类
* 修复归档页面年份和博客没有按照时间排序的问题
* 完善部分注释
* 优化页面显示效果

#### **V1.0.5————————2020/11/29**
* 引入阿里云OSS作为图床来保存项目图片，以获取更快的图片加载速度，也便于管理图片
* 增加日志功能，并且会将除ip地址`127.0.0.1`以外的用户的操作记录写入到数据库中
* 加入书签功能完善（不支持自动添加书签的浏览器会进行弹出提示如何手动加入书签）
* 修复后台管理页面操作结果”成功/失败“弹窗对小屏用户不友好的问题
* 修复后台管理页面”点击返回按钮返回上一页时会出现上次的操作状态弹窗”的问题
* 优化页面显示效果

####  **V1.0.0————————2020/11/24**
* 博客系统正式上线

