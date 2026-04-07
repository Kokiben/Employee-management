package com.myFullstackkaoutar.Employee_management.controllers;

import com.myFullstackkaoutar.Employee_management.abstracts.EmployeeService;
import com.myFullstackkaoutar.Employee_management.abstracts.LeaveRequestService;
import com.myFullstackkaoutar.Employee_management.dtos.EmployeeCreate;
import com.myFullstackkaoutar.Employee_management.dtos.EmployeeUpdate;
import com.myFullstackkaoutar.Employee_management.dtos.LeaveRequestCreate;
import com.myFullstackkaoutar.Employee_management.dtos.PaginatedResponse;
import com.myFullstackkaoutar.Employee_management.entities.Employee;
import com.myFullstackkaoutar.Employee_management.entities.LeaveRequest;
import com.myFullstackkaoutar.Employee_management.shared.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<GlobalResponse<PaginatedResponse<Employee>>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            HttpServletRequest request
    ) {
        int zeroBasedPage = page - 1;

        Page<Employee> employees = employeeService.findAll(zeroBasedPage, size);
        String baseUrl = request.getRequestURL().toString();
        String nextUrl = employees.hasNext() ? String.format("%s?page=%d&siz=%d", baseUrl, page + 1, size) : null;
        String prevUrl = employees.hasPrevious() ? String.format("%s?page=%d&siz=%d", baseUrl, page - 1, size) : null;
        PaginatedResponse<Employee> paginatedResponse = new PaginatedResponse<Employee>(
                employees.getContent(),
                employees.getNumber(),
                employees.getTotalPages(),
                employees.getTotalElements(),
                employees.hasNext(),
                employees.hasPrevious(),
                nextUrl,
                prevUrl
        );
        return new ResponseEntity<>(new GlobalResponse<>(paginatedResponse), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> findOne(@PathVariable UUID employeeId) {
        Employee employee = employeeService.findOne(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(employee), HttpStatus.OK);
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteOne(@PathVariable UUID employeeId) {
        employeeService.deleteOne(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{employeeId}")
    public ResponseEntity<GlobalResponse<Employee>> updateOne(@PathVariable UUID employeeId, @RequestBody @Valid EmployeeUpdate employee) {
        Employee updateemployee = employeeService.updateOne(employeeId, employee);
        return new ResponseEntity<>(new GlobalResponse<>(updateemployee), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<GlobalResponse<Employee>> CreateOne(@RequestBody @Valid EmployeeCreate employee) {
        Employee newemployee = employeeService.CreateOne(employee);
        return new ResponseEntity<>(new GlobalResponse<>(newemployee), HttpStatus.CREATED);
    }

    @PostMapping("/{employeeId}/leave-requests")
    public ResponseEntity<GlobalResponse<LeaveRequest>> leaveRequest(@RequestBody @Valid LeaveRequestCreate leaveRequest,
                                                                     @PathVariable UUID employeeId) {
        LeaveRequest newLeaveRequest = leaveRequestService.createOne(leaveRequest, employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(newLeaveRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/leave-requests")
    public ResponseEntity<GlobalResponse<List<LeaveRequest>>> leaveRequestByEmployeeId(
            @PathVariable UUID employeeId
    ) {
        List<LeaveRequest> leaveRequests = leaveRequestService.findAllByEmployeeId(employeeId);
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequests), HttpStatus.OK);
    }
}
