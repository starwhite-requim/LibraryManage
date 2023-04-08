package com.se.librarymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Slf4j
public class Employee implements Serializable {
    //Long全部转化成String进行传输
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String name;
    String username;
    String password;
    String phone;
    String sex;
    String idNumber;
    Integer status;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    //自动填充
    @TableField(fill = FieldFill.INSERT)
    Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Long updateUser;
}
