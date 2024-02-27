package com.investment.employeerecurringplans.repository;

import com.investment.employeerecurringplans.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailId(String emailId);

}
