package com.se.librarymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Book implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String name;
    String ISBN;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long categoryId;

    BigDecimal price;
    String image;
    String description;
    String author;
    String publisher;
    Integer status;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Long updateUser;

    Integer isBroken;
}
