package com.sparta.week01_homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.dto.UserDto;
import com.sparta.week01_homework.entity.Timestamped;
import com.sparta.week01_homework.service.CustomUserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column (nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(String title, String content, Category category, User user){
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
    }
    public Board(BoardRequestDto boardRequestDto, User user){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.category = boardRequestDto.getCategory();
        this.user = user;
    }
    public void update(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.category = boardRequestDto.getCategory();
    }
}
