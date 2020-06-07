package com.block.chain.news.web.dto.fabric;

import lombok.Getter;

@Getter
public class FabricUserAccount {
    String userID;
    String amount;

    public FabricUserAccount(String userID, String amount) {
        this.userID = userID;
        this.amount = amount;
    }
}
