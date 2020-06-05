package com.block.chain.news.web.dto.fabric;

public class FabricUserAccount {
    String userID;
    String amount;

    public FabricUserAccount(String userID, String amount) {
        this.userID = userID;
        this.amount = amount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FabricUserAccount{" +
                "userID='" + userID + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
