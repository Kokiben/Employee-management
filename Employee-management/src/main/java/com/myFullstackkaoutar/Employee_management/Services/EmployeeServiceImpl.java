package com.myFullstackkaoutar.Employee_management.Services;

import com.myFullstackkaoutar.Employee_management.abstracts.EmployeeService;
import com.myFullstackkaoutar.Employee_management.dtos.EmployeeCreate;
import com.myFullstackkaoutar.Employee_management.dtos.EmployeeUpdate;
import com.myFullstackkaoutar.Employee_management.entities.Department;
import com.myFullstackkaoutar.Employee_management.entities.Employee;
import com.myFullstackkaoutar.Employee_management.repositories.DepartmentRepo;
import com.myFullstackkaoutar.Employee_management.repositories.EmployeeRepo;
import com.myFullstackkaoutar.Employee_management.shared.CustomResponseException;
import com.myFullstackkaoutar.Employee_management.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private EmailService emailService;

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public Employee findOne(UUID employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee With id " + employeeId + "not found"));
        return employee;
    }


    public Page<Employee> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return employeeRepo.findAll(pageable);
    }

    public void deleteOne(UUID employeeId) {
        Optional<Employee> employee = employeeRepo.findById(employeeId);

        employee.ifPresent(value -> employeeRepo.deleteById(value.getId()));
    }

    @PreAuthorize("@securityUtils.isOwner(#employeeId)")
    public Employee updateOne(UUID employeeId, EmployeeUpdate employee) {
        Employee existingemployee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "Employee With id " + employeeId + "not found"
                ));

        existingemployee.setFirstName(employee.firstName());
        existingemployee.setLastName(employee.lastName());
        existingemployee.setPhone(employee.phone());
        existingemployee.setPosition(employee.position());
        employeeRepo.save(existingemployee);
        return existingemployee;

    }

    @Transactional
    public Employee CreateOne(EmployeeCreate employeeCreate) {
        Employee employee = new Employee();
        Department department = departmentRepo.findById(employeeCreate.departmentId())
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "department With id " + employeeCreate.departmentId() + "not found"));


        String token = UUID.randomUUID().toString();
        try {
            employee.setVerified(false);
            employee.setAccountCreationToken(token);

            employee.setFirstName(employeeCreate.firstName());
            employee.setLastName(employeeCreate.lastName());
            employee.setPhone(employeeCreate.phone());
            employee.setEmail(employeeCreate.email());
            employee.setHireDate(employeeCreate.hireDate());
            employee.setPosition(employeeCreate.position());
            employee.setDepartment(department);

            employeeRepo.save(employee);

            emailService.sendAccountCreationEmail(employee.getEmail(), token);
            return employee;
        } catch (Exception e) {
            throw CustomResponseException.BadRequest("Employee creation failed");
        }

    }
}
