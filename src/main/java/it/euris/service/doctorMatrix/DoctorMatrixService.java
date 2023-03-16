package it.euris.service.doctorMatrix;

import it.euris.model.Doctor;
import it.euris.model.Patient;

public interface DoctorMatrixService {

    boolean canAddPatientToDoctor(Patient patient, Doctor doctor);
}
