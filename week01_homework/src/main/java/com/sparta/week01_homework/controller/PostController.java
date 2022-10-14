package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.dto.BoardResponseDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final BoardRepository boardRepository;

    // 전체 게시글 조회
    @GetMapping("")
    public List<Board> getPosts() { //@PageableDefault(page = 0, size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
        return boardRepository.findAll();
    }

    // 게시글 1개 조회
    @GetMapping("/{id}")
    public Board getPostOne(@PathVariable Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
    }

    // 카테고리 별 조회
    @GetMapping("/category/{category}")
    public List<Board> getCategory(@PathVariable Integer category) {
        return boardRepository.findBycategory(Category.find(category));
    }
}
