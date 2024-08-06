package com.example.applestore.repository;
import com.example.applestore.model.entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {

    @Query("SELECT w FROM Watch w ORDER BY w.releaseDate DESC")
    List<Watch> findLatestModelWatch();

    @Query(value = "SELECT * FROM watches ORDER BY registered_on DESC LIMIT 10", nativeQuery = true)
    List<Watch>  findLatestWatches();

    @Query(value = "SELECT * FROM watches ORDER BY warranty DESC LIMIT 5", nativeQuery = true)
    List<Watch> findLongestWarrantyWatch();
}