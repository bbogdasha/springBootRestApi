package com.bogdan.controller;

import com.bogdan.exception.EmployeeNotFoundException;
import com.bogdan.model.Employee;
import com.bogdan.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Employee> employeeList;

    @Before
    public void init() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Mark", "mark@gmail.com", 210.2));
        employeeList.add(new Employee(2L, "Tommy", "tommy@gmail.com", 310.67));
        employeeList.add(new Employee(3L, "Bonny", "bonny@gmail.com", 450.24));
        employeeList.add(new Employee(4L, "Emily", "emilyOne@gmail.com", 300.80));
        employeeList.add(new Employee(5L, "Emily", "emilyTwo@gmail.com", 286.40));
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employeeList.size()));
    }

    @Test
    public void getAllEmployeesByNameTest() throws Exception {
        final String name = "Emily";
        final List<Employee> employeesWithSameName = employeeList.stream()
                .filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());

        when(employeeService.getEmployeesByName(name)).thenReturn(employeesWithSameName);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/name/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employeesWithSameName.size()));
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        final Long id = 1L;
        Employee employeeWithId = new Employee(1L, "Carl", "carl@gmail.com", 345.31);
        when(employeeService.getEmployeeById(id)).thenReturn(employeeWithId);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/id/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(employeeWithId.getName()))
                .andExpect(jsonPath("$.email").value(employeeWithId.getEmail()))
                .andExpect(jsonPath("$.salary").value(employeeWithId.getSalary()));
    }

    @Test
    public void throwException404WhenFindEmployeeById() throws Exception {
        final Long id = 1L;
        when(employeeService.getEmployeeById(id)).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/id/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEmployeeByEmailTest() throws Exception {
        final String email = "carl@gmail.com";
        Employee employeeWithEmail = new Employee(1L, "Carl", "carl@gmail.com", 345.31);
        when(employeeService.getEmployeeByEmail(email)).thenReturn(employeeWithEmail);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/email/" + email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(employeeWithEmail.getName()))
                .andExpect(jsonPath("$.email").value(employeeWithEmail.getEmail()))
                .andExpect(jsonPath("$.salary").value(employeeWithEmail.getSalary()))
                .andDo(print());
    }

    @Test
    public void throwException404WhenFindEmployeeByEmail() throws Exception {
        final String email = "carl@gmail.com";
        when(employeeService.getEmployeeByEmail(email)).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/email/" + email))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEmployeesWithTopSalaryTest() throws Exception {
        final List<Employee> employeesWithTopSalary = employeeList.stream()
                .sorted(comparing(Employee::getSalary, comparing(Math::abs))
                .reversed())
                .limit(3)
                .collect(Collectors.toList());

        when(employeeService.getTop3EmployeesBySalary()).thenReturn(employeesWithTopSalary);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee/top-salary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(employeesWithTopSalary.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(employeesWithTopSalary.get(1).getName()))
                .andExpect(jsonPath("$[2].name").value(employeesWithTopSalary.get(2).getName()));
    }

    @Test
    public void createEmployeeTest() throws Exception {
        Employee employee = new Employee(1L, "Timmy", "timmy@gmail.com", 250.40);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    public void throwException400WhenCreatedEmployeeWithoutEmail() throws Exception {
        Employee employee = new Employee(1L, "Timmy", null, 250.3);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        final Long id = 1L;
        Employee employee = new Employee(id, "Timmy", "mark@gmail.com", 250.3);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/api/employee/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        final Long id = 1L;
        Employee employee = new Employee(id, "Timmy", "timmy@gmail.com", 250.3);
        when(employeeService.deleteEmployee(id)).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/employee/delete/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void throwException404WhenDeleteEmployeeById() throws Exception {
        final Long id = 1L;
        when(employeeService.deleteEmployee(id)).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/employee/delete/" + id))
                .andExpect(status().isNotFound());
    }
}
