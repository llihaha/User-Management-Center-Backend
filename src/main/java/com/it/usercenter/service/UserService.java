package com.it.usercenter.service;

import com.it.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author li
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-04-10 01:25:54
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param oceanCode 用户编号
     * @return 新用户 ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String oceanCode);

    /**
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 请求对象
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 原始信息
     * @return 脱敏信息
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request 请求对象
     */
    int userLogout(HttpServletRequest request);
}
