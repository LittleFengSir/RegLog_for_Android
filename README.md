# 介绍
基于安卓API 28开发的简易的注册登录app
这是我的安卓开发课设，~~写的比较简陋实现了简易的注册登录功能，并没有检查用户的输入，以及注册的时候是否有已存在在账户。
并且密码是明文保存的，并未对密码进行加密。~~ <br />
已经实现对密码的加密存储<br />
引入 **zxcvbn** 库，对密码进行复杂性测量，简单的密码则不会通过注册<br />
用户信息界面依旧是进行明文传输了密码，其实可以不需要，我这里是因为课设的要求，所以在这里进行明文传输了，可以删掉<br />

# 软件架构说明
一共使用了4个界面，主界面，注册界面，登录界面，用户信息界面<br />
使用了SQLite数据库对数据进行保存<br />

# 开发环境：
Abdroid SDK最低要求版本：API 28<br />
Android Studio版本：Jellyfish | 2023.3.1<br />
Gradle 版本: 8.6<br />

# 版权声明
本项目所用到的图片，版权归原作者所有


