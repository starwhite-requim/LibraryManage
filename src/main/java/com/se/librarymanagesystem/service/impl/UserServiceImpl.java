package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.entity.User;
import com.se.librarymanagesystem.mapper.UserMapper;
import com.se.librarymanagesystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
