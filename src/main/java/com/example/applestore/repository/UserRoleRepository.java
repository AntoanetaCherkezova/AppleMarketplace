package com.example.applestore.repository;
import com.example.applestore.model.entity.UserRole;
import com.example.applestore.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByName(Role role);
}

