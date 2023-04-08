package com.se.librarymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.librarymanagesystem.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
