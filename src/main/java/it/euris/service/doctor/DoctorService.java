package it.euris.service.doctor;

import it.euris.model.Doctor;
import it.euris.model.Patient;

import java.util.List;

public interface DoctorService {

    boolean addPatientToDoctor(Patient patient, Doctor doctor);

    List<Patient> getPatientsOutOfRange(Doctor doctor);

    List<Patient> getPatientsNoResponse(Doctor doctor);
}
