package com.investment.employeerecurringplans.service;

import com.investment.employeerecurringplans.model.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.text.ParseException;
import java.util.List;

public interface UserPlanService {

    @PreAuthorize(value = "hasAuthority('USER')")
    RecurringPlanUserResponse createUserPlan(RecurringPlanUserRequest request) throws ParseException;

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    RecurringPlanEmployerResponse createUserPlanByEmployee(RecurringPlanEmployerRequest request) throws ParseException;
    @PreAuthorize(value = "hasAuthority('USER')"
            + "or authentication.principal.equals(id) ")
    RecurringPlanResponse getUserPlanById(String id) throws ParseException;

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    List<RecurringPlanResponse> getAllUsersPlan() throws ParseException;
    @PreAuthorize(value = "hasAuthority('USER')"
            + "or authentication.principal.equals(id) ")
    RecurringPlanUserResponse editUserContributions(RecurringPlanUserRequest request, String id) throws ParseException;

    @PreAuthorize(value = "hasAuthority('ADMIN')"
            + "or authentication.principal.equals(id) ")
    RecurringPlanEmployerResponse editEmployerContributions(RecurringPlanEmployerRequest request,String id);

}
