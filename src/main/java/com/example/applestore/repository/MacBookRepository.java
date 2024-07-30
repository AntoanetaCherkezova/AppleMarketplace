package com.example.applestore.repository;
import com.example.applestore.model.entity.MacBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MacBookRepository extends JpaRepository<MacBook, Long> {

}