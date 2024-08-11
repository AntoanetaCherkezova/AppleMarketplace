package com.example.applemarketplace.repository;
import com.example.applemarketplace.model.entity.UserRole;
import com.example.applemarketplace.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByName(Role role);
}

