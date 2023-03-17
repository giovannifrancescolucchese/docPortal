package it.euris.service.DoctorMatrix;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.doctorMatrix.DoctorMatrixServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoctorMatrixServiceImplTest {
    @Test
    public void testCanAddPatientToDoctor() {
        // Stub del parametro Doctor con 60 pazienti e media età pari a 55
        Doctor doctorStub = Mockito.mock(Doctor.class);
        List<Patient> patients = new ArrayList<>();
        for(int i=0; i<60; i++) {
            Patient patient = Mockito.mock(Patient.class);
            Mockito.when(patient.getAge()).thenReturn(50);
            patients.add(patient);
        }
        Mockito.when(doctorStub.getPatients()).thenReturn(patients);

        // Istanzia oggetto DoctorMatrixServiceImpl e chiama il metodo canAddPatientToDoctor
        DoctorMatrixService doctorMatrixService = new DoctorMatrixServiceImpl();
        boolean result = doctorMatrixService.canAddPatientToDoctor(Mockito.mock(Patient.class), doctorStub);

        // Verifica che il metodo abbia restituito true
        assertTrue(result);
    }
}
