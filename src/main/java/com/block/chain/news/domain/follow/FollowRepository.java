package com.block.chain.news.domain.follow;

import com.block.chain.news.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    @Query(value = "delete from follow where follow_id = :followId", nativeQuery = true)
    void deleteFollow(@Param("followId")Long followId);
}
