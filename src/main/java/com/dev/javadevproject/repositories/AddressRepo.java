package com.dev.javadevproject.repositories;

import com.dev.javadevproject.entities.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepo extends CrudRepository<AddressEntity, Long> {
    boolean existsByPostalCode(Integer postalCode);
    AddressEntity findByPostalCode(Integer postalCode);
}
