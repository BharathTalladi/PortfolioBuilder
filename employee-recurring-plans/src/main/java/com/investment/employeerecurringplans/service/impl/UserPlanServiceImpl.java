package com.investment.employeerecurringplans.service.impl;


import com.investment.employeerecurringplans.constants.RecurringPlanConstants;
import com.investment.employeerecurringplans.entity.EmployerContributionsToUser;
import com.investment.employeerecurringplans.entity.UserRecurringPlanDetails;
import com.investment.employeerecurringplans.exceptions.RecurringPlanContributionException;
import com.investment.employeerecurringplans.exceptions.UserPlanDetailsNotFoundException;
import com.investment.employeerecurringplans.model.*;
import com.investment.employeerecurringplans.repository.EmployerContributionsToUserRepository;
import com.investment.employeerecurringplans.repository.UserRecurringPlanDetailsRepository;
import com.investment.employeerecurringplans.service.UserPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserPlanServiceImpl implements UserPlanService {

    private final UserRecurringPlanDetailsRepository userRecurringPlanDetailsRepository ;
    private final EmployerContributionsToUserRepository employerContributionsToUserRepository;
    public RecurringPlanUserResponse createUserPlan(RecurringPlanUserRequest request) throws ParseException {
        UserRecurringPlanDetails userRecurringPlanDetails=new UserRecurringPlanDetails();
        userRecurringPlanDetails.setUserId(request.getId());
        userRecurringPlanDetails.setDob(request.getDob());
        userRecurringPlanDetails.setSalary(request.getSalary());
        // Calculate contribution limits
        SelfContributionAmount selfContributionAmount = validateAndCalculateSelfContributionLimits(request, userRecurringPlanDetails);
        // Calculate salaryAfterContributions
        double salaryAfterContributions = calculateSalaryAfterContributions(request.getSalary(), selfContributionAmount);
        userRecurringPlanDetailsRepository.save(userRecurringPlanDetails);
        RecurringPlanUserResponse response=new RecurringPlanUserResponse();
        response.setAge(calculateAge(request.getDob()));
        response.setSalaryAfterContributions(salaryAfterContributions);
        response.setSelfContributionAmount(selfContributionAmount);
        return response;
    }
    private SelfContributionAmount validateAndCalculateSelfContributionLimits(RecurringPlanUserRequest request, UserRecurringPlanDetails details) throws ParseException {
        SelfContributionAmount selfContributionAmount=new SelfContributionAmount();
        // Calculate 401K contribution limit
        double _401k = request.getSelf_contribution_limit_401K();
        if (_401k > 6) {
            throw new RecurringPlanContributionException("You cannot contribute more than 6% of your salary to 401K Plan");
        }
        double _401kContribution = _401k * request.getSalary() / 100;
        selfContributionAmount.setSelf_contribution_amount_401K(_401kContribution);
        details.setSelf_contribution_limit_401K(_401kContribution);
        // Calculate HSA contribution limit
        double hsa = request.getSelf_contribution_limit_HSA();
        int age = calculateAge(request.getDob());
        double hsaLimit = (age > 55) ? 5150 : 4150;
        if (hsa > hsaLimit) {
            throw new RecurringPlanContributionException("As your age is " + (age > 55 ? "greater" : "less") + " than 55, you cannot contribute more than $ " + hsaLimit + " to your HSA Account, Please contribute less than $" + hsaLimit);
        }
        selfContributionAmount.setSelf_contribution_amount_HSA(hsa);
        details.setSelf_contribution_limit_HSA(hsa);
        // Calculate FSA contribution limit
        double fsa = request.getSelf_contribution_limit_FSA();
        if (fsa > 3200) {
            throw new RecurringPlanContributionException("You cannot contribute more than $3200 to your FSA Account, Please contribute less than $3200");
        }
        selfContributionAmount.setSelf_contribution_amount_FSA(fsa);
        details.setSelf_contribution_limit_FSA(fsa);
        // Calculate ROTH IRA Contribution limit
        double rothIra = request.getSelf_contribution_limit_ROTHIRA();
        double rothIraLimit = (age>50)? 8000 : 7000;
        if(rothIra>rothIraLimit){
            throw new RecurringPlanContributionException("As your age is " + (age > 50 ? "greater" : "less") + " than 50, you cannot contribute more than $ " + rothIraLimit + " to your RothIRA Account, Please contribute less than $" + rothIraLimit);
        }
        selfContributionAmount.setSelf_contribution_amount_ROTHIRA(rothIra);
        details.setSelf_contribution_limit_ROTHIRA(rothIra);
        return selfContributionAmount;
    }
    private double calculateSalaryAfterContributions(double salary, SelfContributionAmount selfContributionAmount) {
        double totalSelfContributionAmount = selfContributionAmount.getSelf_contribution_amount_401K() +
                selfContributionAmount.getSelf_contribution_amount_HSA() +
                selfContributionAmount.getSelf_contribution_amount_FSA() +
                selfContributionAmount.getSelf_contribution_amount_ROTHIRA();
        return salary - totalSelfContributionAmount;
    }
    public RecurringPlanEmployerResponse createUserPlanByEmployer(RecurringPlanEmployerRequest request) {
        // Check if user exists in the database
        Optional<UserRecurringPlanDetails> userRecurringPlanDetails = userRecurringPlanDetailsRepository.findByUserId(request.getId());
        if (userRecurringPlanDetails.isEmpty()) {
            throw new UsernameNotFoundException("User with ID " + request.getId() + " not found");
        }
        // Retrieve user's recurring plan details
        RecurringPlanUserRequest recurringPlanUserRequest = getRecurringPlanUserRequest(userRecurringPlanDetails);
        // Validate and calculate employer contributions
        EmployerContributionAmount employerContributionAmount=validateAndCalculateEmployerContributions(request, recurringPlanUserRequest);
        // Create response object
        RecurringPlanEmployerResponse response = new RecurringPlanEmployerResponse();
        response.setEmployerContributionAmount(employerContributionAmount);
        return response;
    }
    private static RecurringPlanUserRequest getRecurringPlanUserRequest(Optional<UserRecurringPlanDetails> userRecurringPlanDetails) {
        RecurringPlanUserRequest recurringPlanUserRequest = new RecurringPlanUserRequest();
        userRecurringPlanDetails.ifPresent(details -> {
            recurringPlanUserRequest.setId(details.getUserId());
            recurringPlanUserRequest.setDob(details.getDob());
            recurringPlanUserRequest.setSalary(details.getSalary());
            recurringPlanUserRequest.setSelf_contribution_limit_401K(details.getSelf_contribution_limit_401K());
        });
        return recurringPlanUserRequest;
    }
    private EmployerContributionAmount validateAndCalculateEmployerContributions(RecurringPlanEmployerRequest employerRequest, RecurringPlanUserRequest userRequest) {
        EmployerContributionAmount employerContributionAmount=new EmployerContributionAmount();
        EmployerContributionsToUser employerContributionsToUser = new EmployerContributionsToUser();
        employerContributionsToUser.setEmployeeId(employerRequest.getId());
        double user401kContribution = userRequest.getSelf_contribution_limit_401K();
        double employer401kContribution = employerRequest.getEmployer_contribution_limit_401k() ;
        if (employer401kContribution != user401kContribution) {
            throw new RecurringPlanContributionException("Employer should match 401k with user 401k Plan");
        }
        employerContributionsToUser.setEmployer_contribution_limit_401K(employer401kContribution);
        employerContributionAmount.setEmployer_contribution_amount_401k(employer401kContribution);
        double employerContributionToUserFSA = employerRequest.getEmployer_contribution_limit_FSA();
        if (employerContributionToUserFSA > 3200) {
            throw new RecurringPlanContributionException("Employer cannot contribute more than $3200 to User FSA Account");
        }
        employerContributionsToUser.setEmployer_contribution_limit_FSA(employerContributionToUserFSA);
        double employerContributionToUserHSA = employerRequest.getEmployer_contribution_limit_HSA();
        if (employerContributionToUserHSA > 2100) {
            throw new RecurringPlanContributionException("Employer cannot contribute more than $2100 to User HSA Account");
        }
        employerContributionsToUser.setEmployer_contribution_limit_HSA(employerContributionToUserHSA);
        employerContributionsToUser.setEmployer_contribution_limit_ROTHIRA(employerRequest.getEmployer_contribution_limit_ROTHIRA());
        employerContributionsToUserRepository.save(employerContributionsToUser);
        employerContributionAmount.setMessage(RecurringPlanConstants.SUCCESS_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT);
        employerContributionAmount.setEmployer_contribution_amount_ROTHIRA(employerRequest.getEmployer_contribution_limit_ROTHIRA());
        employerContributionAmount.setEmployer_contribution_amount_FSA(employerContributionToUserFSA);
        employerContributionAmount.setEmployer_contribution_amount_HSA(employerContributionToUserHSA);
        return employerContributionAmount;
    }
    public RecurringPlanResponse getUserPlanById(String id) throws ParseException{
        RecurringPlanResponse response=new RecurringPlanResponse();
        Optional<UserRecurringPlanDetails> userRecurringPlanDetailsOptional = userRecurringPlanDetailsRepository.findByUserId(id);
        Optional<EmployerContributionsToUser> employerContributionsToUserOptional = employerContributionsToUserRepository.findByEmployeeId(id);
        // Check if both entities exist
        if (userRecurringPlanDetailsOptional.isPresent()) {
            UserRecurringPlanDetails userRecurringPlanDetails = userRecurringPlanDetailsOptional.get();
            response.setSalary(userRecurringPlanDetails.getSalary());
            response.setId(userRecurringPlanDetails.getUserId());
            response.setAge(calculateAge(userRecurringPlanDetails.getDob()));
            // Calculate self contribution amount
            SelfContributionAmount selfContributionAmount = getSelfContributionAmount(userRecurringPlanDetails);
            if(employerContributionsToUserOptional.isPresent()){
                EmployerContributionsToUser employerContributionsToUser = employerContributionsToUserOptional.get();
                // Calculate employer contribution amount
                EmployerContributionAmount employerContributionAmount = getEmployerContributionAmount(employerContributionsToUser);
                // Calculate total contribution amount
                TotalContributionAmount totalContributionAmount = calculateTotalRecurringPlanContributions(selfContributionAmount, employerContributionAmount);
                response.setSelfContributionAmount(selfContributionAmount);
                response.setEmployerContributionAmount(employerContributionAmount);
                response.setTotalContributionAmount(totalContributionAmount);
            }
            else {
                EmployerContributionAmount employerContributionAmount = new EmployerContributionAmount();
                employerContributionAmount.setMessage(RecurringPlanConstants.ERROR_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT);
                TotalContributionAmount totalContributionAmount = new TotalContributionAmount();
                totalContributionAmount.setMessage(RecurringPlanConstants.ERROR_MESSAGE_TOTAL_CONTRIBUTION_AMOUNT);
                response.setEmployerContributionAmount(employerContributionAmount);
                response.setTotalContributionAmount(totalContributionAmount);
            }
            // Calculate salary after contributions
            double salaryAfterContributions = calculateSalaryAfterContributions(userRecurringPlanDetails.getSalary(), selfContributionAmount);
            response.setSalaryAfterContributions(salaryAfterContributions);
            response.setSelfContributionAmount(selfContributionAmount);
        } else {
            throw new UserPlanDetailsNotFoundException("User Plan Details not Found: " +id);
        }
        return response;
    }
    public List<RecurringPlanResponse> getAllUsersPlan() throws ParseException{
        List<RecurringPlanResponse> responseList = new ArrayList<>();
        List<UserRecurringPlanDetails> userDetailsList = userRecurringPlanDetailsRepository.findAll();
        List<EmployerContributionsToUser> employerContributionsList = employerContributionsToUserRepository.findAll();
        for(UserRecurringPlanDetails userDetails:userDetailsList){
            RecurringPlanResponse response = new RecurringPlanResponse();
            response.setId(userDetails.getUserId());
            response.setSalary(userDetails.getSalary());
            response.setAge(calculateAge(userDetails.getDob()));
            response.setSelfContributionAmount(getSelfContributionAmount(userDetails));
            TotalContributionAmount totalContributionAmount = new TotalContributionAmount();
            EmployerContributionAmount employerContributionAmount = new EmployerContributionAmount();
            // Find corresponding employer contribution for this user
            Optional<EmployerContributionsToUser> employerContributionOptional = employerContributionsList.stream()
                    .filter(employerContribution -> employerContribution.getEmployeeId().equals(userDetails.getUserId()))
                    .findFirst();
            if (employerContributionOptional.isPresent()) {
                EmployerContributionsToUser employerContribution = employerContributionOptional.get();
                response.setEmployerContributionAmount(getEmployerContributionAmount(employerContribution));
                response.setTotalContributionAmount(calculateTotalRecurringPlanContributions(response.getSelfContributionAmount(), response.getEmployerContributionAmount()));
            } else {
                // If employer contribution is not found, set a message indicating that employer hasn't contributed
                employerContributionAmount.setMessage(RecurringPlanConstants.ERROR_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT);
                totalContributionAmount.setMessage(RecurringPlanConstants.ERROR_MESSAGE_TOTAL_CONTRIBUTION_AMOUNT);
                response.setEmployerContributionAmount(employerContributionAmount);
                response.setTotalContributionAmount(totalContributionAmount);
            }
            // Calculate salary after contributions
            double salaryAfterContributions = calculateSalaryAfterContributions(userDetails.getSalary(), response.getSelfContributionAmount());
            response.setSalaryAfterContributions(salaryAfterContributions);
            responseList.add(response);
        }
        return responseList;
    }
    public RecurringPlanUserResponse editUserContributions(RecurringPlanUserRequest request,String id) throws ParseException {
        // Retrieve the existing user contributions based on the provided user ID
        Optional<UserRecurringPlanDetails> userRecurringPlanDetailsOptional = userRecurringPlanDetailsRepository.findByUserId(id);

        if (userRecurringPlanDetailsOptional.isEmpty()) {
            throw new RecurringPlanContributionException("User not found for ID: " + id);
        }
        UserRecurringPlanDetails userRecurringPlanDetails = getUserRecurringPlanDetails(userRecurringPlanDetailsOptional, Optional.ofNullable(request));
        // Save the updated user contributions
        userRecurringPlanDetailsRepository.save(userRecurringPlanDetails);
        // Calculate and set the response
        RecurringPlanUserResponse response=new RecurringPlanUserResponse();
        int age=calculateAge(userRecurringPlanDetails.getDob());
        response.setAge(age);
        response.setSalaryAfterContributions(calculateSalaryAfterContributions(userRecurringPlanDetails.getSalary(),getSelfContributionAmount(userRecurringPlanDetails)));
        response.setSelfContributionAmount(getSelfContributionAmount(userRecurringPlanDetails));
        return response;
    }
    private static UserRecurringPlanDetails getUserRecurringPlanDetails(Optional<UserRecurringPlanDetails> userRecurringPlanDetailsOptional,Optional<RecurringPlanUserRequest> request) throws ParseException {
        if (userRecurringPlanDetailsOptional.isPresent() && request.isPresent()) {
            UserRecurringPlanDetails userRecurringPlanDetails = userRecurringPlanDetailsOptional.get();
            int age=calculateAge(userRecurringPlanDetails.getDob());
            RecurringPlanUserRequest userRequest = request.get();
            if (userRequest.getSalary() != null) {
                userRecurringPlanDetails.setSalary(userRequest.getSalary());
            }
            if (userRequest.getSelf_contribution_limit_401K() != null) {
                double contribution401K = userRequest.getSelf_contribution_limit_401K();
                if (contribution401K > 6.0) {
                    throw new RecurringPlanContributionException("User cannot contribute more than 6% of salary to their 401k account");
                }
                userRecurringPlanDetails.setSelf_contribution_limit_401K(userRecurringPlanDetails.getSalary()*contribution401K/100);
            }
            if (userRequest.getSelf_contribution_limit_HSA() != null) {
                double contributionHSA = userRequest.getSelf_contribution_limit_HSA();
                if (age > 55 && contributionHSA > 5500) {
                    throw new RecurringPlanContributionException("As your age is greater than 55, You cannot contribute more than $5500 to your HSA Account");
                } else if (age < 55 && contributionHSA > 4500) {
                    throw new RecurringPlanContributionException("As your age is less than 55, You cannot contribute more than $4500 to your HSA Account");
                }
                userRecurringPlanDetails.setSelf_contribution_limit_HSA(contributionHSA);
            }
            if (userRequest.getSelf_contribution_limit_FSA() != null) {
                double contributionFSA = userRequest.getSelf_contribution_limit_FSA();
                if (contributionFSA > 3200) {
                    throw new RecurringPlanContributionException("You cannot contribute more than $3200 to your FSA Account");
                }
                userRecurringPlanDetails.setSelf_contribution_limit_FSA(contributionFSA);
            }
            if (userRequest.getSelf_contribution_limit_ROTHIRA() != null) {
                double contributionROTHIRA = userRequest.getSelf_contribution_limit_ROTHIRA();
                if (age < 50 && contributionROTHIRA > 7000) {
                    throw new RecurringPlanContributionException("As your age is less than 50, You cannot contribute more than $7000 to your ROTH IRA Account");
                } else if (age > 50 && contributionROTHIRA > 8000) {
                    throw new RecurringPlanContributionException("As your age is greater than 50, You cannot contribute more than $8000 to your ROTH IRA Account");
                }
                userRecurringPlanDetails.setSelf_contribution_limit_ROTHIRA(contributionROTHIRA);
            }
            return userRecurringPlanDetails;
        } else {
            // Handle the case where the Optional is empty
            throw new NoSuchElementException("UserRecurringPlanDetails not found");
        }
    }
    public RecurringPlanEmployerResponse editEmployerContributions(RecurringPlanEmployerRequest request,String id) {
        // Check if user employer in the database
        Optional<EmployerContributionsToUser> employeeId = employerContributionsToUserRepository.findByEmployeeId(id);
        Optional<UserRecurringPlanDetails> userId=userRecurringPlanDetailsRepository.findByUserId(id);
        if ( employeeId.isEmpty() && userId.isEmpty()) {
            throw new UsernameNotFoundException("User with ID " + id + " not found");
        }
        // Retrieve user's recurring plan details
        UserRecurringPlanDetails userRecurringPlanDetails = userId.orElseThrow(() -> new NoSuchElementException("UserRecurringPlanDetails not found"));

        EmployerContributionsToUser employerContributions = getEmployerContributionsToUser(employeeId, Optional.of(request), userRecurringPlanDetails);
        employerContributionsToUserRepository.save(employerContributions);
        RecurringPlanEmployerResponse response=new RecurringPlanEmployerResponse();
        response.setEmployerContributionAmount(getEmployerContributionAmount(employerContributions));
        return response;
    }
    private static EmployerContributionsToUser getEmployerContributionsToUser(Optional<EmployerContributionsToUser> employerContributionsOptional,
                                                                              Optional<RecurringPlanEmployerRequest> request,
                                                                              UserRecurringPlanDetails userRecurringPlanDetails) {
        if (employerContributionsOptional.isPresent() && request.isPresent()) {
            EmployerContributionsToUser employerContributionsToUser=employerContributionsOptional.get();
            RecurringPlanEmployerRequest employerRequest=request.get();
            // Calculate employer's 401k contribution based on user's salary and contribution percentage
            if (employerRequest.getEmployer_contribution_limit_401k() != null) {
                if (!employerRequest.getEmployer_contribution_limit_401k().equals(userRecurringPlanDetails.getSelf_contribution_limit_401K())) {
                    throw new RecurringPlanContributionException("Employer should match employee 401k Plan");
                }
                employerContributionsToUser.setEmployer_contribution_limit_401K(employerRequest.getEmployer_contribution_limit_401k() );
            }
            if (employerRequest.getEmployer_contribution_limit_HSA() != null) {
                double limitHSA = employerRequest.getEmployer_contribution_limit_HSA();
                if (limitHSA > 2100) {
                    throw new RecurringPlanContributionException("Employer cannot contribute more than $2100 to employee HSA Account");
                }
                employerContributionsToUser.setEmployer_contribution_limit_HSA(limitHSA);
            }
            if (employerRequest.getEmployer_contribution_limit_FSA() != null) {
                double limitFSA = employerRequest.getEmployer_contribution_limit_FSA();
                if (limitFSA > 3200) {
                    throw new RecurringPlanContributionException("Employer cannot contribute more than $3200 to employee FSA Account");
                }
                employerContributionsToUser.setEmployer_contribution_limit_FSA(limitFSA);
            }
            if(employerRequest.getEmployer_contribution_limit_ROTHIRA() != null){
                employerContributionsToUser.setEmployer_contribution_limit_ROTHIRA(employerRequest.getEmployer_contribution_limit_ROTHIRA());
            }
            return employerContributionsToUser;
        } else {
            // Handle the case where the Optional is empty
            throw new NoSuchElementException("EmployerContributionsToUser not found");
        }
    }
    private static EmployerContributionAmount getEmployerContributionAmount(EmployerContributionsToUser employerContributionsToUser) {
        EmployerContributionAmount employerContributionAmount = new EmployerContributionAmount();
        employerContributionAmount.setMessage(RecurringPlanConstants.SUCCESS_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT);
        employerContributionAmount.setEmployer_contribution_amount_401k(employerContributionsToUser.getEmployer_contribution_limit_401K());
        employerContributionAmount.setEmployer_contribution_amount_HSA(employerContributionsToUser.getEmployer_contribution_limit_HSA());
        employerContributionAmount.setEmployer_contribution_amount_FSA(employerContributionsToUser.getEmployer_contribution_limit_FSA());
        employerContributionAmount.setEmployer_contribution_amount_ROTHIRA(employerContributionsToUser.getEmployer_contribution_limit_ROTHIRA());
        return employerContributionAmount;
    }
    private static SelfContributionAmount getSelfContributionAmount(UserRecurringPlanDetails userRecurringPlanDetails) {
        SelfContributionAmount selfContributionAmount = new SelfContributionAmount();
        selfContributionAmount.setSelf_contribution_amount_401K(userRecurringPlanDetails.getSelf_contribution_limit_401K());
        selfContributionAmount.setSelf_contribution_amount_HSA(userRecurringPlanDetails.getSelf_contribution_limit_HSA());
        selfContributionAmount.setSelf_contribution_amount_FSA(userRecurringPlanDetails.getSelf_contribution_limit_FSA());
        selfContributionAmount.setSelf_contribution_amount_ROTHIRA(userRecurringPlanDetails.getSelf_contribution_limit_ROTHIRA());
        return selfContributionAmount;
    }
    private TotalContributionAmount calculateTotalRecurringPlanContributions(SelfContributionAmount selfContributionAmount, EmployerContributionAmount employerContributionAmount){
        TotalContributionAmount totalContributionAmount=new TotalContributionAmount();
        totalContributionAmount.setTotal_contribution_amount_401k(selfContributionAmount.getSelf_contribution_amount_401K()+employerContributionAmount.getEmployer_contribution_amount_401k());
        totalContributionAmount.setMessage(RecurringPlanConstants.SUCCESS_MESSAGE_TOTAL_CONTRIBUTION_AMOUNT);
        double selfFSAContributionAmount=selfContributionAmount.getSelf_contribution_amount_FSA();
        double employerFSAContributionAmount=employerContributionAmount.getEmployer_contribution_amount_FSA();
        //when both user and employer contribution is equal($1500, $1,500 (match) //$3,000 )
        if(selfFSAContributionAmount==employerFSAContributionAmount){
            totalContributionAmount.setTotal_contribution_amount_FSA(selfFSAContributionAmount+employerFSAContributionAmount);
        }
        //when user doesn't contribute and employer contributes(user : 0+ employer 500=500)
        else if(selfFSAContributionAmount == 0 && employerFSAContributionAmount<1000){
            totalContributionAmount.setTotal_contribution_amount_FSA(employerFSAContributionAmount);
        }
        else if(selfFSAContributionAmount == 0 && employerFSAContributionAmount>1000){
            totalContributionAmount.setTotal_contribution_amount_FSA(1000.00);
        }
        //when user is < employer (1000+1500(not a match only 1000 applies)=2000)
        else if(selfFSAContributionAmount<employerFSAContributionAmount && employerFSAContributionAmount>1000){
            totalContributionAmount.setTotal_contribution_amount_FSA(selfFSAContributionAmount*2);
        }
        //when user is < employer (300+500(not a match but acceptable)=800)
        else if(selfFSAContributionAmount<employerFSAContributionAmount && employerFSAContributionAmount<1000){
            totalContributionAmount.setTotal_contribution_amount_FSA(selfFSAContributionAmount+employerFSAContributionAmount);
        }
        //when user is > employee (1000+500=1500)
        else if (selfFSAContributionAmount>employerFSAContributionAmount){
            totalContributionAmount.setTotal_contribution_amount_FSA(selfFSAContributionAmount+employerFSAContributionAmount);
        }
        totalContributionAmount.setTotal_contribution_amount_HSA(selfContributionAmount.getSelf_contribution_amount_HSA()+employerContributionAmount.getEmployer_contribution_amount_HSA());
        totalContributionAmount.setTotal_contribution_amount_ROTHIRA(selfContributionAmount.getSelf_contribution_amount_ROTHIRA()+employerContributionAmount.getEmployer_contribution_amount_ROTHIRA());
        return totalContributionAmount;

    }
    public static int calculateAge(Date dob) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String dateOfBirthString = format.format(dob); // Convert Date to String in desired format
        Date parsedDob = format.parse(dateOfBirthString);
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(parsedDob);
        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        // adjust for birthday not yet passed in current year
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

}
