package com.block.chain.news.web.dto.fabric;

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

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FabricNews{" +
                "newsID='" + newsID + '\'' +
                ", userID='" + userID + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
