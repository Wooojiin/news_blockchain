package com.block.chain.news.domain.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubject is a Querydsl query type for Subject
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubject extends EntityPathBase<Subject> {

    private static final long serialVersionUID = -507046816L;

    public static final QSubject subject = new QSubject("subject");

    public final com.block.chain.news.domain.QBaseTimeEntity _super = new com.block.chain.news.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<com.block.chain.news.domain.post.Post, com.block.chain.news.domain.post.QPost> posts = this.<com.block.chain.news.domain.post.Post, com.block.chain.news.domain.post.QPost>createList("posts", com.block.chain.news.domain.post.Post.class, com.block.chain.news.domain.post.QPost.class, PathInits.DIRECT2);

    public final NumberPath<Long> SubjectId = createNumber("SubjectId", Long.class);

    public final StringPath title = createString("title");

    public QSubject(String variable) {
        super(Subject.class, forVariable(variable));
    }

    public QSubject(Path<? extends Subject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubject(PathMetadata metadata) {
        super(Subject.class, metadata);
    }

}

