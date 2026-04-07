package com.myFullstackkaoutar.Employee_management.Services;

import com.myFullstackkaoutar.Employee_management.config.JwtHelper;
import com.myFullstackkaoutar.Employee_management.dtos.LoginRequest;
import com.myFullstackkaoutar.Employee_management.dtos.SignupRequest;
import com.myFullstackkaoutar.Employee_management.entities.Employee;
import com.myFullstackkaoutar.Employee_management.entities.UserAccount;
import com.myFullstackkaoutar.Employee_management.repositories.EmployeeRepo;
import com.myFullstackkaoutar.Employee_management.repositories.UserAccountRepo;
import com.myFullstackkaoutar.Employee_management.shared.CustomResponseException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepo userAccountRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    public void signup(@Valid SignupRequest signupRequest, String token) {
        UserAccount account = new UserAccount();
        Employee employee = employeeRepo.findOneByAccountCreationToken(token)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Invalid token"));
        if (employee.isVerified()) {
            throw CustomResponseException.BadRequest("Account already created");

        }


        account.setUsername(signupRequest.username());
        account.setPassword(passwordEncoder.encode(signupRequest.password()));
        account.setEmployee(employee);
        userAccountRepo.save(account);

        employee.setVerified(true);
        employee.setAccountCreationToken(null);
        employeeRepo.save(employee);
    }


    public String login(@Valid LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        UserAccount user = userAccountRepo.findOneByUsername(loginRequest.username())
                .orElseThrow(() -> CustomResponseException.BadCredentials());
        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("userId", user.getId());
        customClaims.put("role", user.getRole());
        return jwtHelper.generateToken(user);
    }
}
