package com.dev.javadevproject.repositories;

import com.dev.javadevproject.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepo extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByUserId(int userId);
}
