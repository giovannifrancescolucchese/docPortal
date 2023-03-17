package it.euris.service.doctor;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.testdoubles.PatientServiceDummy;
import it.euris.testdoubles.PatientServiceStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DoctorServiceImplTest {
    //dejd@Test
    @Test
    public void testAddPatientToDoctor() {
        // create stubs
        PatientService patientService = mock(PatientService.class);
        DoctorMatrixService doctorMatrixService = mock(DoctorMatrixService.class);
        // configure stubs
        when(doctorMatrixService.canAddPatientToDoctor(any(Patient.class), any(Doctor.class))).thenReturn(true);
        // create service to test with stubs
        DoctorService doctorService = new DoctorServiceImpl(patientService, doctorMatrixService);
        Doctor doctor = new Doctor(1L, "fff", "fff", "fff", "ddddd", new ArrayList<>(), true);
        doctor.setMorePatients(true);
        Patient patient = new Patient();
        boolean result = doctorService.addPatientToDoctor(patient, doctor);
        assertTrue(result);
        assertEquals(1, doctor.getPatients().size());
    }
    @Test
    void givenDoctorWithPatientsThenReturnOutOfRangePatients() {
        // arrange
        List<PressureLog> logs = new ArrayList<>();
        logs.add(new PressureLog(1L, LocalDate.now(), 80)); // dentro range
        logs.add(new PressureLog(2L, LocalDate.now(), 90)); // dentro range
        logs.add(new PressureLog(3L, LocalDate.now(), 110)); // fuori range
        logs.add(new PressureLog(4L, LocalDate.now(), 120)); // fuori range
        PressureDevice device = new PressureDevice(1L, logs);
        Patient inRangePatient = new Patient(1L, "Mario", "Rossi", "Via Roma 1", "mario.rossi@example.com", 'M', LocalDate.of(1980, Month.JANUARY, 1), device);
        Patient outOfRangePatient = new Patient(2L, "Luigi", "Verdi", "Via Garibaldi 1", "luigi.verdi@example.com", 'M', LocalDate.of(1970, Month.MARCH, 15), device);
        List<Patient> patientList = new ArrayList<>();
        patientList.add(inRangePatient);
        patientList.add(outOfRangePatient);
        Doctor doctor = new Doctor(1L, "Nome", "Cognome", "Via Dante 10", "nome.cognome@example.com", patientList, true);

        // create a stub for the patientService to return the expected results
        PatientService patientService = Mockito.mock(PatientService.class);
        Mockito.when(patientService.last3MeasuresOutOfRange(inRangePatient)).thenReturn(false);
        Mockito.when(patientService.last3MeasuresOutOfRange(outOfRangePatient)).thenReturn(true);

        DoctorServiceImpl doctorService = new DoctorServiceImpl(patientService, null);

        // act
        List<Patient> outOfRangePatients = doctorService.getPatientsOutOfRange(doctor);

        // assert
        assertFalse(outOfRangePatients.isEmpty());
        assertEquals(1, outOfRangePatients.size());
        assertEquals(outOfRangePatient, outOfRangePatients.get(0));
    }

}
