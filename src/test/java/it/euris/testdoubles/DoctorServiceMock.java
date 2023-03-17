package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctor.DoctorService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DoctorServiceMock implements DoctorService {
    private List<Patient> patientList = new ArrayList<>();

    @Override
    public boolean addPatientToDoctor(Patient patient, Doctor doctor) {
        patientList.add(patient);
        return true;
    }

    @Override
    public List<Patient> getPatientsOutOfRange(Doctor doctor) {
        return patientList;
    }

    @Override
    public List<Patient> getPatientsNoResponse(Doctor doctor) {
        return new ArrayList<>();
    }


    public void verify() {
        assertThat(patientList.size() > 0);
    }
}
