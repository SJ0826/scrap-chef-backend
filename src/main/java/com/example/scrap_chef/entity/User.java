package com.example.scrap_chef.entity;

import com.example.scrap_chef.data.auth.SignupInDto;
import com.example.scrap_chef.util.EncryptUtil;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그인 아이디")
    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Comment("비밀번호")
    @Column(name = "password", nullable = false, unique = false, length = 255)
    private String password;

    /**
     * 유저 회원가입
     */
    public void signUp(SignupInDto signupInDto) {
        this.loginId = signupInDto.getLoginId();
        this.password = EncryptUtil.encryptSha512(signupInDto.getPassword());
    }
}

