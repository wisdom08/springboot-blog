package com.wisdom.blogwithoutlogin.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Blog extends BaseTime {
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

}
