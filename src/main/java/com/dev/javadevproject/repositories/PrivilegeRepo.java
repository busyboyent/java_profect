package com.dev.javadevproject.repositories;

import com.dev.javadevproject.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepo extends JpaRepository<Privilege, Integer> {
    Privilege findByName(String name);
}
