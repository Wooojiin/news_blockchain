package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FabricUserView {
    String userId;
    String count;

    public FabricUserView(String userId, String count) {
        this.userId = userId;
        this.count = count;
    }
}
