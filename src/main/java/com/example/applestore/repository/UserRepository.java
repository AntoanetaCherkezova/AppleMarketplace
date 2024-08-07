package com.example.applestore.repository;
import com.example.applestore.model.entity.User;
import com.example.applestore.service.UserDetailsServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
