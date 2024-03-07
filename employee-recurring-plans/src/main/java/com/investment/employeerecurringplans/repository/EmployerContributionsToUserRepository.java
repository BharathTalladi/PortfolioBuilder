package com.investment.employeerecurringplans.repository;

import com.investment.employeerecurringplans.entity.EmployerContributionsToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerContributionsToUserRepository extends JpaRepository<EmployerContributionsToUser, String> {

    // Method to find an EmployerContributionsToUser entity by its employeeId
    // Spring Data JPA automatically generates the query based on the method name
    Optional<EmployerContributionsToUser> findByEmployeeId(String id);
}
