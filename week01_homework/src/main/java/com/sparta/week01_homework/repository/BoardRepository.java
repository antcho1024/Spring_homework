package com.sparta.week01_homework.repository;

import com.sparta.week01_homework.Category;
import com.sparta.week01_homework.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBycategory(Category category);
}
