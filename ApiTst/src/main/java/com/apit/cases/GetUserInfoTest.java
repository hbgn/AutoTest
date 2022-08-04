package com.apit.cases;

import com.apit.config.TestConfig;
import com.apit.model.GetUserInfoCase;
import com.apit.model.User;
import com.apit.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取userId为1的用户信息")
    public void getUseInfo() throws IOException {
        SqlSession session = DatabaseUtil.gerSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getResultJson(getUserInfoCase);
        User user = session.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);

        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        JSONArray jsonArray0 = new JSONArray(resultJson.getString(0));
        Assert.assertEquals(jsonArray0.toString(), jsonArray.toString());

    }

    private JSONArray getResultJson(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject params = new JSONObject();
        params.put("id",getUserInfoCase.getUserId());

        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(params.toString(), "utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");

        List resultList = Arrays.asList(result); // result转换为list
        JSONArray array = new JSONArray(resultList);

        return array;
    }

}
