package com.apit.config;

import org.apache.http.impl.client.DefaultHttpClient;

import java.net.CookieStore;

/*
    处理用户基本信息，储存url变量
 */
public class TestConfig {
    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    // defaultHttpClient
    public static DefaultHttpClient defaultHttpClient;
    // cookies
    public static CookieStore store;


}
