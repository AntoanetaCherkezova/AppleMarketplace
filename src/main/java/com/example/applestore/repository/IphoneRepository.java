package com.example.applestore.repository;
import com.example.applestore.model.entity.Iphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IphoneRepository extends JpaRepository<Iphone, Long> {

    @Query("SELECT i FROM Iphone i ORDER BY i.internalMemory DESC")
    List<Iphone> findIphoneWithLargestMemory();

    @Query("SELECT i FROM Iphone i ORDER BY i.releaseDate DESC")
    List<Iphone>findLatestModelIphone();
}