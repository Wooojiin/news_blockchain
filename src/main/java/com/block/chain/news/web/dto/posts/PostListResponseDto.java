package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListResponseDto {
//    private Long id;
//    private String title;
//    private String author;
//    private String banner;
//    private String state;
    private List<Post> savedPost;
    private List<Post> otherPost;

    public PostListResponseDto(List<Post> savedPost, List<Post> otherPost){
//        this.id = entity.getPostId();
//        this.title=entity.getTitle();
//        this.author = entity.getAuthor();
////        this.content = entity.getContent();
//        this.banner = entity.getBanner();
//        this.state = entity.getState();
        this.savedPost = savedPost;
        this.otherPost = otherPost;
    }
}
