package pl.beng.thesis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.security.SecurityConstants;
import pl.beng.thesis.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmployeeController(EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<Employee> findLoggedEmployee() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee loggedEmployee = employeeService.findByUsername(username);
        return new ResponseEntity<>(loggedEmployee, HttpStatus.OK);
    }
    /**
     * Find and return list of employees
     *
     * @return all employees list
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> findAll() {
        logger.info(bCryptPasswordEncoder.encode("password"));

        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    /**
     * Find and return employee with specified id
     *
     * @param id employee id
     * @return Employee with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> findOne(@PathVariable("id") long id) {

        return new ResponseEntity<>(employeeService.find(id), HttpStatus.OK);
    }

    /**
     * Find and update employee with given body
     *
     * @param updatedEmployee employee with new values
     * @return Employee with updated fields
     */
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
