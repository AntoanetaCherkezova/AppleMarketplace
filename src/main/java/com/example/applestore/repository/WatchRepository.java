package com.example.applestore.repository;
import com.example.applestore.model.entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {

}