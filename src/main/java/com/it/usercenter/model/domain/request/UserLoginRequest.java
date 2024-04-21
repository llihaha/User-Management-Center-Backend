package com.it.usercenter.model.domain.request;


import lombok.Data;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author li
 */
@Data
public class UserLoginRequest implements Serializable {

    /**
     * 序列化版本号
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
}
