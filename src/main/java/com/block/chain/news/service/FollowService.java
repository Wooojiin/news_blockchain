package com.block.chain.news.service;

import com.block.chain.news.domain.follow.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
}
