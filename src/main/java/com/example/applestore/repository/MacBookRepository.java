package com.example.applestore.repository;

import com.example.applestore.model.entity.MacBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MacBookRepository extends JpaRepository<MacBook, Long> {

    @Query("SELECT m FROM MacBook m ORDER BY m.releaseDate DESC")
    List<MacBook> findLatestModelMacBook();
}