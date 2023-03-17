package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctorMatrix.DoctorMatrixService;

public class DoctorMatrixServiceDummy implements DoctorMatrixService {

    @Override
    public boolean canAddPatientToDoctor(Patient patient, Doctor doctor) {
        return false;//TODO
    }
}
