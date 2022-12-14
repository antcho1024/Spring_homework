package com.sparta.week01_homework.service;

import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 로그인 시에 DB에서 유저 정보와 권한 정보를 가져오게 됨
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(final String username) {
//        return userRepository.findOneByUsername(username)
//                .map(this::createUser)
//                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
//    }
//
//    // 해당 정보를 기반으로 활성화 상태면 userdetails.User 객체를 생성해서 리턴
//    private UserDetails createUser(User user) {
//        if (!user.isActivated()) {
//            throw new RuntimeException( " -> 활성화되어 있지 않습니다.");
//        }
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.singleton(grantedAuthority)
//        );
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        User user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        return new CustomUserDetailsImpl(user);
    }
    // 해당 정보를 기반으로 활성화 상태면 userdetails.User 객체를 생성해서 리턴
//    private UserDetails createUser(User user) {
//        if (!user.isActivated()) {
//            throw new RuntimeException( " -> 활성화되어 있지 않습니다.");
//        }
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.singleton(grantedAuthority)
//        );
//    }
}