package com.kenji.web_order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", length = 200)
    private String name;

    @Column(name = "product_code", length = 100)
    private String productCode;

    @ElementCollection
    @CollectionTable(name = "product_prices", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "product_size")
    @Column(name = "product_price")
    private Map<String, BigDecimal> price; // size → price

    @Column(name = "product_img", length = 100)
    private String productImage;

    @Column(name = "product_description", length = 200)
    private String description;

    @Column(name = "in_stock")
    private boolean inStock;

    @Column(name = "in_popular")
    private boolean inPopular;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_materials",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> materials;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

}
