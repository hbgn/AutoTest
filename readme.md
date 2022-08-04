### 1、接口用springboot和mybatis造

### 2、用例层次设计
- 用例层
- 配置层
- 工具层
- 模型层

### 3、用例层次设计


解决add操作，产生的缓存问题：
报错空指针异常：解决办法就是把事务的默认隔离级别设置成 "读已提交".
mysql> SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;

### 4、持续集成
jenkins 在git上拉取代码，然后打包，运行接口程序，触发测试代码。
开发提交代码到git仓库，我们运行Jenkins job拉取代码、打包部署，部署完触发测试应用程序，生成测试报告。

  

- 接口程序打包和测试代码打包
持续集成部署到服务器时，是一个jar包，用jar包启动才能自动化跑起来。
1、配置pom文件build中添加groupid的maven打包插件/ configuration配置testng.xml

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-site-plugin</artifactId>
                <version>3.5.1</version>
            </plugin>
        </plugins>
    </build>
```
打包命令(项目路径下)：mv clean package



