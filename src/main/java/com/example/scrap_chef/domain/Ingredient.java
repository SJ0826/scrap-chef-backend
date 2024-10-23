package com.example.scrap_chef.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor // JPA를 위한 기본 생성자
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    // 생성날짜 자동설정
    // 엔티티가 저장되기 전에 호출된다
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Ingredient(Long id, String title, LocalDateTime createdAt) { // 빌더 패턴 사용을 위한 생성자
        this.title = title;
        this.createdAt = createdAt;
    }
}
