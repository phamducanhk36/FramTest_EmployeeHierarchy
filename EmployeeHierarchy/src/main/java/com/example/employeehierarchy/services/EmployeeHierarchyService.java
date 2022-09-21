package com.example.employeehierarchy.services;

import com.example.employeehierarchy.models.EmployeeHierarchy;
import com.example.employeehierarchy.repositories.EmployeeHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeHierarchyService {
    private final EmployeeHierarchyRepository repository;

    @Transactional
    public JSONObject createEmployeeHierarchy(HashMap<String, String> payload) {
        EmployeeHierarchy root = new EmployeeHierarchy();
        List<EmployeeHierarchy> employeeHierarchies = payload.keySet().stream().map(p -> {
            if (!payload.containsKey(payload.get(p))) {
                root.setEmployee(payload.get(p));
            }
            return new EmployeeHierarchy(p, payload.get(p));
        }).collect(Collectors.toList());
        employeeHierarchies.add(root);
        repository.deleteAll();
        repository.saveAll(employeeHierarchies);
        return getEmployeeHierarchy();
    }

    public String validateEmployeeHierarchy(HashMap<String, String> payload) {
        List<String> root = payload.keySet().stream().filter(p -> !payload.containsKey(payload.get(p))).collect(Collectors.toList());
        if (root.size() > 1) {
            return "Multiple root!";
        }
        if (root.size() == 0) {
            return "No root!";
        }
        return null;
    }

    public JSONObject getEmployeeHierarchy() {
        List<EmployeeHierarchy> all = repository.findAll();
        Optional<EmployeeHierarchy> rootOptional = all.stream().filter(e -> e.getSupervisor() == null).findFirst();
        if (rootOptional.isEmpty()) {
            return null;
        }
        EmployeeHierarchy root = rootOptional.get();
        JSONObject jo = new JSONObject();
        jo.put(root.getEmployee(), getSupervisor(root.getEmployee(), all));
        return jo;
    }

    private JSONObject getSupervisor(String employee, List<EmployeeHierarchy> all) {
        List<EmployeeHierarchy> collect = all.stream().filter(e -> employee.equals(e.getSupervisor())).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return new JSONObject();
        }
        JSONObject jo = new JSONObject();
        for (EmployeeHierarchy e : collect) {
            jo.put(e.getEmployee(), getSupervisor(e.getEmployee(), all));
        }
        return jo;
    }
}
