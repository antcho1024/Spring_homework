package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.dto.CommentRequestDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.entity.Comment;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.repository.CommentRepository;
import com.sparta.week01_homework.repository.UserRepository;
import com.sparta.week01_homework.service.CommentService;
import com.sparta.week01_homework.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentService commentService;
    // 댓글 목록 조회 -인증 없이 가능
    @GetMapping("")
    public List<Comment> getPosts() { //@PageableDefault(page = 0, size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
        return commentRepository.findAll();
    }

    //댓글 작성
    @PostMapping("/post")
    public String createPost(@RequestBody CommentRequestDto commentRequestDto) {
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);

        Board board = boardRepository.findById(commentRequestDto.getBoard_id()).orElse(null);
        Comment comment = new Comment(commentRequestDto,user, board);
        commentRepository.save(comment);
        return "";
    }
    //댓글 수정
    @PutMapping("/post/{id}")
    public void update(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);
        commentService.update(id, commentRequestDto, user);
    }
    //댓글 삭제
    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable Long id){
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);
        commentService.delete(id, user);
    }
}
