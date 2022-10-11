package com.sparta.week01_homework.repository;

import com.sparta.week01_homework.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 해당 쿼리가 수행이 될 때 Lazy 조회가 아니고 Eager 조회로 authorities정보를 같이 갖고옴
//    @EntityGraph(attributePaths = "authorities")
    //유저 이름을 기준으로 갖고 오는데 권한정보도 같이 갖고옴
    Optional<User> findOneByUsername(String username);
//    Optional<User> findOneWithAuthoritiesByUsername(String username);

}
