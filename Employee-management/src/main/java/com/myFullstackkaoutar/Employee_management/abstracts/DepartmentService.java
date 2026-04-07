package com.myFullstackkaoutar.Employee_management.abstracts;

import com.myFullstackkaoutar.Employee_management.dtos.DepartmentCreate;
import com.myFullstackkaoutar.Employee_management.entities.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    Department findOne(UUID DepartmentId);

    List<Department> findAll();

    void delete(UUID DepartmentId);

    Department createOne(DepartmentCreate departmentCreate);


}
