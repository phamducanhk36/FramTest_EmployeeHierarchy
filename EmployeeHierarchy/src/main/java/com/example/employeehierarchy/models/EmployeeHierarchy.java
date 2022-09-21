package com.example.employeehierarchy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHierarchy {
    @Id
    private String employee;

    private String supervisor;
}
