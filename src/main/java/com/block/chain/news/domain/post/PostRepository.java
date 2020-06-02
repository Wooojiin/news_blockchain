package com.block.chain.news.domain.post;

import com.block.chain.news.web.dto.posts.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 이거 desc 굳이 없어도 될것 같긴한데 최신거 부터 보여줄려면 DESC 있어야할듯
//    List<Post> findAllOrderByPostIdDesc();

    @Query("SELECT p FROM  Post p ORDER BY p.postId DESC")
    List<Post> findAllDesc();

}

