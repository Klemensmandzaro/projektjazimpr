package org.example.repositories;


import org.example.model.ItemStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStatsRepository extends JpaRepository<ItemStats, Long> {}
