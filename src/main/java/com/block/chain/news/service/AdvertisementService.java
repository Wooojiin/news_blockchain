package com.block.chain.news.service;

import com.block.chain.news.domain.advertisement.Advertisement;
import com.block.chain.news.domain.advertisement.AdvertisementRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.advertisement.AdvertisementSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

//    public static final String SAVE_FOLDER = "/home/ubuntu/image/";
//    public static final String IMAGE_URL = "http://i02b202.p.ssafy.io:8081/image/";
    public static final String SAVE_FOLDER = "C:/ssafy/news/src/main/resources/static/image/";
    public static final String IMAGE_URL = "localhost:8080/image/";


    @Transactional
    public Long support(AdvertisementSaveRequestDto requestDto, MultipartFile image) throws Exception {
        User user = userRepository.findByEmail(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getUserEmail()));

        String saveUrl = "none";

        if(!image.isEmpty()){
            String imageName = image.getOriginalFilename();
            String imageExtension = FilenameUtils.getExtension(imageName).toLowerCase();
            File destinationImage;
            String destinationImageName;
            String imageUrl = SAVE_FOLDER;

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyMMddHHmmss");
            destinationImageName = timeFormat.format(new Date()) + "." + imageExtension;
            destinationImage = new File(imageUrl + destinationImageName);

            log.info("Image uploaded : {}", destinationImageName);

            image.transferTo(destinationImage);
            saveUrl = IMAGE_URL + destinationImageName;
        }
        Advertisement advertisement = requestDto.toEntity(user, saveUrl);

        return advertisementRepository.save(advertisement).getAdvertisementId();
    }
}
