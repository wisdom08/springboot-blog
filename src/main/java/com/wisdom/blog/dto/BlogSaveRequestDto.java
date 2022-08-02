package com.wisdom.blog.dto;

import com.wisdom.blog.model.Blog;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BlogSaveRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public Blog toEntity() {
        return Blog.builder()
                .name(name)
                .title(title)
                .contents(contents)
                .password(password)
                .build();
    }


}
