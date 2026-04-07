package com.myFullstackkaoutar.Employee_management.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdate(
        @NotNull(message = "First name not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String firstName,
        @NotNull(message = "last name not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String lastName,
        @NotNull(message = "phone not required")
        @Pattern(regexp = "^(\\+212|0)[0-9]\\d{8}$", message = "Please enter a valid Moroccan phone number.")
        String phone,
        @NotNull(message = "position not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String position

) {
}
