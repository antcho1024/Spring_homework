package com.sparta.week01_homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.entity.UserRoleEnum;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String passwordCheck;

    @NotNull
    private UserRoleEnum role;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .passwordCheck(user.getPassword())
                .role(user.getRole())
                .build();
    }
}