package com.example.employeehierarchy.controllers;

import com.example.employeehierarchy.dtos.BaseResponse;
import com.example.employeehierarchy.services.EmployeeHierarchyService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmployeeHierarchyController {

    private final EmployeeHierarchyService service;

    @RequestMapping(value = "/employee-hierarchy", method = RequestMethod.POST)
    public ResponseEntity<?> createEmployeeHierarchy(@RequestBody HashMap<String, String> payload) {
        String error = service.validateEmployeeHierarchy(payload);
        if (error != null && !error.isEmpty()) {
            return ResponseEntity.badRequest().body(new BaseResponse(null, error));
        }
        JSONObject employeeHierarchy = service.createEmployeeHierarchy(payload);
        return ResponseEntity.ok().body(employeeHierarchy.toMap());

    }

    @GetMapping("/employee-hierarchy")
    public ResponseEntity<?> getEmployeeHierarchy() {
        JSONObject employeeHierarchy = service.getEmployeeHierarchy();
        if (employeeHierarchy == null)
            return ResponseEntity.ok().body(new BaseResponse(null, "Please create employee hierarchy first!"));
        return ResponseEntity.ok().body(employeeHierarchy.toMap());

    }
}
