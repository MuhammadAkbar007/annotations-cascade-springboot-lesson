package uz.pdp.annotationsandcascade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.annotationsandcascade.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
