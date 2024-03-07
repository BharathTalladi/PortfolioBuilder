package com.investment.employeerecurringplans.repository;

import com.investment.employeerecurringplans.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Method to find a User entity by its emailId
    // Spring Data JPA automatically generates the query based on the method name
    Optional<User> findByEmailId(String emailId);
}
