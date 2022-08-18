package com.wisdom.blog.controller;

import com.wisdom.blog.dto.auth.SignInDto;
import com.wisdom.blog.dto.auth.SignUpDto;
import com.wisdom.blog.dto.auth.TokenResponseDto;
import com.wisdom.blog.service.AuthService;
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

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public Long signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        TokenResponseDto tokenResponseDto = authService.signIn(signInDto);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accessToken",tokenResponseDto.getAccessToken());
        responseHeaders.set("refreshToken",tokenResponseDto.getAccessToken());

        return ResponseEntity.ok().headers(responseHeaders).body(null);
    }

    /**
     * Access token 이 만료되었을 경우 프론트에서 요청할 api
     * @param  refreshTokenMap : Refresh token 을 입력받는다.
     * @return TokenResponseDto : Access token 과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/reissue")
    public TokenResponseDto reissueToken(@RequestBody Map<String, String> refreshTokenMap){
       return authService.reissueAccessToken(refreshTokenMap.get("refreshToken"));
    }
}

