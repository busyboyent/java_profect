package com.dev.javadevproject.dto.orders;

import java.util.List;

public class CartResponse {
    public List<String> items;

    public CartResponse(List<String> items) {
        this.items = items;
    }
}
