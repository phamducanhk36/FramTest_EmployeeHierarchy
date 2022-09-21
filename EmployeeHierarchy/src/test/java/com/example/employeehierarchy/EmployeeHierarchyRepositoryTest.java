package com.example.employeehierarchy;

import com.example.employeehierarchy.models.EmployeeHierarchy;
import com.example.employeehierarchy.repositories.EmployeeHierarchyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeHierarchyRepositoryTest {
    @Autowired
    private EmployeeHierarchyRepository repository;

    @Test
    public void testCreate() {
        EmployeeHierarchy employeeHierarchy = new EmployeeHierarchy("Employee", "Supervisor");
        EmployeeHierarchy save = repository.save(employeeHierarchy);
        assertEquals(employeeHierarchy.getEmployee(), save.getEmployee());
        List<EmployeeHierarchy> all = repository.findAll();
        assertEquals(1, all.size());
    }

}
