package pl.beng.thesis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import pl.beng.thesis.model.DTO.EditEmployeeDTO;
import pl.beng.thesis.model.Employee;
import pl.beng.thesis.repository.EmployeeRepository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.List;

@Service
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            "OR (hasRole('ROLE_DEV') AND #username == principal)")
    public Employee updateEmployee(EditEmployeeDTO updatedEmployee, String username, int ETag) {

        Employee employee = employeeRepository.findByUsername(username);

        if(ETag != employee.getVersion()) {
            throw new OptimisticLockException("Employees versions do not match!");
        }
        employee.setAddress(updatedEmployee.getAddress());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setName(updatedEmployee.getName());
        employee.setSurname(updatedEmployee.getSurname());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setRoles(updatedEmployee.getRoles());

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
                                   String newPasswordConfirmed,
                                   int ETag) throws Exception {

        if(!newPasswordConfirmed.equals(newPassword)) {
           throw new Exception("Passwords do not match!");
        }
        Employee employee = employeeRepository.findByUsername(username);

        if(ETag != employee.getVersion()) {
            throw new OptimisticLockException("Employees versions do not match!");
        }

        if(!bCryptPasswordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new Exception("Passwords do not match!");
        }
        employee.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Employee activate(String username, boolean active, int ETag) {

        Employee employee = employeeRepository.findByUsername(username);
        if(ETag != employee.getVersion()) {
            throw new OptimisticLockException("Employees versions do not match!");
        }
        employee.setActive(active);
        return employeeRepository.saveAndFlush(employee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Employee lock(String username, boolean locked, int ETag) {

        Employee employee = employeeRepository.findByUsername(username);
        if(ETag != employee.getVersion()) {
            throw new OptimisticLockException("Employees versions do not match!");
        }
        employee.setLocked(locked);
        return employeeRepository.saveAndFlush(employee);
    }
}
