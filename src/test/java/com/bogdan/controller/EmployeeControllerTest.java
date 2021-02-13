package com.bogdan.controller;

import com.bogdan.model.Employee;
import com.bogdan.service.EmployeeService;
import com.bogdan.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
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

    private List<Employee> employeeList;

    @Before
    public void init() {
        System.out.println("++++");
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "Mark", "mark@gmail.com", 210.2));
        employeeList.add(new Employee(2L, "Tommy", "tommy@gmail.com", 310.67));
        employeeList.add(new Employee(3L, "Bonny", "bonny@gmail.com", 450.24));
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employeeList.size()));
    }
}
