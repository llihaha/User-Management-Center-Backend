package com.it.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 加密方法测试
     * @throws NoSuchAlgorithmException
     */
    @Test
    void testDigest() throws NoSuchAlgorithmException {
        // 加密方法1 Java的MessageDigest类
//        // 获取一个MD5算法实例,MD5是一种常用的哈希算法，用于将任意长度的数据映射为固定长度的哈希值
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        // 将字符串"abcd"转换为字节数组，并计算其MD5哈希值，使用了UTF-8编码将字符串转换为字节数组
//        byte[] bytes = md5.digest("abcd".getBytes(StandardCharsets.UTF_8));
//        // 将计算得到的MD5哈希值的字节数组转换为字符串，通过new String(bytes)方法进行转换
//        String result = new String(bytes);
//        System.out.println(result);

        // 加密方法2 Apache Commons Codec库中的DigestUtils类
        // 将字符串"abcd"和"mypassword"拼接起来，并将拼接后的字符串转换为字节数组
        // 对该字节数组进行MD5哈希计算，并将结果以十六进制字符串的形式返回
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(newPassword);
    }
}
