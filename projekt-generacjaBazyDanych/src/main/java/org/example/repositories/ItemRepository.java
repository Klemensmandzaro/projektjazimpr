package org.example.repositories;

import org.example.model.Item;
import org.example.model.ItemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByBlizzardId(Long id);
}
