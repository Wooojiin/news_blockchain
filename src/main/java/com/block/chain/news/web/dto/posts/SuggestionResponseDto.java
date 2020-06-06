package com.block.chain.news.web.dto.posts;

import com.block.chain.news.web.dto.subject.SubjectItem;
import lombok.Getter;

import java.util.List;

@Getter
public class SuggestionResponseDto {
    private List<SubjectItem> subjectItems;

    public SuggestionResponseDto(List<SubjectItem> subjectItems){
        this.subjectItems = subjectItems;
    }
}
