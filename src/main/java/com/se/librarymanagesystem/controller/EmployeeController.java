package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.se.librarymanagesystem.common.CurrentId;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.Employee;
import com.se.librarymanagesystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 登录请求，传入参数由@RequestBody 封装成 Employee
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //@RequestBody把Json字符串转化成employee
        //调用 Service
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        //获取Employee
        Employee empGet = employeeService.getOne(queryWrapper);

        //3.如果没有查到则返回登陆失败
        if(empGet == null){
            return R.error("登录失败");
        }

        //4.如果查询到的密码与传过来的密码不一样，返回登录失败
        if (!empGet.getPassword().equals(employee.getPassword())){
            return R.error("密码错误");
        }
        //5.查看账号状态，如果为1，则停用
        if (empGet.getStatus() == 0){
            return R.error("账号停用中，请联系管理员");
        }

        //6.账号正常，登录成功，用户id存入session中
        request.getSession().setAttribute("Employee",empGet.getId());
        Long SessionID = (Long) request.getSession().getAttribute("Employee");

        log.info("写入session"+request.getSession().getAttribute("Employee"));
        //写入session的同时写入 ThreadLocal中


        return R.success(empGet);

    }

    /**
     * 把前端Json封装成 Employee对象，调用MybatisPlus接口 存入，updateUser updateTime等内容使用
     * tableFiled自动填充
     * @param request
     * @param employee
     * @return
     */
    @PostMapping()
    public R addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        //默认分配密码为123456
        //调用Service 层的save 保存 employee
        //前端传入 账号 ，员工姓名 ，手机号， 性别， 身份证号
        //设置默认密码为 123456 调用Spring 提供的接口进行MD5加密
        String password = "123456";
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);
        boolean success = employeeService.save(employee);
        if (success){
            return R.success("添加成功");
        }
        else {
            return R.error("添加失败");
        }
    }
}
