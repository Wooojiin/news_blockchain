package com.block.chain.news.web.dto;

import com.block.chain.news.domain.subject.Subject;
import lombok.Builder;
import lombok.Getter;


@Getter
public class SuggestionList implements Comparable<SuggestionList> {
    private Subject subject;
    private int similarity;


    public SuggestionList(Subject subject, int similarity){
        this.subject = subject;
        this.similarity = similarity;
    }

    @Override
    public int compareTo(SuggestionList o) {
        return (o.similarity - this.similarity);
    }
}
