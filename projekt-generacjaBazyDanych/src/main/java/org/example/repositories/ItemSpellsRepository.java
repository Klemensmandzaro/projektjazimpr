package org.example.repositories;

import org.example.model.ItemSpells;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemSpellsRepository extends JpaRepository<ItemSpells, Long> {
    Optional<ItemSpells> findByName(String name);

}
