package com.bogdan.service;

import com.bogdan.exception.EmployeeCreateException;
import com.bogdan.exception.EmployeeNotFoundException;
import com.bogdan.model.Employee;
import com.bogdan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (optionalEmployee.isPresent()) {
            throw new EmployeeCreateException("Error create new Employee: employee with email: " +
                    employee.getEmail() + " already exists!");
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Optional<Employee> optionalEmployeeEmail = employeeRepository.findByEmail(employee.getEmail());

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found!");
        } else if (optionalEmployeeEmail.isPresent() && !optionalEmployeeEmail.get().getId().equals(id)) {
            throw new EmployeeCreateException("Error update exists Employee: this email: " +
                    employee.getEmail() + " already exists!");
        }
        employee.setId(optionalEmployee.get().getId());

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found!");
        }

        return optionalEmployee.get();
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with email: " + email + " not found!");
        }

        return optionalEmployee.get();
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.findByName(name);
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

    @Override
    public List<Employee> getTop3EmployeesBySalary() {
        List<Employee> employees = employeeRepository.findTop3ByOrderBySalaryDesc();
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

    @Override
    public Employee deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (!optionalEmployee.isPresent()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found!");
        }

        employeeRepository.deleteById(id);
        return optionalEmployee.get();
    }
}
