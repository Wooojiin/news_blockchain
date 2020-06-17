package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FabricUserAccount {
    String userId;
    String amount;

    public FabricUserAccount(String userId, String amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
