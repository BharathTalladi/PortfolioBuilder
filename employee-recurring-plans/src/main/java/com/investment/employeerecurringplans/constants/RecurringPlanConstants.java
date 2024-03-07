package com.investment.employeerecurringplans.constants;

// This class holds constant values used throughout the application
public final class RecurringPlanConstants {

    // Private constructor to prevent instantiation of this class
    private RecurringPlanConstants(){

    }
    // Error messages for cases where employer contribution amount is not available
    public static final String ERROR_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT ="Employer haven't contributed. Please reach out to Employer";
    // Error message for cases where total contribution amount cannot be calculated
    public static final String ERROR_MESSAGE_TOTAL_CONTRIBUTION_AMOUNT ="After Employer contributes, total Contribution Amount will be calculated. Please reach out to Employer";
    // Success message for successful calculation of employer contribution
    public static final String SUCCESS_MESSAGE_EMPLOYER_CONTRIBUTION_AMOUNT ="Employer contribution calculated successfully";
    // Success message for cases where total contribution amount is calculated successfully
    public static final String SUCCESS_MESSAGE_TOTAL_CONTRIBUTION_AMOUNT ="Total Contribution Amount is based on self and employer contributions";

}
