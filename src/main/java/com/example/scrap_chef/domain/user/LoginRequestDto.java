package com.example.scrap_chef.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}
