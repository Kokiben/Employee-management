package com.myFullstackkaoutar.Employee_management.Services;

import com.myFullstackkaoutar.Employee_management.abstracts.LeaveRequestService;
import com.myFullstackkaoutar.Employee_management.dtos.LeaveRequestCreate;
import com.myFullstackkaoutar.Employee_management.entities.Employee;
import com.myFullstackkaoutar.Employee_management.entities.LeaveRequest;
import com.myFullstackkaoutar.Employee_management.repositories.EmployeeRepo;
import com.myFullstackkaoutar.Employee_management.repositories.LeaveRequestRepo;
import com.myFullstackkaoutar.Employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeaveRequestImpl implements LeaveRequestService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public LeaveRequest createOne(LeaveRequestCreate leaveRequestCreate, UUID employeeId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee With id " + employeeId + "not found"));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStatus("PENDING");
        leaveRequest.setStartDate(leaveRequestCreate.startDate());
        leaveRequest.setEndDate(leaveRequestCreate.endDate());
        leaveRequest.setReason(leaveRequestCreate.reason());
        leaveRequest.setEmployee(employee);

        leaveRequestRepo.save(leaveRequest);
        return leaveRequest;
    }

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public List<LeaveRequest> findAllByEmployeeId(java.util.UUID employeeId) {
        return leaveRequestRepo.findByEmployeeId(employeeId);
    }

}
