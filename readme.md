## TestNG测试demo
### 1、接口用springboot和mybatis编写，用例管理在mysql数据库

### 2、用例层次设计
- 用例层
- 配置层
- 工具层
- 模型层

### 3、问题1
解决add操作，产生的缓存问题：
报错空指针异常：将事务的默认隔离级别设置成 "读已提交".
mysql> SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;

### 4、持续集成
在docker运行jenkins，使用shell脚本构建执行jar包，启动接口服务。
使用testng进行测试.
打包命令：mvn clean package

待完善部分：
- 提交代码触发自动构建（gitHooks）
- 使用dockfile一键部署安装相关环境

