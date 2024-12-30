package org.example.repositories;


import org.example.model.ItemClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemClassRepository extends JpaRepository<ItemClass, Long> {
    Optional<ItemClass> findByClassName(String name);
}