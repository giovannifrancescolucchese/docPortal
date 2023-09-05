package it.euris.model;

import lombok.AllArgsConstructor;import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        return ((Long)ChronoUnit.YEARS.between(this.birthDate, LocalDate.now())).intValue();
        //return LocalDate.now().getYear()-this.birthDate.getYear();
    }

}
