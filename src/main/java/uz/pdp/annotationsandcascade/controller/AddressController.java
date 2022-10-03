package uz.pdp.annotationsandcascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.annotationsandcascade.entity.Address;
import uz.pdp.annotationsandcascade.entity.Person;
import uz.pdp.annotationsandcascade.payload.AddressDto;
import uz.pdp.annotationsandcascade.repository.AddressRepository;
import uz.pdp.annotationsandcascade.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @PostMapping
    public HttpEntity<?> add(@RequestBody List<AddressDto> addressDtoList) {
        List<Address> addressList = new ArrayList<>();
        for (AddressDto addressDto : addressDtoList) {
            Address address = new Address(
                    addressDto.getCity(), addressDto.getStreet(), personRepository.getOne(addressDto.getPersonId())
            );
            addressList.add(address);
        }
        addressRepository.saveAll(addressList);
        return ResponseEntity.ok("Saqlandi !");
    }
}
