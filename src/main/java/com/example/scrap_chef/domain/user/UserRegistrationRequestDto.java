package com.example.scrap_chef.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDto {
    private String username;
    private String password;
}
