package com.kenji.web_order.entity;

import com.kenji.web_order.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "category_name", length = 200)
    String name;

    @Column(name = "category_code")
    String categoryCode;

    @Column(name = "is_active")
    boolean active;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    List<Category> subCategories;

    @OneToMany(mappedBy = "category")
    List<Product> products;
}
