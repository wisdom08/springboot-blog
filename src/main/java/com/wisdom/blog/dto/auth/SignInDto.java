package com.wisdom.blog.dto.auth;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignInDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pw;
}
