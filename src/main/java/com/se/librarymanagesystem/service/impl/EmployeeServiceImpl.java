package com.se.librarymanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.librarymanagesystem.entity.Employee;
import com.se.librarymanagesystem.mapper.EmployeeMapper;
import com.se.librarymanagesystem.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
