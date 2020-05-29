package com.block.chain.news.domain.advertisement;

import com.block.chain.news.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertisementId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @Builder
    public Advertisement(String content, String image, int price, LocalDate endDate, User user){
        this.content = content;
        this.image = image;
        this.price = price;
        this.endDate = endDate;
        this.user = user;
    }
}
