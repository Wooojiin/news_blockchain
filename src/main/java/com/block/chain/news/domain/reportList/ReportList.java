package com.block.chain.news.domain.reportList;

import com.block.chain.news.domain.report.Report;
import com.block.chain.news.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ReportList {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reportListId;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @Column(nullable=false)
    private Long reportId;

    @Column(nullable=false)
    private int decision;

    @Builder
    public ReportList(User user, Long reportId, int decision){
        this.user = user;
        this.reportId = reportId;
        this.decision = decision;
    }

}
