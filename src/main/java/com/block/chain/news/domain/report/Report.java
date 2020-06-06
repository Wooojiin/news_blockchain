package com.block.chain.news.domain.report;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class    Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reportId;

    @OneToOne
    private Post post;

    @Column
    @ColumnDefault("0")
    private int disagree;

    @Column
    @ColumnDefault("0")
    private int agree;

    @Column(nullable=false)
    private String contents;

    @Column
    private LocalDate endDate;

    @Column(nullable=false)
    private String reporter;

    @Builder
    public Report(Post post, String contents,String reporter){
        this.post = post;
        this.contents = contents;
        this.reporter = reporter;
        this.disagree = 0;
        this.agree = 0;
        this.endDate = LocalDate.now().plusMonths(1);
    }

    public void agree(){
        this.agree += 1;
    }
    public void disagree(){
        this.disagree += 1;
    }
}
