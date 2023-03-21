package com.spring.backproject.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Data
public class RegisterDto {
    private String firstName;
    private String lastName;

    private String email;
    private String password;
}
