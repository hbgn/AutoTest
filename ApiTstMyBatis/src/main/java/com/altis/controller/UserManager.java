package com.altis.controller;

import com.altis.model.User;
import lombok.extern.log4j.Log4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;


@Log4j
@RestController
@Api(value = "v1")
@RequestMapping("v1")
public class UserManager {

    // 加载数据库对象、完成装配给对应模板属性
    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登陆接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response,
                         @RequestBody User user) {
        // 根据传参查询数据库中是否有用户
        int i = template.selectOne("login", user);
        log.info("查询到的结果是：" + i);

        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        if (i == 1) {
            log.info("登陆的用户是：" + user.getUsername());
            return true;
        }
        return false;
    }


    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Boolean addUser(HttpServletRequest request,
                           @RequestBody User user) {
        Boolean x = verIfyCookies(request);
        int result = 0;
        if (x != null) {
            result = template.insert("addUser", user);
        }
        if (result > 0) {
            log.info("添加用户的数量是：" + result);
            return true;
        }
        return false;
    }

    /**
     * 验证cookies是否存在且正确
     *
     * @param request
     * @return
     */
    private Boolean verIfyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            log.info("cookies为空");
            return false;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")) {
                log.info("cookie验证通过！");
                return true;
            }
        }
        return false;
    }


    @ApiOperation(value = "获取用户（列表）信息接口", httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,
                                  @RequestBody User user) {
        Boolean x = verIfyCookies(request);
        if (x) {
            List<User> users = template.selectList("getUserInfo", user);
            log.info("getUserInfo获取到的用户数量是：" + users.size());
            return users;
        }
        return null;
    }

    @ApiOperation(value = "更新/删除用户数据", httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,
                          @RequestBody User user) {
        Boolean x = verIfyCookies(request);
        int u = 0;
        if (x) {
            u = template.update("updateUserInfo", user);
        }
        log.info("更新数据的条数是：" + u);
        return u;

    }

}


