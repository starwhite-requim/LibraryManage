package com.se.librarymanagesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//自动扫描mapper
@MapperScan("com.se.librarymanagesystem.mapper")
//扫描Servlet组件，用于使用Filter
@ServletComponentScan
public class LibraryManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManageSystemApplication.class, args);
    }

}
