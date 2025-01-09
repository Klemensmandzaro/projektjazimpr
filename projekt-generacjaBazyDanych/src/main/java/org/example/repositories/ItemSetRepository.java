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
public interface ItemSetRepository extends JpaRepository<ItemSet, Long> {
    Optional<ItemSet> findBySetName(String name);
    List<ItemSet> findByIdBetween(Long start, Long end);
    @Query("SELECT i FROM ItemSet i WHERE i.id >= :start ORDER BY i.id ASC")
    List<ItemSet> findByIdGreaterThanOrEqual(@Param("start") Long start, Pageable pageable);
}
