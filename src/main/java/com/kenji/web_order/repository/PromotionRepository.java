package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Promotion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByInStockTrue(Pageable pageable);
}
