package com.myFullstackkaoutar.Employee_management.Services;

import com.myFullstackkaoutar.Employee_management.abstracts.DepartmentService;
import com.myFullstackkaoutar.Employee_management.dtos.DepartmentCreate;
import com.myFullstackkaoutar.Employee_management.entities.Department;
import com.myFullstackkaoutar.Employee_management.repositories.DepartmentRepo;
import com.myFullstackkaoutar.Employee_management.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public Department findOne(UUID DepartmentId) {
        Department department = departmentRepo.findById(DepartmentId)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                        "department with id " + DepartmentId + "Not found."
                ));
        return department;


    }

    public List<Department> findAll() {
        return departmentRepo.findAll();
    }

    public void delete(UUID DepartmentId) {
        Optional<Department> department = departmentRepo.findById(DepartmentId);

        department.ifPresent(value -> departmentRepo.deleteById(value.getId()));
    }

    public Department createOne(DepartmentCreate departmentCreate) {

        Department department = new Department();

        department.setName(departmentCreate.name());

        departmentRepo.save(department);

        return department;
    }


}
