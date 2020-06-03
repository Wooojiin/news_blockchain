package com.block.chain.news.web.dto.advertisement;

import com.block.chain.news.domain.advertisement.Advertisement;
import lombok.Getter;

@Getter
public class AdvertisementListResponseDto {
    private String content;
    private String image;

    public AdvertisementListResponseDto(Advertisement entity){
        this.content = entity.getContent();
        this.image = entity.getImage();
    }
}
