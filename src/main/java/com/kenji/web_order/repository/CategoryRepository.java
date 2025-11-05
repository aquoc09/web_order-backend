package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryCode(String categoryCode);
}
