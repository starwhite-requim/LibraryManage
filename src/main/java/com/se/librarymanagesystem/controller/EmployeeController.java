package com.se.librarymanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.se.librarymanagesystem.common.CurrentId;
import com.se.librarymanagesystem.common.R;
import com.se.librarymanagesystem.entity.Employee;
import com.se.librarymanagesystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;

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
        //passowrd进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //3.如果没有查到则返回登陆失败
        if(empGet == null){
            return R.error("登录失败");
        }

        //4.如果查询到的密码与传过来的密码不一样，返回登录失败
        if (!password.equals(empGet.getPassword())){
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


    /**
     * 登出，清除session中的 ID
     * @param request  request用于获取session
     * @return
     */
    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        if(request.getSession().getAttribute("Emloyee")!=null){
            request.getSession().removeAttribute("Employee");
        }
        return R.success("登出成功");
    }


    /**
     *根据用户的输入返回查询结果
     * 1. 页面发送ajax请求，把分页查询参数（page，pageSize，name）提交到服务端
     * 2. 服务端Controller接受页面提交的数据并调用Service查询数据
     * 3. Service调用Mapper操作数据库，查询分页数据
     * 4. Controller把查询到的分页数据响应给页面
     * 5. 页面接受到分页数据并通过ElementUI的Table组件展示到页面上
     * @param page  当前页数
     * @param pageSize  分页大小
     * @param name  查询参数，可能不传入
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //获取page
        log.info("page={} pageSize={} name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件，第一个参数为true的时候再执行
        queryWrapper.like(StringUtils.hasText(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getCreateTime);
        //page方法执行后分页
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     *  修改员工信息
     * @param request
     * @param empIn
     * @return
     */
    @PutMapping
    public R<String> changeStatus(HttpServletRequest request,@RequestBody Employee empIn){
        //构造 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //等值查询
        queryWrapper.eq(Employee::getId,empIn.getId());
        Employee employee = employeeService.getOne(queryWrapper);

        //设置查询对象的值
        if (empIn.getName() != null){
            employee.setName(empIn.getName());
            employee.setUsername(empIn.getUsername());
            employee.setSex(empIn.getSex());
            employee.setIdNumber(empIn.getIdNumber());
            employee.setPhone(empIn.getPhone());
        }
        else {
            employee.setStatus(empIn.getStatus());
        }
        //通过request 请求得到 当前登录的员工ID
        Long empID = (Long) request.getSession().getAttribute("Employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empID);
        //update
        employeeService.update(employee,queryWrapper);
        return R.success("状态修改成功");
    }

    /**
     * Restful风格请求，获得相应id的Employee实体
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployee(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
}
