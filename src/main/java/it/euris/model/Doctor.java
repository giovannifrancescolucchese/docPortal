package it.euris.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    Long id;
    String name;
    String surname;
    String address;
    String email;
    List<Patient> patients;
    Boolean morePatients;

}
