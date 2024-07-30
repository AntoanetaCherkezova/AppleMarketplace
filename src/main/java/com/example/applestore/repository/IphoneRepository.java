package com.example.applestore.repository;
import com.example.applestore.model.entity.Iphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IphoneRepository extends JpaRepository<Iphone, Long> {

}