package com.investment.employeerecurringplans.controller;

import com.investment.employeerecurringplans.model.*;
import com.investment.employeerecurringplans.service.UserPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/contributions")
@RequiredArgsConstructor
public class UserPlansController {

    private final UserPlanService userPlanService;
    // Endpoint for users to create a recurring plan
    @PostMapping("/createUserPlan")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RecurringPlanUserResponse> createPlanByUser(@RequestBody RecurringPlanUserRequest request,Authentication authentication ) throws ParseException {
        // Check if the user ID matches the authenticated user
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(request.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return forbidden status if user ID does not match
        }
        return ResponseEntity.ok(userPlanService.createUserPlan(request)); // Otherwise, create the user plan
    }

    // Endpoint for employers to create a user's recurring plan
    @PostMapping("/createUserPlanByEmployer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RecurringPlanEmployerResponse> createUserPlanByEmployer(@RequestBody RecurringPlanEmployerRequest request) throws ParseException{
        return ResponseEntity.ok(userPlanService.createUserPlanByEmployer(request)); // Create user plan by employer
    }

    // Endpoint to get a user's plan by ID
    @GetMapping("/getUserPlanById/{id}")
    @PreAuthorize("hasAuthority('USER') or authentication.principal.equals(#id)")
    public ResponseEntity<RecurringPlanResponse> getUserPlanById(@PathVariable String id,Authentication authentication) throws ParseException{
        // Check if the user ID matches the authenticated user
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return forbidden status if user ID does not match
        }
        return ResponseEntity.ok(userPlanService.getUserPlanById(id)); // Otherwise, retrieve the user plan
    }

    // Endpoint to get all users' plans (accessible only by ADMIN)
    @GetMapping("/getAllUsersPlan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<RecurringPlanResponse>> getAllUsersPlan() throws ParseException{
        return ResponseEntity.ok(userPlanService.getAllUsersPlan()); // Retrieve all users' plans
    }
    // Endpoint for users to edit their contributions
    @PatchMapping("/editUserContributions/{id}")
    @PreAuthorize("hasAuthority('USER') or authentication.principal.equals(#id)")
    public ResponseEntity<RecurringPlanUserResponse> editUserContributions(@RequestBody RecurringPlanUserRequest request, @PathVariable String id,Authentication authentication) throws ParseException{
        // Check if the user ID matches the authenticated user
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return forbidden status if user ID does not match
        }
        return ResponseEntity.ok(userPlanService.editUserContributions(request,id)); // Otherwise, edit the user's contributions
    }
    // Endpoint for admins to edit employer contributions
    @PatchMapping("/editEmployerContributions/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RecurringPlanEmployerResponse> editEmployerContributions(@RequestBody RecurringPlanEmployerRequest request,@PathVariable String id){
        return ResponseEntity.ok(userPlanService.editEmployerContributions(request,id)); // Edit employer contributions
    }
    // Helper method to extract user ID from authentication
    private String getUserIdFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
