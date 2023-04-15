package com.se.librarymanagesystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class User {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long id;
    String name;
    String phone;
    String sex;
    String password;
    String id_number;
    String avatar;
    Integer status;
}
