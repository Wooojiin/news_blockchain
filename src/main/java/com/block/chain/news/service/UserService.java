package com.block.chain.news.service;

import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.user.UserResponseDto;
import com.block.chain.news.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FabricCCService fabricCCService;

    @Transactional
    public String save(UserSaveRequestDto requestDto){
        try{
            User user = userRepository.findByEmail(requestDto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("신규 회원"));
        } catch (Exception e){
//            fabricCCService.registerUser(requestDto.getEmail(), requestDto.getRole());
        }
        return userRepository.save(requestDto.toEntity()).getEmail();
    }

    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + id));

        return new UserResponseDto(user);
    }
}
