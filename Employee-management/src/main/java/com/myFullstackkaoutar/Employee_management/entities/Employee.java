package com.myFullstackkaoutar.Employee_management.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "First_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "Last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phone;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean isVerified;

    @Column(name = "account_creation_token")
    private String accountCreationToken;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_Id", nullable = false)
    @JsonProperty("departmentId")
    private Department department;

    public UUID getDepartment() {
        return department.getId();
    }

}
