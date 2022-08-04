package com.wisdom.blog.dto;

import com.wisdom.blog.model.Comment;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentRequestDto {

    private long id;
    private String name;

    @NotBlank(message = "내용을 입력해주세요.")
    public String body;

    public Comment toEntity() {
        return Comment.builder()
                .name(name)
                .body(body)
                .build();
    }
}
