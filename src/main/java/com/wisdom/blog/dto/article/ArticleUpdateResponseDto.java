package com.wisdom.blog.dto.article;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleUpdateResponseDto {
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime modifiedDate;
    @Builder
    public ArticleUpdateResponseDto(String name, String title, String content, LocalDateTime modifiedDate) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

}
