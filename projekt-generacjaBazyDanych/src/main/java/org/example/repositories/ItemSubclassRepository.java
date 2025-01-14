package org.example.repositories;


import org.example.model.ItemSubclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemSubclassRepository extends JpaRepository<ItemSubclass, Long> {
    Optional<ItemSubclass> findBySubclassName(String name);
    Optional<ItemSubclass> findByItemClassId(Long id);
}
