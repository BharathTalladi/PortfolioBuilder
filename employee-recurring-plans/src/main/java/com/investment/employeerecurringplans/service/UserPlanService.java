package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.model.*;
import java.text.ParseException;
import java.util.List;

public interface UserPlanService {

    // Method to create a recurring plan for a user
    RecurringPlanUserResponse createUserPlan(RecurringPlanUserRequest request) throws ParseException;

    // Method to create a recurring plan by an employer
    RecurringPlanEmployerResponse createUserPlanByEmployer(RecurringPlanEmployerRequest request) throws ParseException;

    // Method to get a user's plan by their ID
    RecurringPlanResponse getUserPlanById(String id) throws ParseException;

    // Method to get all users' plans
    List<RecurringPlanResponse> getAllUsersPlan() throws ParseException;

    // Method to edit user contributions
    RecurringPlanUserResponse editUserContributions(RecurringPlanUserRequest request, String id) throws ParseException;

    // Method to edit employer contributions
    RecurringPlanEmployerResponse editEmployerContributions(RecurringPlanEmployerRequest request, String id);

}
