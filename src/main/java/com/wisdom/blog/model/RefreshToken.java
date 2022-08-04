package com.wisdom.blog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private String userId;
    private String token;

    @Builder
    private RefreshToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    static public RefreshToken createToken(String userId, String token){
        return new RefreshToken(userId, token);
    }
}
