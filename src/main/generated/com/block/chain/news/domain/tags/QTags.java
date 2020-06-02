package com.block.chain.news.domain.tags;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTags is a Querydsl query type for Tags
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTags extends EntityPathBase<Tags> {

    private static final long serialVersionUID = 605211524L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTags tags = new QTags("tags");

    public final StringPath content = createString("content");

    public final com.block.chain.news.domain.post.QPost post;

    public final NumberPath<Long> SelectedId = createNumber("SelectedId", Long.class);

    public final NumberPath<Double> similarity = createNumber("similarity", Double.class);

    public QTags(String variable) {
        this(Tags.class, forVariable(variable), INITS);
    }

    public QTags(Path<? extends Tags> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTags(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTags(PathMetadata metadata, PathInits inits) {
        this(Tags.class, metadata, inits);
    }

    public QTags(Class<? extends Tags> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.block.chain.news.domain.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

