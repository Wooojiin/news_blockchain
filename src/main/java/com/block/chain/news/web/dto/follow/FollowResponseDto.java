package com.block.chain.news.web.dto.follow;

import com.block.chain.news.domain.follow.Follow;
import com.block.chain.news.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FollowResponseDto {
    private List<String> follower;
    private List<String> following;

    public FollowResponseDto(List<Follow> followers, List<Follow> followings){
//        List<Follow> followers = entity.getFollower();
//        List<Follow> followings = entity.getFollowing();
        List<String> follower = new ArrayList<>();
        List<String> following = new ArrayList<>();

//        for (Follow followerUser : followers) {
//            follower.add(followerUser.getFromUser().getEmail());
//        }

//        for (Follow followingUser : followings) {
//            following.add(followingUser.getToUser().getEmail());
//        }
        for (Follow followerUser : followers){  // 내 아이디를 toUser로 찾아온것
            follower.add(followerUser.getFromUser());
        }
        for (Follow followingUser : followings){ // 내 아이디를 fromUser로 찾아온것
            following.add(followingUser.getToUser());
        }

        this.follower = follower;
        this.following = following;
    }
}
