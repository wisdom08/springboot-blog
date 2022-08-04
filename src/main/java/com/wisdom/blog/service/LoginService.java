package com.wisdom.blog.service;

import com.wisdom.blog.dto.SignInDto;
import com.wisdom.blog.dto.SignUpDto;
import com.wisdom.blog.dto.TokenResponseDto;
import com.wisdom.blog.model.Member;
import com.wisdom.blog.model.RefreshToken;
import com.wisdom.blog.repository.MemberRepository;
import com.wisdom.blog.security.MyUserDetailsService;
import com.wisdom.blog.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final com.wisdom.blog.repository.refreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signUp(SignUpDto signUpDto){
        String userId = signUpDto.getUserId();

        validateDuplicateUser(userId);

        if (!signUpDto.getPw().equals(signUpDto.getPwConfirm())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String encodePw = passwordEncoder.encode(signUpDto.getPw());
        return memberRepository.save(Member.toEntity(userId, encodePw)).getId();
    }

    @Transactional
    public TokenResponseDto signIn(SignInDto signInDto) {
        String userId = signInDto.getUserId();
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userId);
        if(!passwordEncoder.matches(signInDto.getPw(), userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(userId, refreshToken);

        refreshTokenRepository.save(token);

        return TokenResponseDto.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+refreshToken)
                .build();
    }

    private void validateDuplicateUser(String userId){
        memberRepository.findByUserId(userId)
                .ifPresent(member -> {
                    log.debug("userId : {}, 아이디 중복으로 회원가입 실패", userId);
                    throw new RuntimeException("아이디 중복");
                });
    }

    public TokenResponseDto reissueAccessToken(String refreshToken) {

        String resolveToken = resolveToken(refreshToken);
        jwtTokenProvider.validateToken(resolveToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolveToken);
        RefreshToken refreshRedisToken = refreshTokenRepository.findByUserId(authentication.getName()).get();

        if(!resolveToken.equals(refreshRedisToken.getToken())){
            throw new RuntimeException("not equals refresh token");
        }

        String newToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken newRedisToken = RefreshToken.createToken(authentication.getName(), newToken);
        refreshTokenRepository.save(newRedisToken);

        return TokenResponseDto.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+newToken)
                .build();
    }

    //token 앞에 "Bearer-" 제거
    private String resolveToken(String token){
        if(token.startsWith("Bearer-"))
            return token.substring(7);
        throw new RuntimeException("not valid refresh token !!");
    }
}
