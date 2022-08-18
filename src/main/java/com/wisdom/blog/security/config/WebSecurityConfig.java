package com.wisdom.blog.security.config;

import com.wisdom.blog.security.jwt.JwtAccessDeniedHandler;
import com.wisdom.blog.security.jwt.JwtAuthenticationEntryPoint;
import com.wisdom.blog.security.jwt.JwtTokenFilterConfigurer;
import com.wisdom.blog.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/articles/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.PUT, "/api/articles/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.DELETE, "/api/articles/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.POST, "/api/comments/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.PUT,"/api/comments/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.DELETE,"/api/comments/**").access("isAuthenticated()")
                .anyRequest().permitAll()

                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider)); // JWT 관련 필터 추가
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
