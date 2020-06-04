package com.block.chain.news.domain.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static com.block.chain.news.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<Post> findByTopics(String topics){
//        String [] targets = topics.split(",");
//        List<Post> resultSet = new LinkedList<>();
//        for (String target : targets){
//            List<Post> result = queryFactory.selectDistinct(post)
//                    .from(post)
//                    .where(post.topics.contains(target))
//                    .fetch();
//            for(Post idx : result){
//                if (! resultSet.contains(idx)){
//                    resultSet.add(idx);
//                }
//            }
//        }
//        return resultSet;
//    }
}
