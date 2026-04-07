package com.myFullstackkaoutar.Employee_management.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCreate(
        @NotNull(message = "name not required")
        @Size(min = 2, message = "min is 2 characters and max is 50 characters")
        String name
) {
}
