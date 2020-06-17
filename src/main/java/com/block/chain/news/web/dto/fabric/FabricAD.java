package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
}
