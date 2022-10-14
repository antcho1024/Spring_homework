package com.sparta.week01_homework.dto;

import lombok.Getter;

@Getter

public class CommentRequestDto {
    private Long board_id;
    private String content;
}
