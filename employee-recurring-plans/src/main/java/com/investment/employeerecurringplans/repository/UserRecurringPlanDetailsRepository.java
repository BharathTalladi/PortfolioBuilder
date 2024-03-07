package com.investment.employeerecurringplans.repository;

import com.investment.employeerecurringplans.entity.UserRecurringPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecurringPlanDetailsRepository extends JpaRepository<UserRecurringPlanDetails, String> {

    // Method to find a UserRecurringPlanDetails entity by its userId
    // Spring Data JPA automatically generates the query based on the method name
    Optional<UserRecurringPlanDetails> findByUserId(String id);
}
