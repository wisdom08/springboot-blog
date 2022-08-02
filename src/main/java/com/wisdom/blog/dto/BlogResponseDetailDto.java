package com.wisdom.blog.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogResponseDetailDto {
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    @Builder
    public BlogResponseDetailDto(String name, String title, String content, LocalDateTime createdDate) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

}
