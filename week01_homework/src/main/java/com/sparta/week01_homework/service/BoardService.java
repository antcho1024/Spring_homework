package com.sparta.week01_homework.service;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.dto.BoardPasswordDto;
import com.sparta.week01_homework.dto.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return id;
    }
    public Category toCategory(Integer category){
        if(category == Category.Study.ordinal()) return Category.Study;
        else if (category == Category.Game.ordinal()) return Category.Game;
        return Category.Play;
    }
    public Boolean checkPassword(Long id, BoardPasswordDto boardPasswordDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        return board.getPassword().equals(boardPasswordDto.getPassword());
    }


}
