package com.wisdom.blog.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpDto {

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{4,12}", message = "아이디는 4~12자 / 알파벳 대/소문자 + 숫자를 혼합해서 사용하세요.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,32}", message = "비밀번호는 4~32자 / 알파벳 소문자 + 숫자를 혼합해서 사용하세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pw;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String pwConfirm;
}
