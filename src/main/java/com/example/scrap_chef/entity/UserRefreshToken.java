package com.example.scrap_chef.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // private은 Reflection API의 접근을 막는다.
public class UserRefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Comment("리프레시 토큰")
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Comment("만료일시")
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public void updateRefreshToken(String refreshToken, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public void expiredAtNow() {
        this.expiredAt = LocalDateTime.now();
    }

    public static UserRefreshToken create(User user, String refreshToken, LocalDateTime expiredAt) {
        UserRefreshToken token = new UserRefreshToken();
        token.user = user;
        token.refreshToken = refreshToken;
        token.expiredAt = expiredAt;
        return token;
    }
}
