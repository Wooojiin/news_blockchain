package com.block.chain.news.domain.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static com.block.chain.news.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findByQuery(String topics){
        String [] targets = topics.split(",");
        BooleanBuilder builder = new BooleanBuilder();
        for(String target : targets){
            builder.and(post.title.contains(target));
        }

        return queryFactory
                .selectDistinct(post)
                .from(post)
                .where(builder)
                .fetch();
    }

}
