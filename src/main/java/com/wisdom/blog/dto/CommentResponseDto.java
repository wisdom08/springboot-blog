package com.wisdom.blog.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final String name;
    private final LocalDateTime createdDate;
    private final String body;
    @Builder
    public CommentResponseDto(String name, String body, LocalDateTime createdDate) {
        this.name = name;
        this.body = body;
        this.createdDate = createdDate;
    }
}
