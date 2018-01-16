package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.repository.EmployeeRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN')")
    public Employee createEmployee(Employee newEmployee) {
        newEmployee.setPassword(bCryptPasswordEncoder.encode(newEmployee.getPassword()));
        return employeeRepository.saveAndFlush(newEmployee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Employee find(Long id) {
        return employeeRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #updatedEmployee.username == principal)")
    public Employee updateEmployee(Employee updatedEmployee) {

        Employee employee = employeeRepository.findOne(updatedEmployee.getId());

        employee.setAddress(updatedEmployee.getAddress());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setName(updatedEmployee.getName());
        employee.setSurname(updatedEmployee.getSurname());

        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #username == principal)")
    public Employee changePassword(String username,
                                   String newPassword,
                                   String oldPassword,
                                   String newPasswordConfirmed) throws Exception {

        if(!newPasswordConfirmed.equals(newPassword)) {
           throw new Exception("Passwords do not match!");
        }
        Employee employee = employeeRepository.findByUsername(username);
        if(!employee.getPassword().equals(bCryptPasswordEncoder.encode(oldPassword))) {
            throw new Exception("Passwords do not match!");
        }
        employee.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return employeeRepository.saveAndFlush(employee);
    }
}
