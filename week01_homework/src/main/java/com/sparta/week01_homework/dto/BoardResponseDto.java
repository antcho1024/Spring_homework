package com.sparta.week01_homework.dto;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.entity.User;

public class BoardResponseDto {
    private String title;
    private String content;
    private Category category;
    private User user;

    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.user = board.getUser();
    }
}
