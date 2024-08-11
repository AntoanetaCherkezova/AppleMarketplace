package com.example.applemarketplace.repository;
import com.example.applemarketplace.model.entity.MacBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MacBookRepository extends JpaRepository<MacBook, Long> {

    @Query("SELECT m FROM MacBook m ORDER BY m.dateOfPurchase DESC")
    List<MacBook> findLatestModelMacBook();

    @Query(value = "SELECT * FROM mac_books ORDER BY date_of_register DESC LIMIT 10", nativeQuery = true)
    List<MacBook> findLatestMacBooks();

    @Query(value = "SELECT * FROM mac_books ORDER BY warranty ASC LIMIT 5", nativeQuery = true)
    List<MacBook> findLongestWarrantyMacBook();
}