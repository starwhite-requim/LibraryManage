package com.se.librarymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    Integer type;
    String name;
    Integer sort;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Long updateUser;
}
