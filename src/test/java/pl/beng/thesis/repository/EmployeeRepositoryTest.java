package pl.beng.thesis.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import pl.beng.thesis.model.Address;
import pl.beng.thesis.model.Developer;
import pl.beng.thesis.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    private final String username = "Tomek";
    private final String email = "Tomek@outlook.com";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Rollback(value = true)
    public void testMethodFindByUsername() {
        // given
        Employee newDeveloper = new Developer("Tomek", "Tomek",
                                              username, "password",
                                              email, "123456789");
        newDeveloper.setAddress(new Address("Lodz", "Heleny", 123));
        employeeRepository.saveAndFlush(newDeveloper);

        // when
        Employee foundEmployee = employeeRepository.findByUsername(username);

        // then
        assertThat(foundEmployee.getUsername().equals(username));
    }

    @Test
    @Rollback(value = true)
    public void testMethodFindByEmail() {
        // given
        Employee newDeveloper = new Developer("Tomek", "Tomek",
                                              username, "password",
                                              email, "123456789");
        newDeveloper.setAddress(new Address("Lodz", "Heleny", 123));
        employeeRepository.saveAndFlush(newDeveloper);

        // when
        Employee foundEmployee = employeeRepository.findByEmail(email);

        // then
        assertThat(foundEmployee.getEmail().equals(email));
    }
}
