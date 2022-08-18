package com.wisdom.blog.dto.article;

import com.wisdom.blog.model.Article;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ArticleUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;

    private String name;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .name(name)
                .contents(contents)
                .build();
    }
}
