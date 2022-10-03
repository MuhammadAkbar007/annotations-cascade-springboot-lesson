package uz.pdp.annotationsandcascade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.annotationsandcascade.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
