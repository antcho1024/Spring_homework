package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.dto.LoginDto;
import com.sparta.week01_homework.dto.TokenDto;
import com.sparta.week01_homework.jwt.JwtFilter;
import com.sparta.week01_homework.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//로그인 API 만들기
@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) { //로그인 디티오로 정보 받고

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()); // 디ㅣ티오를 이용해 토큰 생성

        // authenticationToken 을 이용해서 authentication 객체를 생성하기위해
        // authenticate 메소드가 실행이 되고
        // 그 때 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
        // 그 결과를 갖고 authentication 객체 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 그 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //그 객체를 통해 JWT 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // JWT 토큰을 Response header에도 넣어주고
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        // TokenDto 를 이용해서 Response Body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}