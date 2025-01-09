package org.example.repositories;

import org.example.model.Item;
import org.example.model.ItemSpells;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemSpellsRepository extends JpaRepository<ItemSpells, Long> {
    Optional<ItemSpells> findByName(String name);
    List<ItemSpells> findByIdBetween(Long start, Long end);
    Optional<ItemSpells> findFirstByDescription(String description);
    @Query("SELECT i FROM ItemSpells i WHERE i.id >= :start ORDER BY i.id ASC")
    List<ItemSpells> findByIdGreaterThanOrEqual(@Param("start") Long start, Pageable pageable);

    @Query("SELECT i FROM ItemSpells i WHERE i.name LIKE :prefix%")
    List<ItemSpells> findByNameStartingWith(@Param("prefix") String prefix);


}
