package it.euris.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient {
    Long id;
    String name;
    String surname;
    String address;
    String email;
    char sex;
    LocalDate birthDate;
    PressureDevice pressureDevice;


    public int getAge() {
        return LocalDate.now().getYear() - this.birthDate.getYear();
    }

}
