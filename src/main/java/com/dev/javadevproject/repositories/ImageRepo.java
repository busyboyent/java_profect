package com.dev.javadevproject.repositories;

import com.dev.javadevproject.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<ImageEntity, Integer> {  }
