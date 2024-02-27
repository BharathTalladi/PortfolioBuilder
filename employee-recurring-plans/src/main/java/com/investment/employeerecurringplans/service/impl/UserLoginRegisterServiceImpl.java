package com.investment.employeerecurringplans.service.impl;

import com.investment.employeerecurringplans.entity.Role;
import com.investment.employeerecurringplans.entity.User;
import com.investment.employeerecurringplans.model.LoginResponse;
import com.investment.employeerecurringplans.model.LoginRequest;
import com.investment.employeerecurringplans.model.RegistrationRequest;
import com.investment.employeerecurringplans.model.RegistrationResponse;
import com.investment.employeerecurringplans.repository.UserRepository;
import com.investment.employeerecurringplans.service.JWTService;
import com.investment.employeerecurringplans.service.UserLoginRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserLoginRegisterServiceImpl implements UserLoginRegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    // Method to generate a random 6-digit ID
    private String generateRandom6DigitId() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest){
        Optional<User> existingUser = userRepository.findByEmailId(registrationRequest.getEmailId());
        if(existingUser.isPresent()){
            return new RegistrationResponse(existingUser.get().getId(),"User already exists");
        }
        User registeringUser=new User();
        registeringUser.setId(generateRandom6DigitId());
        registeringUser.setName(registrationRequest.getName());
        registeringUser.setEmailId(registrationRequest.getEmailId());
        registeringUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        registeringUser.setRoles(registrationRequest.getRoles() != null ? registrationRequest.getRoles() : Role.USER);
        userRepository.save(registeringUser);
        return new RegistrationResponse(registeringUser.getId(), "User registration successful");
    }


    public LoginResponse authenticate(LoginRequest request) {
        if (request.getEmailId() == null || request.getId() == null) {
            return new LoginResponse("User login failed", "Both email ID and ID are required");
        }
        /*try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmailId(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Authentication failed, return an error response
            return new LoginResponse("User login failed", "Invalid credentials");
        }*/
        Optional<User> optionalUser = userRepository.findById(request.getId());
        if (optionalUser.isEmpty()) {
            return new LoginResponse("User login failed","Cannot provide token");
        }
        User user = optionalUser.get();
        String jwtToken = jwtService.generateToken(user.getId());
        //String refreshJwtToken=jwtService.generateRefreshToken(new HashMap<>(),user);
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setMessage("User login successful");
        loginResponse.setToken(jwtToken);
        //loginResponse.setRefreshToken(refreshJwtToken);
        return loginResponse;

    }
}
