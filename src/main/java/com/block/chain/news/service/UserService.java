package com.block.chain.news.service;

import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String save(UserSaveRequestDto requestDto){
        return userRepository.save(requestDto.toEntity()).getEmail();
    }
}
