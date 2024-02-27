package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.model.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.text.ParseException;
import java.util.List;

public interface UserPlanService {

    RecurringPlanUserResponse createUserPlan(RecurringPlanUserRequest request) throws ParseException;
    RecurringPlanEmployerResponse createUserPlanByEmployee(RecurringPlanEmployerRequest request) throws ParseException;
    RecurringPlanResponse getUserPlanById(String id) throws ParseException;
    List<RecurringPlanResponse> getAllUsersPlan() throws ParseException;
    RecurringPlanUserResponse editUserContributions(RecurringPlanUserRequest request, String id) throws ParseException;
    RecurringPlanEmployerResponse editEmployerContributions(RecurringPlanEmployerRequest request,String id);

}
