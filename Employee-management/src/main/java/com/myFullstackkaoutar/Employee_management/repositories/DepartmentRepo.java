package com.myFullstackkaoutar.Employee_management.repositories;

import com.myFullstackkaoutar.Employee_management.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, UUID> {
}
