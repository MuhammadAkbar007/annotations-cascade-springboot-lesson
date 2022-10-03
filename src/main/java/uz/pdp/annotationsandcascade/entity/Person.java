package uz.pdp.annotationsandcascade.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"birthDate", "fullName"}) // shularni berib yuborma
@Where(clause = "birth_date is not null")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private LocalDate birthDate;

    @OrderBy(value = "city asc, street desc") // city ni o'sish tartibida, streetni kamayish tartibida olib beradi
    @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
    private List<Address> addresses;

    @Transient // DB da bunday ustun bo'lmaydi, backend da field sifatida ishlayveradi
    private Integer countFullNameLetters;

    @Transient
    private Integer age;

    public Integer getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public Integer getCountFullNameLetters() {
        return fullName != null ? fullName.length() : 0;
    }
}
