package com.wisdom.blog.dto;

import com.wisdom.blog.model.Blog;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BlogUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    private String name;

    public Blog toEntity() {
        return Blog.builder()
                .title(title)
                .name(name)
                .contents(contents)
                .build();
    }
}
