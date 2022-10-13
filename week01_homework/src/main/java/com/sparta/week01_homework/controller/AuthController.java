package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.repository.UserRepository;
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
    private final UserRepository userRepository;

//    @PostMapping("/test")
//    public void testAuth(@AuthenticationPrincipal CustomUserDetailsImpl userDetails){
//        //HttpServletRequest request
//        //request.getHeader()
//        System.out.println("dkdkdkdkd");
//        System.out.println(userDetails.getUsername());
//    }

    @PostMapping("/test")
    public String testAuth(HttpServletRequest request){

        System.out.println("------유저정보------\n\n" + SecurityUtil.getCurrentMemberName() + "\n\n----------------");
        return "test";
    }

    // 작성
    @PostMapping("/post")
    public String createPost(@RequestBody BoardRequestDto boardRequestDto) {
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);
        Board board = new Board(boardRequestDto,user);
//        user.getBoards().add(board);

        boardRepository.save(board);
        return ""; //컨트롤러에서는 디티오로 반환하기
    }
    //수정
    @PutMapping("/post/{id}")
    public void update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);
        boardService.update(id, boardRequestDto, user);
    }
    //삭제
    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable Long id){
        User user = userRepository.findOneByUsername(SecurityUtil.getCurrentMemberName()).orElse(null);
        boardService.delete(id, user);
    }
    //댓글
}
