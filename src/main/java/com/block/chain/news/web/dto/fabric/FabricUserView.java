package com.block.chain.news.web.dto.fabric;

public class FabricUserView {
    String userID;
    String count;

    public FabricUserView(String userID, String count) {
        this.userID = userID;
        this.count = count;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FabricUserView{" +
                "userID='" + userID + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
