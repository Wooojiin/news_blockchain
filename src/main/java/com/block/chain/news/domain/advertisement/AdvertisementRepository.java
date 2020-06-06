package com.block.chain.news.domain.advertisement;

import com.block.chain.news.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByUser(User user);

    List<Advertisement> findAllByEndDateAfter(LocalDate now);
}
