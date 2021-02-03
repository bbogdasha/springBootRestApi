package com.bogdan.service;

import com.bogdan.exception.EmployeeCreateException;
import com.bogdan.model.Employee;
import com.bogdan.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (optionalEmployee.isPresent()) {
            throw new EmployeeCreateException("Error create new Employee: employee with email: " +
                    employee.getEmail() + " already exists!");
        }

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (!optionalEmployee.isPresent()) {
            throw new EntityNotFoundException("Employee with id: " + id + " not found!");
        }
        employee.setId(optionalEmployee.get().getId());

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (!optionalEmployee.isPresent()) {
            throw new EntityNotFoundException("Employee with id: " + id + " not found!");
        }

        return optionalEmployee.get();
    }

    public Employee getEmployeeByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);

        if (!optionalEmployee.isPresent()) {
            throw new EntityNotFoundException("Employee with email: " + email + " not found!");
        }

        return optionalEmployee.get();
    }

    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.findByName(name);
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

//    public List<Employee> getTop3EmployeesBySalary() {
//        return employeeRepository.findTop3BySalary();
//    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
