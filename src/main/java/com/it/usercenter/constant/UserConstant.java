package com.it.usercenter.constant;

/**
 * 用户常量
 *
 * @author li
 */
public interface  UserConstant {

    /**
     * 用户登录态键
     * 可以看作一个key,找到唯一的值
     */
    String USER_LOGIN_STATE = "userLoginState";

    // ------------ 权限 -------------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;
}
