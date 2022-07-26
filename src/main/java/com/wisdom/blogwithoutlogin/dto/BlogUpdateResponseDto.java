package com.wisdom.blogwithoutlogin.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogUpdateResponseDto {
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime modifiedDate;
    @Builder
    public BlogUpdateResponseDto(String name, String title, String content, LocalDateTime modifiedDate) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

}
