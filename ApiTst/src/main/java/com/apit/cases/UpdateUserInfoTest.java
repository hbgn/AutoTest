package com.apit.cases;

import com.apit.config.TestConfig;
import com.apit.model.UpdateUserInfoCase;
import com.apit.model.User;
import com.apit.utils.DatabaseUtil;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @SneakyThrows
    @Test(dependsOnGroups = "loginTrue", description = "更改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        Thread.sleep(3000);
        SqlSession session = DatabaseUtil.gerSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 1);
        System.out.println("updateUserInfoCase  " + updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        // 发送请求，获取接口实际返回结果
        int result = getResult(updateUserInfoCase);
        Thread.sleep(3000);
        // 获取预期结果
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        // 判断user是否为空 //断言
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);

    }


    @SneakyThrows
    @Test(dependsOnGroups = "loginTrue", description = "删除用户信息")
    public void deleteUser() throws IOException {
        SqlSession session = DatabaseUtil.gerSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase", 2); // 查询id为2的更新表数据
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        // 发送请求，获取接口实际返回结果
        int result = getResult(updateUserInfoCase);
        Thread.sleep(3000);
        // 获取预期结果
        User user = session.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        // 判断user是否为空 //断言
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);

    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject params = new JSONObject();
        params.put("id", updateUserInfoCase.getUserId());
        params.put("username", updateUserInfoCase.getUsername());
        params.put("sex", updateUserInfoCase.getSex());
        params.put("age", updateUserInfoCase.getAge());
        params.put("permission", updateUserInfoCase.getPermission());
        params.put("isDelete", updateUserInfoCase.getIsDelete());

        post.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(params.toString(), "utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        return Integer.parseInt(result);

    }
}
