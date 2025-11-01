package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
