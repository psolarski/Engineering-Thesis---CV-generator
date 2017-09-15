package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
