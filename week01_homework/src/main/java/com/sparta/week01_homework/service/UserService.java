package com.sparta.week01_homework.service;

import com.sparta.week01_homework.dto.UserDto;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        if(!userDto.getPassword().equals(userDto.getPasswordCheck())){
            throw new RuntimeException("비밀번호가 서로 일치하지 않습니다.");
        }
        // 권한 정보도 넣어서 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }
}
