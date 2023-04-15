package com.se.librarymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.librarymanagesystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
