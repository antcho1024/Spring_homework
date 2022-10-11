package com.sparta.week01_homework.config;

import com.sparta.week01_homework.jwt.JwtAccessDeniedHandler;
import com.sparta.week01_homework.jwt.JwtAuthenticationEntryPoint;
import com.sparta.week01_homework.jwt.JwtSecurityConfig;
import com.sparta.week01_homework.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //기본적인 web 보안 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해서 적용
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //h2-console 하위 모든 요청들과 파비콘 관련 요청은 Spring Security 로직을 수행하지 않도록
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()


                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //Exception을 핸들링할 때 우리가 만들었던 클래스들을 추가
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                //HTTPServletRequest를 사용하는 요청들에 대한 접근 제한을 설정하겠다
                .authorizeRequests()
//                .antMatchers("/api/hello").permitAll() // /api/hello 에 대한 접근은 인증없이 허용하겠다
                .antMatchers("/api/authenticate").permitAll() //토큰을 받기 위한 로그인 api와
                .antMatchers("/api/signup").permitAll() //회원 가입을 위한 api는 토큰이 없는 상태에서 이뤄지기 때문에 permitall

                .anyRequest().authenticated() // 나머지 요청들에 대해는 모두 인증을 받아야한다.

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); //JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스도 적용

        return httpSecurity.build();
    }
}