package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
