package com.wisdom.blogwithoutlogin.model;

import com.wisdom.blogwithoutlogin.util.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Blog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Builder
    public Blog(Long id, String name, String password, String title, String contents) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }
}
