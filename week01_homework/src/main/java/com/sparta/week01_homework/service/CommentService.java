package com.sparta.week01_homework.service;

import com.sparta.week01_homework.dto.CommentRequestDto;
import com.sparta.week01_homework.dto.ResponseDto;
import com.sparta.week01_homework.entity.Board;
import com.sparta.week01_homework.entity.Comment;
import com.sparta.week01_homework.entity.User;
import com.sparta.week01_homework.repository.BoardRepository;
import com.sparta.week01_homework.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@Service // 서비스임을 선언합니다.
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public ResponseDto<?> update(Long id, CommentRequestDto requestDto, User user) {
        Board board = boardRepository.findById(requestDto.getBoard_id()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.getBoard().getUser()!=user) return ResponseDto.fail("BAD_REQUEST","작성자만 수정 가능합니다.");
        comment.update(requestDto);
        return ResponseDto.success("data success");
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public ResponseDto<?> delete(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if(comment.getBoard().getUser()!=user) return ResponseDto.fail("BAD_REQUEST","작성자만 삭제 가능합니다.");
        commentRepository.deleteById(id);
        return ResponseDto.success("data success");
    }
}
