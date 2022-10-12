package com.sparta.week01_homework.dto;

import com.sparta.week01_homework.Category;
import lombok.Getter;

import javax.persistence.Column;
import java.util.Locale;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private Category category;
}
