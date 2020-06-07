package com.block.chain.news.domain.follow;

import com.block.chain.news.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    void deleteByFollowId(Long followId);
}
