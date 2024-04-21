package com.it.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.usercenter.common.BaseResponse;
import com.it.usercenter.common.ErrorCode;
import com.it.usercenter.common.ResultUtils;
import com.it.usercenter.exception.BusinessException;
import com.it.usercenter.model.domain.User;
import com.it.usercenter.model.domain.request.UserLoginRequest;
import com.it.usercenter.model.domain.request.UserRegisterRequest;
import com.it.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.it.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.it.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author li
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求体
     * @return 用户信息
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        // 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册请求参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String oceanCode = userRegisterRequest.getOceanCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, oceanCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, oceanCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求体
     * @param request          请求对象
     * @return 登录信息
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 判空
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "登录请求参数为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 注销登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        // 判空
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注销请求参数为空");
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取登录态
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrent(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        // 查数据库
        long userId = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 查询用户信息
     *
     * @param username 用户昵称
     * @param request  请求对象
     * @return 用户信息
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        // 仅管理员可查
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户不是管理员!");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            //模糊查询
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
//        return userList.stream().map(user -> {
//            return userService.getSafetyUser(user);
//        }).collect(Collectors.toList());

    }

    /**
     * 逻辑删除用户信息
     *
     * @param id      用户id
     * @param request 请求对象
     * @return 是否成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        // 仅管理员可查
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "该用户不是管理员!");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户请求参数错误");
        }
        // mybatis-plus 逻辑删除
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     *
     * @param request 请求对象
     * @return null && ADMIN_ROLE
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 鉴权: 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        // 登录态不存在时,强转会抛出异常
//        if (user == null || user.getRole() != ADMIN_ROLE) {
//            return false;
//        }
//        return true;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
