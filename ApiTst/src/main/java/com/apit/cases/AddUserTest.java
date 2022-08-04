package com.apit.cases;

import com.apit.config.TestConfig;
import com.apit.model.AddUserCase;
import com.apit.model.User;
import com.apit.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

   @Test(dependsOnGroups = "loginTrue" , description = "添加用户测试")
    public void addUser() throws IOException, InterruptedException {
        // sqlSession
       SqlSession session = DatabaseUtil.gerSqlSession();
       AddUserCase addUserCase = session.selectOne("addUserCase",1);
       System.out.println(addUserCase.toString());
       // 依赖前序登陆交易,分组为同组，登陆前置交易实例化类变量
       System.out.println(TestConfig.addUserUrl);

       // （添加的用户参数）发送请求、获取返回实际结果
       String result = getResult(addUserCase);

       Thread.sleep(6000);
       // 验证返回预期结果(从数据库查询添加的用户数据)
       User user = session.selectOne("addUser",addUserCase);

       // 结果断言
       Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        // 定义发送请求实例
        HttpPost post = new HttpPost(TestConfig.addUserUrl);

        // 实例json入参新对象
        JSONObject param = new JSONObject();
        param.put("username", addUserCase.getUsername());
        param.put("password", addUserCase.getPassword());
        param.put("age", addUserCase.getAge());
        param.put("sex", addUserCase.getSex());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        // 设置请求头信息
        post.setHeader("content-type","application/json");

        // 请求参数添加到entity方法
        StringEntity entity = new StringEntity(param.toString(),"utf-8");

        // entity装载到post对象
        post.setEntity(entity);

        // 设置cookies
        TestConfig.defaultHttpClient.setCookieStore((CookieStore) TestConfig.store);

        String result; // 存放返回结果
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
}
