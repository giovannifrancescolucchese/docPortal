package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctorMatrix.DoctorMatrixService;

public class DoctorMatrixServiceSpy implements DoctorMatrixService {

    int counter = 0;

    @Override
    public boolean canAddPatientToDoctor(Patient patient, Doctor doctor) {
        counter++;
        return true;
    }

    public int getCounter() {
        return counter;
    }

}
