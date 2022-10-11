package com.sparta.week01_homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false)
    private String author;
    @JsonIgnore
    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Category category;

    public Board(String title, String author, String password, String content, Category category){
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
        this.category = category;
    }
    public Board(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.author = boardRequestDto.getAuthor();
        this.password = boardRequestDto.getPassword();
        this.content = boardRequestDto.getContent();
        this.category = boardRequestDto.getCategory();
    }
    public void update(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.author = boardRequestDto.getAuthor();
        this.password = boardRequestDto.getPassword();
        this.content = boardRequestDto.getContent();
        this.category = boardRequestDto.getCategory();
    }
}
