package com.block.chain.news.web.dto.subject;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.subject.Subject;
import lombok.Getter;

import java.util.List;

@Getter
public class SubjectItem {
    private Subject subject;
    private List<Post> posts;

    public SubjectItem(Subject subject, List<Post> posts){
        this.subject = subject;
        this.posts = posts;
    }
}
