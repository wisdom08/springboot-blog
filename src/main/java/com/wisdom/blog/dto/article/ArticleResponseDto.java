package com.wisdom.blog.dto.article;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private final String name;
    private final String title;
    private final LocalDateTime createdDate;
    private final String contents;
    @Builder
    public ArticleResponseDto(String name, String title, String contents, LocalDateTime createdDate) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }
}
