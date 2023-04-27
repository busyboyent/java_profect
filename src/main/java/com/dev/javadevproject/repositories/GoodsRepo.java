package com.dev.javadevproject.repositories;

import com.dev.javadevproject.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepo extends JpaRepository<ProductEntity, Integer> {
    boolean existsByName(String name);
    ProductEntity findByName(String name);
    List<ProductEntity> findByDescriptionContainingIgnoreCase(String word);
    List<ProductEntity> findByNameContainingIgnoreCase(String word);
    @Query("SELECT DISTINCT a.category FROM product_entity a")
    List<String> findDistinctCategory();

    List<ProductEntity> findAllByCategory(String category);
}
