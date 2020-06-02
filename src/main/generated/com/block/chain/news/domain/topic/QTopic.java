package com.block.chain.news.domain.topic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTopic is a Querydsl query type for Topic
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTopic extends EntityPathBase<Topic> {

    private static final long serialVersionUID = -1605574490L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTopic topic = new QTopic("topic");

    public final StringPath content = createString("content");

    public final com.block.chain.news.domain.post.QPost post;

    public final NumberPath<Double> similarity = createNumber("similarity", Double.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public QTopic(String variable) {
        this(Topic.class, forVariable(variable), INITS);
    }

    public QTopic(Path<? extends Topic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTopic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTopic(PathMetadata metadata, PathInits inits) {
        this(Topic.class, metadata, inits);
    }

    public QTopic(Class<? extends Topic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.block.chain.news.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

