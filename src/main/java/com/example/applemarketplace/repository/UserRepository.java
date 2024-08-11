package com.example.applemarketplace.repository;
import com.example.applemarketplace.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByContactEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username != :excludedUsername")
    List<User> findAllUsersExcludingUsername(String excludedUsername);
}
