package com.it.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author li
 */
@Data
public class UserRegisterRequest implements Serializable {
// Serializable 接口是 Java 提供的一个标记接口，用于指示该类的实例可以被序列化和反序列化，即可以在网络上传输或存储到文件中

    /**
     * 序列化版本号
     *
     * 序列化版本号(serialVersionUID) 是在序列化和反序列化过程中用来确保序列化类的兼容性的一个标识符
     */
    private static final long serialVersionUID = 7069213497860870339L;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 校验密码
     */
    private String checkPassword;

    /**
     *  用户编号
     */
    private String oceanCode;
}

