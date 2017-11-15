package pl.beng.thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Address;
import pl.beng.thesis.service.AddressService;

import java.util.List;

@RestController
@RequestMapping(value = "addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Find address by given id.
     * @param id of address
     * @return address with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> findOne(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(addressService.find(id), HttpStatus.OK);
    }

    /**
     * Find and return all addresses.
     *
     * @return list of addresses
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Address>> findAll() {

        return new ResponseEntity<>(addressService.findAllAddresses(), HttpStatus.OK);
    }

    /**
     * Update existing address by given address object.
     *
     * @param updatedAddress given address to update.
     * @return updated address.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Address> updateAddress(@RequestBody Address updatedAddress) {

        return new ResponseEntity<>(addressService.updateAddress(updatedAddress), HttpStatus.OK);
    }

    /**
     * Create new address.
     *
     * @param newAddress given address to create.
     * @return created address.
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) {

        return new ResponseEntity<>(addressService.createAddress(newAddress), HttpStatus.OK);
    }
}
