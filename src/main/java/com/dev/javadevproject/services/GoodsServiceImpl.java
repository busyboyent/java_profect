package com.dev.javadevproject.services;

import com.dev.javadevproject.entities.ProductEntity;
import com.dev.javadevproject.repositories.GoodsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GoodsServiceImpl  {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private
    GoodsRepo goodsRepo;

    public ProductEntity loadProductByName(String name) {
        return goodsRepo.findByName(name);
    }

    public void Create(String name, String description, String category, Integer price, Integer discount){
        ProductEntity product = new ProductEntity();
        product.name = name;
        product.setDescription(description);
        product.category = category;
        product.setPrice(price);
        product.setDiscount(discount);
        goodsRepo.save(product);
    }

    public List<ProductEntity> getGoodsList(){
        List<ProductEntity> result = new ArrayList<>();
        Iterable<ProductEntity> goodsList = goodsRepo.findAll();
        for(ProductEntity item: goodsList) {
            result.add(item);
        }
        return result;
    }

    public List<ProductEntity> findGoodsBySearchRequest(String search) {
        Set<ProductEntity> result = new HashSet<>();
        result.addAll(goodsRepo.findByDescriptionContainingIgnoreCase(search));
        result.addAll(goodsRepo.findByNameContainingIgnoreCase(search));
        return new ArrayList<>(result);
    }

    public List<String> getCategoryList() {
        return goodsRepo.findDistinctCategory();
    }

    public List<ProductEntity> findGoodsByCategoryRequest(String category) {
        return goodsRepo.findAllByCategory(category);
    }

    public ProductEntity findById(String id) {
        return goodsRepo.findById(Integer.parseInt(id)).orElse(null);
    }
}