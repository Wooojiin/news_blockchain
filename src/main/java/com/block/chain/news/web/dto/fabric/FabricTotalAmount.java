package com.block.chain.news.web.dto.fabric;

public class FabricTotalAmount {
    //totalAmount
    String totalAmount;

    public FabricTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "FabricTotalAmount{" +
                "totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
