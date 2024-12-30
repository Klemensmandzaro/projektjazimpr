package org.example.repositories;


import org.example.model.ItemMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMediaRepository extends JpaRepository<ItemMedia, Long> {}
