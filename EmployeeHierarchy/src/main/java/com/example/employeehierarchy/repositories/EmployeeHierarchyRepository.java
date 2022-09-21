package com.example.employeehierarchy.repositories;

import com.example.employeehierarchy.models.EmployeeHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeHierarchyRepository extends JpaRepository<EmployeeHierarchy, Integer> {
}
