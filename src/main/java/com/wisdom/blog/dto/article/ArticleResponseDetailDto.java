package com.wisdom.blog.dto.article;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDetailDto {
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    @Builder
    public ArticleResponseDetailDto(String name, String title, String content, LocalDateTime createdDate) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

}
