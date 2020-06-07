package com.block.chain.news.domain.follow;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFollow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFollow extends EntityPathBase<Follow> {

    private static final long serialVersionUID = -1326353340L;

    public static final QFollow follow = new QFollow("follow");

    public final com.block.chain.news.domain.QBaseTimeEntity _super = new com.block.chain.news.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> followId = createNumber("followId", Long.class);

    public final StringPath fromUser = createString("fromUser");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath toUser = createString("toUser");

    public QFollow(String variable) {
        super(Follow.class, forVariable(variable));
    }

    public QFollow(Path<? extends Follow> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFollow(PathMetadata metadata) {
        super(Follow.class, metadata);
    }

}

