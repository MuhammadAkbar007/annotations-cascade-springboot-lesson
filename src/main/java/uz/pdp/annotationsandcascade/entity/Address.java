package uz.pdp.annotationsandcascade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Person person;

    public Address(String city, String street, Person person) {
        this.city = city;
        this.street = street;
        this.person = person;
    }
}
