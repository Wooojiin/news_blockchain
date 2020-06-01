package com.block.chain.news.web;

import com.block.chain.news.service.AdvertisementService;
import com.block.chain.news.web.dto.advertisement.AdvertisementSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdvertisementApiController {
    private final AdvertisementService advertisementService;

    @PostMapping("/api/v1/advertisement")
    public ResponseEntity<Long> save(@RequestParam(value = "content") String content,
                                     @RequestParam(value = "image") MultipartFile image,
                                     @RequestParam(value = "price") int price,
                                     @RequestParam(value = "months") int months,
                                     @RequestParam(value = "userEmail") String userEmail) throws Exception{

        log.info("Advertisement save");
        AdvertisementSaveRequestDto requestDto = AdvertisementSaveRequestDto.builder()
                .content(content)
                .price(price)
                .months(months)
                .userEmail(userEmail)
                .build();

        return new ResponseEntity<Long>(advertisementService.support(requestDto, image), HttpStatus.OK);
    }
}
