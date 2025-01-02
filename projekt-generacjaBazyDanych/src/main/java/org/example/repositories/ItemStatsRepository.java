package org.example.repositories;


import org.example.model.Item;
import org.example.model.ItemStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemStatsRepository extends JpaRepository<ItemStats, Long> {
    List<ItemStats> findByIdBetween(Long start, Long end);
}
