package com.kenji.web_order.repository;

import com.kenji.web_order.entity.Category;
import com.kenji.web_order.entity.Product;
import com.kenji.web_order.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryCode(String categoryCode);

    @Query("""
    SELECT p FROM Category p
    WHERE (:parentCategoryId IS NULL OR :parentCategoryId = 0 OR p.parentCategory.id = :parentCategoryId)
      AND (:keyword IS NULL OR :keyword = '' 
           OR p.name LIKE CONCAT('%', :keyword, '%'))
    """)
    List<Category> searchCategories
            (@Param("parentCategoryId") Long parentCategoryId,
             @Param("keyword") String keyword);
}
