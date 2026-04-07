package com.myFullstackkaoutar.Employee_management.repositories;

import com.myFullstackkaoutar.Employee_management.entities.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByEmployeeId(UUID employeeId);
}