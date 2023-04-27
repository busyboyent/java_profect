package com.dev.javadevproject.dto.goods;

import com.dev.javadevproject.entities.ProductEntity;

import java.util.List;

public class ProductResponse {
    public List<ProductEntity> items;
    public int count;

    public ProductResponse(List<ProductEntity> items){
        this.items = items;
        this.count = items.size();
    }
}
