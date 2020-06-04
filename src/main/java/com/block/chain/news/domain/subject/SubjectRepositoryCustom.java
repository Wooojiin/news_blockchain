package com.block.chain.news.domain.subject;

import java.util.List;

public interface SubjectRepositoryCustom {
    List<Subject> findByTopics(String topics);
}
