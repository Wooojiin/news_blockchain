package com.block.chain.news.web.dto.advertisement;

import com.block.chain.news.domain.advertisement.Advertisement;
import com.block.chain.news.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdvertisementSaveRequestDto {
    private String content;
    private int price;
    private int months;
    private String userEmail;

    @Builder
    public AdvertisementSaveRequestDto(String content, int price, int months, String userEmail){
        this.content = content;
        this.price = price;
        this.months = months;
        this.userEmail = userEmail;
    }

    public Advertisement toEntity(User user, String imageUrl){
        return Advertisement.builder()
                .content(content)
                .image(imageUrl)
                .price(price)
                .endDate(LocalDate.now().plusMonths(months))
                .user(user)
                .build();
    }
}
