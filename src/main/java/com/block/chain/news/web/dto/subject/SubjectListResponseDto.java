package com.block.chain.news.web.dto.subject;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class SubjectListResponseDto {
    private Long subjectListId;
    private String title;
    private List<Post> posts = new ArrayList<>();

    public SubjectListResponseDto(Long subjectListId, String title, List<Post> posts){
        this.subjectListId = subjectListId;
        this.title = title;
        this.posts = posts;
    }
}
