package com.wisdom.blog.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}

