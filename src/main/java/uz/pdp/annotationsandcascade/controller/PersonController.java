package uz.pdp.annotationsandcascade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.annotationsandcascade.entity.Address;
import uz.pdp.annotationsandcascade.entity.Person;
import uz.pdp.annotationsandcascade.payload.AddressDto;
import uz.pdp.annotationsandcascade.payload.PersonDto;
import uz.pdp.annotationsandcascade.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping
    public HttpEntity<?> add(@RequestBody PersonDto personDto) {
        Person person = new Person();
        person.setFullName(personDto.getFullName());

        List<Address> addressList = new ArrayList<>();
        for (AddressDto addressDto : personDto.getAddressDtoList()) {
            Address address = new Address(
                    addressDto.getCity(), addressDto.getStreet(), person
            );
            addressList.add(address);
        }
        person.setAddresses(addressList);
        personRepository.save(person);
        return ResponseEntity.ok("Saqlandi !");
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id) {
        Person person = personRepository.getOne(id);
        person.setFullName("IsmO'zgartirildi");

        for (Address address : person.getAddresses()) {
            address.setStreet("Ko'chaO'zgardi");
        }
        personRepository.save(person);
        return ResponseEntity.ok("Tahrirlandi !");
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        try {
            personRepository.deleteById(id);
            return ResponseEntity.ok("O'chirildi !");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("O'chirilmadi !");
        }
    }

//    @Transactional(dontRollbackOn = NullPointerException.class) // shunday bo'lsa orqaga qaytarma
    @Transactional(rollbackOn = NullPointerException.class) // shunday bo'lsa orqaga qaytar
    @DeleteMapping("/forTransactional/{id}")
    public HttpEntity<?> deleteTransactionally(@PathVariable Integer id) {
        personRepository.deleteById(id);
        throw new NullPointerException();
    }

    @GetMapping
    public HttpEntity<?> getPersons() {
        return ResponseEntity.ok(personRepository.findAll());
    }
}
