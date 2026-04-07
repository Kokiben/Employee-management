package com.myFullstackkaoutar.Employee_management.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCreate(

        @NotNull(message = "First name not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String firstName,
        @NotNull(message = "last name not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String lastName,
        @NotNull(message = "email not required")
        @Email(message = "Invalid email format")
        String email,
        @NotNull(message = "phone not required")
        @Pattern(regexp = "^(\\+212|0)[0-9]\\d{8}$", message = "Please enter a valid Moroccan phone number.")
        String phone,
        @NotNull(message = "hireDate not required")
        @PastOrPresent(message = "Hire date cannot be in the future")
        LocalDate hireDate,
        @NotNull(message = "position not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String position,

        @NotNull(message = "department Id not required")
        UUID departmentId

) {
}
