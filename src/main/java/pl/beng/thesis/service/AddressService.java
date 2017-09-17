package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beng.thesis.model.Address;
import pl.beng.thesis.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address find(Long id) {
        return addressRepository.findOne(id);
    }

    @Transactional
    public List<Address> findAllAddresses() {
            return addressRepository.findAll();
    }

    @Transactional
    public Address createAddress(Address newAddress) {
        return addressRepository.saveAndFlush(newAddress);
    }

    @Transactional
    public Address updateAddress(Address updatedAddress) {
        return addressRepository.saveAndFlush(updatedAddress);
    }
}
