package com.block.chain.news.domain.postList;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostList is a Querydsl query type for PostList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostList extends EntityPathBase<PostList> {

    private static final long serialVersionUID = -2078605148L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostList postList = new QPostList("postList");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> PostListId = createNumber("PostListId", Long.class);

    public final com.block.chain.news.domain.user.QUser user;

    public QPostList(String variable) {
        this(PostList.class, forVariable(variable), INITS);
    }

    public QPostList(Path<? extends PostList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostList(PathMetadata metadata, PathInits inits) {
        this(PostList.class, metadata, inits);
    }

    public QPostList(Class<? extends PostList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.block.chain.news.domain.user.QUser(forProperty("user")) : null;
    }

}

