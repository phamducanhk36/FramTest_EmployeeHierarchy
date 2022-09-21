package com.example.employeehierarchy;

import com.example.employeehierarchy.models.EmployeeHierarchy;
import com.example.employeehierarchy.repositories.EmployeeHierarchyRepository;
import com.example.employeehierarchy.services.EmployeeHierarchyService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeHierarchyServiceTest {

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @InjectMocks
    EmployeeHierarchyService service;

    @Mock
    private EmployeeHierarchyRepository repository;

    @Test
    public void createEmployeeHierarchy_test() throws JSONException {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("A", "B");
        payload.put("B", "C");
        ArrayList<EmployeeHierarchy> mock = new ArrayList<>();
        mock.add(new EmployeeHierarchy("A", "B"));
        mock.add(new EmployeeHierarchy("B", "C"));
        mock.add(new EmployeeHierarchy("C", null));
        Mockito.when(repository.findAll()).thenReturn(mock);
        JSONObject employeeHierarchy = service.createEmployeeHierarchy(payload);
        Assertions.assertNotNull(employeeHierarchy.getJSONObject("C"));
        Assertions.assertNotNull(employeeHierarchy.getJSONObject("C").getJSONObject("B"));
    }

    @Test
    public void validateEmployeeHierarchy_test() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("A", "B");
        payload.put("B", "C");
        String error = service.validateEmployeeHierarchy(payload);
        Assertions.assertNull(error);
    }

    @Test
    public void validateEmployeeHierarchy_testLoop() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("A", "B");
        payload.put("B", "C");
        payload.put("C", "A");
        String error = service.validateEmployeeHierarchy(payload);
        Assertions.assertEquals("No root!", error);
    }

    @Test
    public void validateEmployeeHierarchy_testMultipleRoot() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("A", "B");
        payload.put("B", "E");
        payload.put("C", "D");
        String error = service.validateEmployeeHierarchy(payload);
        Assertions.assertEquals("Multiple root!", error);
    }
}
