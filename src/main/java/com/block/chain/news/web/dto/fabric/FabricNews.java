package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FabricNews {
    String newsID;
    String userID;
    String subject;
    String content;

    public FabricNews(String newsID, String userID, String subject, String content) {
        this.newsID = newsID;
        this.userID = userID;
        this.subject = subject;
        this.content = content;
    }
}
