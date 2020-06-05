package com.block.chain.news.web.dto.fabric;

public class FabricAD {
    //args[0]: AdvertisementID, args[1]: UserID, args[2]: amount, args[3] : months
    String adID;
    String userID;
    String amount;
    String months;

    public FabricAD(String adID, String userID, String amount, String months) {
        this.adID = adID;
        this.userID = userID;
        this.amount = amount;
        this.months = months;
    }

    public String getAdID() {
        return adID;
    }

    public void setAdID(String adID) {
        this.adID = adID;
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

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    @Override
    public String toString() {
        return "FabricAD{" +
                "adID='" + adID + '\'' +
                ", userID='" + userID + '\'' +
                ", amount='" + amount + '\'' +
                ", months='" + months + '\'' +
                '}';
    }
}
