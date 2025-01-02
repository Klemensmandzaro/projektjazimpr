package org.example.repositories;


import org.example.model.ItemMedia;
import org.example.model.ItemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMediaRepository extends JpaRepository<ItemMedia, Long> {
    List<ItemMedia> findByIdBetween(Long start, Long end);
}
