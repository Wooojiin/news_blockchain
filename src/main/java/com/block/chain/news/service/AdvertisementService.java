package com.block.chain.news.service;

import com.block.chain.news.domain.advertisement.AdvertisementRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.advertisement.AdvertisementSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long support(AdvertisementSaveRequestDto requestDto){
        User user = userRepository.findByEmail(requestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getUserEmail()));

        return advertisementRepository.save(requestDto.toEntity(user)).getAdvertisementId();
    }
}
