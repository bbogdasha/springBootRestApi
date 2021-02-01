package com.bogdan.service;

import com.bogdan.exception.EmployeeCreateException;
import com.bogdan.model.Employee;
import com.bogdan.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (optionalEmployee.isPresent()) {
            throw new EmployeeCreateException("Error create new Employee: employee with email: " + employee.getEmail() + " already exists!");
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (optionalEmployee.isPresent()) {
            throw new EmployeeCreateException("Error update new Employee: employee with email: " + employee.getEmail() + " already exists!");
        }

        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getTop3EmployeesBySalary() {
        return employeeRepository.findTop3BySalary();
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
