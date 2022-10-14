package com.sparta.week01_homework.service;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.dto.BoardResponseDto;
import com.sparta.week01_homework.dto.ResponseDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.dto.BoardPasswordDto;
import com.sparta.week01_homework.dto.BoardRequestDto;
import com.sparta.week01_homework.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public ResponseDto<?> update(Long id, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if(board.getUser()!=user) return ResponseDto.fail("BAD_REQUEST","작성자만 수정 가능합니다.");
        board.update(requestDto);
        return ResponseDto.success("data success");
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public ResponseDto<?> delete(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        if(board.getUser()!=user) return ResponseDto.fail("BAD_REQUEST","작성자만 삭제 가능합니다.");
        boardRepository.deleteById(id);
        return ResponseDto.success("data success");
    }

    public List<BoardResponseDto> toBoardList(User user){
        List<BoardResponseDto> responseDtos = new ArrayList<>();
        for(Board board : user.getMySavePost()){
            BoardResponseDto dto = new BoardResponseDto(board);
            responseDtos.add(dto);
        }
        return responseDtos;
    }

    public Category toCategory(Integer category){
        if(category == Category.Study.ordinal()) return Category.Study;
        else if (category == Category.Game.ordinal()) return Category.Game;
        return Category.Play;
    }

}
