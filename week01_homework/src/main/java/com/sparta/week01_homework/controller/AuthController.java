package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.service.BoardService;
import com.sparta.week01_homework.service.CustomUserDetailsImpl;
import com.sparta.week01_homework.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

//    @PostMapping("/test")
//    public void testAuth(@AuthenticationPrincipal CustomUserDetailsImpl userDetails){
//        System.out.println("dkdkdkdkd");
//        System.out.println(userDetails.getUsername());
//    }

    @PostMapping("/test")
    public String testAuth(){
        System.out.println("------유저정보------\n\n" + SecurityUtil.getCurrentMemberName() + "\n\n----------------");
        return "test";
    }

    // 작성
    @PostMapping("/post")
    public Board createPost(@RequestBody BoardRequestDto boardRequestDto) {
//        Board board = new Board(boardRequestDto,);
//        return boardRepository.save(board);
    }
    //수정
    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.update(id, boardRequestDto);
    }
    //삭제
    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable Long id){
        boardRepository.deleteById(id);
    }
    //댓글
}
