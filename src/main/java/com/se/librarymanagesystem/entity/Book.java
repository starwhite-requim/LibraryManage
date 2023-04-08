package com.se.librarymanagesystem.entity;

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

    LocalDateTime createTime;
    LocalDateTime updateTime;

    Long createUser;
    Long updateUser;

    Integer isBroken;
}
