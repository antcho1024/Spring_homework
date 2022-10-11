package com.sparta.week01_homework.controller;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.dto.BoardPasswordDto;
import com.sparta.week01_homework.dto.BoardRequestDto;
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
public class BoardRestController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/api/post")
    public Page<Board> getPosts(@PageableDefault(page = 0, size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) { //sort = "createdAt", direction = Sort.Direction.DESC
        return boardRepository.findAll(pageable);
    }
    @GetMapping("/api/post/{id}")
    public Board getPostOne(@PathVariable Long id) {
        //여기 보드 디티오? 그냥 보드?
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
    }
    @GetMapping("/api/post/category/{category}")
    public List<Board> getCategory(@PathVariable Integer category) {
//        return boardRepository.findAll(pageable);
//        return boardRepository.findBycategory(boardService.toCategory(category));
        return boardRepository.findBycategory(Category.find(category));
    }
    @PostMapping("/api/post")
    public Board createPost(@RequestBody BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        return boardRepository.save(board);
    }
    @PostMapping("/api/post/{id}")
    public Boolean checkPassword(@PathVariable Long id, @RequestBody BoardPasswordDto boardPasswordDto) {
        return boardService.checkPassword(id, boardPasswordDto);
    }
    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.update(id, boardRequestDto);
    }
    @DeleteMapping("/api/post/{id}")
    public void delete(@PathVariable Long id){
        boardRepository.deleteById(id);
    }
}
