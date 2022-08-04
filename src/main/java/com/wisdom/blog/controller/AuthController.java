package com.wisdom.blog.controller;

import com.wisdom.blog.dto.SignInDto;
import com.wisdom.blog.dto.SignUpDto;
import com.wisdom.blog.dto.TokenResponseDto;
import com.wisdom.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginService loginService;

    @Autowired
    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signUp")
    public Long signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return loginService.signUp(signUpDto);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponseDto> signInp(@RequestBody @Valid SignInDto signInDto) {
        TokenResponseDto tokenResponseDto = loginService.signIn(signInDto);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken",tokenResponseDto.getAccessToken());
        responseHeaders.set("refreshToken",tokenResponseDto.getAccessToken());

        return ResponseEntity.ok().headers(responseHeaders).body(tokenResponseDto);
    }

    /**
     * Access token 이 만료되었을 경우 프론트에서 요청할 api
     * @param  refreshTokenMap : Refresh token 을 입력받는다.
     * @return TokenResponseDto : Access token 과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/reissue")
    public TokenResponseDto reissueToken(@RequestBody Map<String, String> refreshTokenMap){
       return loginService.reissueAccessToken(refreshTokenMap.get("refreshToken"));
    }
}

