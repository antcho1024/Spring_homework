package com.sparta.week01_homework.entity;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(String content, User user, Board board){
        this.content = content;
        this.author = user.getUsername();
        this.board = board;
    }
    public Comment(CommentRequestDto commentRequestDto, User user, Board board){
        this.content = commentRequestDto.getContent();
        this.author = user.getUsername();
        this.board = board;
    }
    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }
}
