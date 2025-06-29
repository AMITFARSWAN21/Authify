package com.authify.repository;

import com.authify.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarksRepository extends JpaRepository<Marks,Long> {
    Optional<Marks> findByName(String name);
}
