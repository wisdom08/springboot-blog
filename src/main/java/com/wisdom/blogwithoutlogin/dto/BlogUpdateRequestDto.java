package com.wisdom.blogwithoutlogin.dto;

import com.wisdom.blogwithoutlogin.model.Blog;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BlogUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    public Blog toEntity() {
        return Blog.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}
