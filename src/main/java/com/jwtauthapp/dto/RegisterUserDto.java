package com.jwtauthapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUserDto {

    private String username;
    private String firstName;
    private String lastName;
    private String password;

}
