package com.myFullstackkaoutar.Employee_management.controllers;


import com.myFullstackkaoutar.Employee_management.abstracts.DepartmentService;
import com.myFullstackkaoutar.Employee_management.dtos.DepartmentCreate;
import com.myFullstackkaoutar.Employee_management.entities.Department;
import com.myFullstackkaoutar.Employee_management.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Department>>> findAll() {
        List<Department> department = departmentService.findAll();
        return new ResponseEntity<>(new GlobalResponse<>(department), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse<Department>> findOne(@PathVariable UUID departmentId) {
        Department department = departmentService.findOne(departmentId);
        return new ResponseEntity<>(new GlobalResponse<>(department), HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<GlobalResponse<Department>> delete(@PathVariable UUID departmentId) {
        departmentService.delete(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Department>> createOne(@RequestBody @Valid DepartmentCreate departmentCreate) {
        Department newdepartment = departmentService.createOne(departmentCreate);
        return new ResponseEntity<>(new GlobalResponse<>(newdepartment), HttpStatus.CREATED);
    }
}
