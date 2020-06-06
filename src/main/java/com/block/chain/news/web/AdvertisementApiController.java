package com.block.chain.news.web;

import com.block.chain.news.service.AdvertisementService;
import com.block.chain.news.web.dto.advertisement.AdvertisementListResponseDto;
import com.block.chain.news.web.dto.advertisement.AdvertisementSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/api/v1/advertisement/{email}")
    public ResponseEntity<List<AdvertisementListResponseDto>> findByUser(@PathVariable String email){
        log.info("Advertisement findByEmail : {}", email);

        return new ResponseEntity<List<AdvertisementListResponseDto>>(advertisementService.findByUser(email), HttpStatus.OK);
    }


}
