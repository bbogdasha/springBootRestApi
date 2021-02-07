package com.bogdan.service;

import com.bogdan.model.Employee;
import com.bogdan.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    //Создает макетную реализацию для необходимых вам классов
    @Mock
    private EmployeeRepository employeeRepository;

    //Создает экземпляр класса и вводит в него mocks, помеченные аннотациями @Mock.
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void createNewEmployeeTest() {
        final Employee employee = new Employee(1L, "Bob", "bob@gmail.com", 1200.20);

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willAnswer(invocation -> invocation.getArgument(0));

        Employee createEmployee = employeeService.createEmployee(employee);

        assertThat(createEmployee).isNotNull();

        verify(employeeRepository).save(any(Employee.class));
    }
}
