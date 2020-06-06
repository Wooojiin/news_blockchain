package com.block.chain.news.domain.subject;

import com.block.chain.news.domain.post.Post;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.block.chain.news.domain.post.QPost.post;
import static com.block.chain.news.domain.subject.QSubject.subject;

import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepositoryCustom{
    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<Subject> findByTopics(String topics) {
//        String[] target = topics.split(",");
//        List<Subject> resultSet = new LinkedList<>();
//        for (String idx : target) {
//            List<Subject> newOne = queryFactory.selectDistinct(subject)
//                    .from(subject)
//                    .where((subject.title).contains(idx))
//                    .fetch();
//            for (Subject subject : newOne) {
//                if (!resultSet.contains(subject)) {
//                    resultSet.add(subject);
//                }
//            }
//        }
//        return resultSet;
//    }

    @Override
    public List<Subject> findByTopics(String topics){
        String [] targets = topics.split(",");
        BooleanBuilder builder = new BooleanBuilder();
        for(String target : targets){
            builder.or((subject.title).contains(target));
        }

        return queryFactory
                .selectDistinct(subject)
                .from(subject)
                .where(builder)
                .fetch();
    }
}
