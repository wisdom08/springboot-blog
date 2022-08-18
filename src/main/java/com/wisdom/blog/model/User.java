package com.wisdom.blog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String pw;

    private String roles;

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public User(String userId, String pw) {
        this.userId = userId;
        this.pw = pw;
        this.roles = "ROLE_USER";
    }

    public static User toEntity(String userId, String pw) {
        return User.builder()
                .userId(userId)
                .pw(pw)
                .build();
    }
}
