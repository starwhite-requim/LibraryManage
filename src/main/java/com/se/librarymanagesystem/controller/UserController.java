package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.User;
import com.se.librarymanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody Map UserIn, HttpServletRequest request){
        String phone = String.valueOf(UserIn.get("phone"));
        String code = String.valueOf(UserIn.get("code"));
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User userSelected = userService.getOne(queryWrapper);
        if (userSelected!=null){
            return R.success(userSelected,"登录成功");
        }
        return R.error("登录失败");
    }
}
