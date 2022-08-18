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
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Builder
    public Comment(long id, String name, String body, Article article) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.article = article;
    }
}
