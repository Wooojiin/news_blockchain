package com.block.chain.news.domain.post;

import com.block.chain.news.web.dto.posts.PostResponseDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long>{

    @Query("SELECT p FROM  Post p ORDER BY p.postId DESC")
    List<Post> findAllDesc();

    List<Post> findAllByKindsEquals(int kinds);

    List<Post> findAllByAuthor(String author);
}

