package com.bogdan.service;

import com.bogdan.exception.EmployeeCreateException;
import com.bogdan.model.Employee;
import com.bogdan.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    //Создает макетную реализацию для необходимых вам классов
    @Mock
    private EmployeeRepository employeeRepository;

    //Создает экземпляр класса и вводит в него mocks, помеченные аннотациями @Mock.
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void createNewEmployeeTest() {
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willAnswer(invocation -> invocation.getArgument(0));

        Employee createEmployee = employeeService.createEmployee(employee);

        assertEquals(employee, createEmployee);
    }

    @Test
    public void tryCreateEmployeeWithExistEmailTest() {
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        Exception exception = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.createEmployee(employee);
        });

        String expected = "Error create new Employee: employee with email: " +
                employee.getEmail() + " already exists!";

        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void getAllEmployeesTest() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Mark", "mark@gmail.com", 210.2));
        employeeList.add(new Employee(2L, "Tommy", "tommy@gmail.com", 310.67));
        employeeList.add(new Employee(3L, "Bonny", "bonny@gmail.com", 450.24));

        given(employeeRepository.findAll()).willReturn(employeeList);

        List<Employee> expected = employeeService.getAllEmployees();

        assertEquals(expected, employeeList);
    }

    @Test
    public void getEmployeeByIdTest() {
        final Long id = 1L;
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

        Employee expected = employeeService.getEmployeeById(id);

        assertThat(expected).isNotNull();
        assertEquals(expected, employee);
    }

    @Test
    public void getEmployeeByEmailTest() {
        final String email = "bob@gmail.com";
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findByEmail(email)).willReturn(Optional.of(employee));

        Employee expected = employeeService.getEmployeeByEmail(email);

        assertThat(expected).isNotNull();
        assertEquals(expected, employee);
    }

    @Test
    public void getAllEmployeesByNameTest() {
        final String name = "Mark";
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Mark", "mark@gmail.com", 210.2));
        employeeList.add(new Employee(3L, "Mark", "markNew@gmail.com", 555.2));

        given(employeeRepository.findByName(name)).willReturn(employeeList);

        List<Employee> expected = employeeService.getEmployeesByName(name);

        assertEquals(expected, employeeList);
    }

    @Test
    public void updateEmployeeTest() {
        final Long id = 1L;
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employee);

        final Employee expected = employeeService.updateEmployee(id, employee);

        assertThat(expected).isNotNull();
        assertEquals(employee, expected);
    }

    @Test
    public void deleteEmployeeTest() {
        final Long id = 1L;
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).deleteById(id);
    }
}
