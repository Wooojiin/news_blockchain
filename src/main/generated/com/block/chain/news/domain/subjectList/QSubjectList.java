package com.block.chain.news.domain.subjectList;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubjectList is a Querydsl query type for SubjectList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubjectList extends EntityPathBase<SubjectList> {

    private static final long serialVersionUID = 1387663324L;

    public static final QSubjectList subjectList = new QSubjectList("subjectList");

    public final com.block.chain.news.domain.QBaseTimeEntity _super = new com.block.chain.news.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> subjectId = createNumber("subjectId", Long.class);

    public final NumberPath<Long> subjectListId = createNumber("subjectListId", Long.class);

    public QSubjectList(String variable) {
        super(SubjectList.class, forVariable(variable));
    }

    public QSubjectList(Path<? extends SubjectList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjectList(PathMetadata metadata) {
        super(SubjectList.class, metadata);
    }

}

