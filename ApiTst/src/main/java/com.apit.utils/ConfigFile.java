package com.apit.utils;

import com.apit.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

/*
    工具类，用于url拼接
 */
public class ConfigFile {
    // bundle 变量加载配置文件
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    // 拼接url, 使用静态方法
    public static String getUrl(InterfaceName name) {
        String address = bundle.getString("test.url");
        String uri = "";
        if (name == InterfaceName.ADDUSER) {
            uri = bundle.getString("addUser.uri");
        }
        if (name == InterfaceName.GETUSERINFO) {
            uri = bundle.getString("getUserInfo.uri");
        }
        if (name == InterfaceName.GETUSERLIST) {
            uri = bundle.getString("getUserList.uri");
        }
        if (name == InterfaceName.UPDATEUSERINFO) {
            uri = bundle.getString("updateUserInfo.uri");
        }
        if (name == InterfaceName.LOGIN) {
            uri = bundle.getString("login.uri");
        }
        String testUrl = address + uri;
        return testUrl;
    }

}
