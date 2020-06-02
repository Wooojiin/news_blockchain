package com.block.chain.news.domain.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import static com.block.chain.news.domain.post.QPost.post;
import java.util.List;

public class PostRepositorySupport extends QuerydslRepositorySupport {
    public final JPAQueryFactory queryFactory;

    public PostRepositorySupport(JPAQueryFactory queryFactory){
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    public List<Post> findByTopics(String topics){
        return queryFactory.selectFrom(post).fetch();
    }
}
