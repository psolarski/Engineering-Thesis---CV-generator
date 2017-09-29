package pl.beng.thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "cv-generator/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeController(EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> findAll() {

        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> findOne(@PathVariable("id") long id) {

        return new ResponseEntity<>(employeeService.find(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateEmployee(@RequestBody Employee updatedEmployee) {

        Employee employee = employeeService.find(updatedEmployee.getId());

        employee.setAddress(updatedEmployee.getAddress());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setName(updatedEmployee.getName());
        employee.setSurname(updatedEmployee.getSurname());

        employeeService.updateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
