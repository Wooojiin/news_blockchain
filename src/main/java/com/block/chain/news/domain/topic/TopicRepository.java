package com.block.chain.news.domain.topic;

import com.block.chain.news.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT g From Topic g WHERE g.post = ?1 ORDER BY g.topicId DESC")
    List<Topic> findByPostsDesc(Post postId);
}
