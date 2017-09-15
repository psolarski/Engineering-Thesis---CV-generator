package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
