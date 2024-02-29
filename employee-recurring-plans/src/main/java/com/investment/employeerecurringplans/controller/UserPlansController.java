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

    @PostMapping("/createUserPlan")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<RecurringPlanUserResponse> createPlanByUser(@RequestBody RecurringPlanUserRequest request,Authentication authentication ) throws ParseException {
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(request.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userPlanService.createUserPlan(request));
    }
    @PostMapping("/createUserPlanByEmployer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RecurringPlanEmployerResponse> createUserPlanByEmployer(@RequestBody RecurringPlanEmployerRequest request) throws ParseException{
        return ResponseEntity.ok(userPlanService.createUserPlanByEmployer(request));
    }
    @GetMapping("/getUserPlanById/{id}")
    @PreAuthorize("hasAuthority('USER') or authentication.principal.equals(#id)")
    public ResponseEntity<RecurringPlanResponse> getUserPlanById(@PathVariable String id,Authentication authentication) throws ParseException{
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userPlanService.getUserPlanById(id));
    }
    @GetMapping("/getAllUsersPlan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<RecurringPlanResponse>> getAllUsersPlan() throws ParseException{
        return ResponseEntity.ok(userPlanService.getAllUsersPlan());
    }
    @PatchMapping("/editUserContributions/{id}")
    @PreAuthorize("hasAuthority('USER') or authentication.principal.equals(#id)")
    public ResponseEntity<RecurringPlanUserResponse> editUserContributions(@RequestBody RecurringPlanUserRequest request, @PathVariable String id,Authentication authentication) throws ParseException{
        String userId=getUserIdFromAuthentication(authentication);
        if (!userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userPlanService.editUserContributions(request,id));
    }
    @PatchMapping("/editEmployerContributions/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RecurringPlanEmployerResponse> editEmployerContributions(@RequestBody RecurringPlanEmployerRequest request,@PathVariable String id){
        return ResponseEntity.ok(userPlanService.editEmployerContributions(request,id));
    }
    // Helper method to extract user ID from authentication
    private String getUserIdFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
