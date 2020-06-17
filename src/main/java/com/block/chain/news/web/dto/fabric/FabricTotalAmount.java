package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FabricTotalAmount {
    String totalAmount;

    public FabricTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
