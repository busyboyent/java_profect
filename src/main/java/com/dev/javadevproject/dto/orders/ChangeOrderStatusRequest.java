package com.dev.javadevproject.dto.orders;

import com.dev.javadevproject.entities.OrderStatus;

public class ChangeOrderStatusRequest {
    public Integer orderId;
    public OrderStatus newStatus;
}
