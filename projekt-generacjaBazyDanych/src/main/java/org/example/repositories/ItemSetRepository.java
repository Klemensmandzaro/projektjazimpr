package org.example.repositories;


import org.example.model.ItemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemSetRepository extends JpaRepository<ItemSet, Long> {
    Optional<ItemSet> findBySetName(String name);
}
