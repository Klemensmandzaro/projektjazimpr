package org.example.repositories;

import org.example.model.Item;
import org.example.model.ItemSet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByBlizzardId(Long id);
    Optional<Item> findTopByOrderByIdDesc();
    List<Item> findAll();
    List<Item> findFirst10ByOrderByIdAsc();
    List<Item> findByIdBetween(Long start, Long end);
    @Query("SELECT i FROM Item i WHERE i.id >= :start ORDER BY i.id ASC")
    List<Item> findByIdGreaterThanOrEqual(@Param("start") Long start, Pageable pageable);
    Optional<Item> findFirstByName(String name);
    List<Item> findByItemClassClassName(String itemClassName);
    List<Item> findByItemSubclassSubclassName(String itemSubclassName);
    List<Item> findByItemSetSetName(String itemSetName);
    List<Item> findByItemSpellsName(String itemSpellName);


}
