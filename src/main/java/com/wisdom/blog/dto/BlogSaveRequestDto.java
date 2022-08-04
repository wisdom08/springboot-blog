package com.wisdom.blog.dto;

import com.wisdom.blog.model.Blog;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class BlogSaveRequestDto {

    private String name;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    public Blog toEntity() {
        return Blog.builder()
                .name(name)
                .title(title)
                .contents(contents)
                .build();
    }


}
