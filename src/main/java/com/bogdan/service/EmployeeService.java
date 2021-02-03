package com.bogdan.service;

import com.bogdan.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    Employee getEmployeeById(Long id);

    Employee getEmployeeByEmail(String email);

    List<Employee> getEmployeesByName(String name);

    List<Employee> getAllEmployees();

//    List<Employee> getTop3EmployeesBySalary();

    void deleteEmployee(Long id);
}
