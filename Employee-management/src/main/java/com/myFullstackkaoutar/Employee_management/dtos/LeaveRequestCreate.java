package com.myFullstackkaoutar.Employee_management.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record LeaveRequestCreate(


        @NotNull(message = " start date not required")
        @FutureOrPresent(message = "start date should be now or in the future")
        LocalDate startDate,

        @NotNull(message = " end date not required")
        @FutureOrPresent(message = "end date should be now or in the future")
        LocalDate endDate,
        @NotNull(message = "reason not required")
        @Size(min = 2, max = 100, message = "min is 2 characters and max is 100 characters")
        String reason


) {
}
