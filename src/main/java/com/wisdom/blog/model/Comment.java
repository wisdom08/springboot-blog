package com.wisdom.blog.model;

import com.wisdom.blog.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @Builder
    public Comment(long id, String name, String body, Blog blog) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.blog = blog;
    }
}
