package com.block.chain.news.web.dto;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.subject.Subject;
import lombok.Getter;

import java.util.List;

@Getter
public class SuggestionList implements Comparable<SuggestionList> {
    private Subject subject;
    private int similarity;
    private List<Post> posts;

    public SuggestionList(Subject subject, int similarity){
        this.subject = subject;
        this.similarity = similarity;
        this.posts = subject.getPosts();
    }

    @Override
    public int compareTo(SuggestionList o) {
        return (o.similarity - this.similarity);
    }
}
