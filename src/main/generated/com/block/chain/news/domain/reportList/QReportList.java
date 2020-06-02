package com.block.chain.news.domain.reportList;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportList is a Querydsl query type for ReportList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReportList extends EntityPathBase<ReportList> {

    private static final long serialVersionUID = 1416574244L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportList reportList = new QReportList("reportList");

    public final NumberPath<Integer> decision = createNumber("decision", Integer.class);

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public final NumberPath<Long> reportListId = createNumber("reportListId", Long.class);

    public final com.block.chain.news.domain.user.QUser user;

    public QReportList(String variable) {
        this(ReportList.class, forVariable(variable), INITS);
    }

    public QReportList(Path<? extends ReportList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportList(PathMetadata metadata, PathInits inits) {
        this(ReportList.class, metadata, inits);
    }

    public QReportList(Class<? extends ReportList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.block.chain.news.domain.user.QUser(forProperty("user")) : null;
    }

}

