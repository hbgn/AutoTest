package com.apit.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * 读取数据库信息，创建可执行sql语句的对象
 */
public class DatabaseUtil {
    public static SqlSession gerSqlSession() throws IOException {
        // 获取配置的资源文件
        // 读文件Reader
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");

        //build文件,使用类加载器加载配置文件对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);

        // 返回sqlSession, sqlSession可执行配置文件中的sql语句
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }
}
