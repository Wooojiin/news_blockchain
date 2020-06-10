package com.block.chain.news.service;

import com.block.chain.news.domain.advertisement.Advertisement;
import com.block.chain.news.domain.advertisement.AdvertisementRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.advertisement.AdvertisementListResponseDto;
import com.block.chain.news.web.dto.advertisement.AdvertisementSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final FabricCCService fabricCCService;

    public static final String SAVE_FOLDER = "/home/ubuntu/image/";
    public static final String IMAGE_URL = "http://k02b2041.p.ssafy.io:8080/image/";
//    public static final String SAVE_FOLDER = "C:/ssafy/news/src/main/resources/static/image/";
//    public static final String IMAGE_URL = "localhost:8080/image/";


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
        Long adId = advertisementRepository.save(advertisement).getAdvertisementId();

        fabricCCService.registerAD(adId.toString(), user.getEmail(), requestDto.getPrice() + "", requestDto.getMonths() + "");

        return adId;
    }

    @Transactional(readOnly = true)
    public List<AdvertisementListResponseDto> findByUser(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + email));

        return advertisementRepository.findAllByUser(user).stream()
                .map(AdvertisementListResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean totalAmount(){
        List<Advertisement> advertisementList = advertisementRepository.findAllByEndDateAfter(LocalDate.now());

        String[] adList = new String[advertisementList.size()];

        for (int i = 0; i <  advertisementList.size(); i++) {
            adList[i] = advertisementList.get(i).getAdvertisementId().toString();
        }
        return fabricCCService.totalADAmountCalculation(adList);
    }

    public boolean divideAmount(){
        List<User> userList = userRepository.findAllByRole("JOURNALIST");

        String[] reporterList = new String[userList.size()];

        for (int i = 0; i < userList.size(); i++) {
            reporterList[i] = userList.get(i).getEmail();
        }

        return fabricCCService.divisionAmount(reporterList);
    }

    @Transactional
    public List<AdvertisementListResponseDto> findValidAd(){
        List<Advertisement> advertisementList = advertisementRepository.findAllByEndDateAfter(LocalDate.now());

        List<AdvertisementListResponseDto> responseDtoList = new LinkedList<>();
        for (Advertisement entity : advertisementList) {
            responseDtoList.add(new AdvertisementListResponseDto(entity));
        }

        return responseDtoList;
    }
}
