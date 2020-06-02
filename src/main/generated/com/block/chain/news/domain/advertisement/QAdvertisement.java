package com.block.chain.news.domain.advertisement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdvertisement is a Querydsl query type for Advertisement
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdvertisement extends EntityPathBase<Advertisement> {

    private static final long serialVersionUID = 1413078738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdvertisement advertisement = new QAdvertisement("advertisement");

    public final NumberPath<Long> advertisementId = createNumber("advertisementId", Long.class);

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.block.chain.news.domain.user.QUser user;

    public QAdvertisement(String variable) {
        this(Advertisement.class, forVariable(variable), INITS);
    }

    public QAdvertisement(Path<? extends Advertisement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdvertisement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdvertisement(PathMetadata metadata, PathInits inits) {
        this(Advertisement.class, metadata, inits);
    }

    public QAdvertisement(Class<? extends Advertisement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.block.chain.news.domain.user.QUser(forProperty("user")) : null;
    }

}

