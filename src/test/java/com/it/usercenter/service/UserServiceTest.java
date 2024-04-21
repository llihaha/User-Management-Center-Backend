package com.it.usercenter.service;
import java.util.Date;

import com.it.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author li
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("dogHaha");
        //user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://images.zsxq.com/Fk6t7wkIMiHgS-jAZSxKwC7YVQDC?e=1717171199&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:wGPiOOjhjJzfKe86Co0683BWzTs=");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result); //断言if
    }

    @Test
    void userRegister() {
        // 1. 非空
        String userAccount = "haha";
        String userPassWord = "";
        String checkPassWord = "12345678";
        String oceanCode = "1";
        long result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 2. 账户长度不小于4位
        userAccount = "ha";
        userPassWord = "12345678";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 3. 密码长度不小于8位
        userAccount = "haha";
        userPassWord = "123456";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 4. 账户不包含特殊字符
        userAccount = "ha ha";
        userPassWord = "12345678";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 5. 密码和校验密码相同
        userAccount = "haha";
        checkPassWord = "123456789";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 6. 账户不能重复
        userAccount = "dogHaha";
        checkPassWord = "12345678";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

        // 7. 正确案例
        userAccount = "haha";
        result = userService.userRegister(userAccount, userPassWord, checkPassWord, oceanCode);
        Assertions.assertEquals(-1, result);

//        String userAccount = "yupi";
//        String userPassword = "";
//        String checkPassword = "123456";
//        String oceanCode = "1";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "yu";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "yupi";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "yu pi";
//        userPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "dogyupi";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "yupi";
//        result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
//        Assertions.assertEquals(-1, result);
    }
}