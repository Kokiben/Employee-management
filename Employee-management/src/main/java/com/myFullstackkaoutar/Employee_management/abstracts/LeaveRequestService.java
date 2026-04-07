package com.myFullstackkaoutar.Employee_management.abstracts;

import com.myFullstackkaoutar.Employee_management.dtos.LeaveRequestCreate;
import com.myFullstackkaoutar.Employee_management.entities.LeaveRequest;

import java.util.List;
import java.util.UUID;


public interface LeaveRequestService {
    LeaveRequest createOne(LeaveRequestCreate LeaveRequestCreate, UUID employeeId);

    List<LeaveRequest> findAllByEmployeeId(UUID employeeId);
}
