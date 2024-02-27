package com.investment.employeerecurringplans.repository;

import com.investment.employeerecurringplans.entity.UserRecurringPlanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecurringPlanDetailsRepository extends JpaRepository<UserRecurringPlanDetails, String> {
    Optional<UserRecurringPlanDetails> findByUserId(String id);
}
