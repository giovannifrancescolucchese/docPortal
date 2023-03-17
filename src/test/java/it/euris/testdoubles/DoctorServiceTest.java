package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.doctorMatrix.DoctorMatrixServiceImpl;
import it.euris.service.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceTest {

    PatientService patientService;
    DoctorService doctorService;

    DoctorMatrixService doctorMatrixService;

    @BeforeEach
    void setup() {

        patientService=new PatientServiceStub(false);
        doctorMatrixService=new DoctorMatrixServiceDummy();
    }

    //STUB
    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmpty() {
        //arrange
        doctorService=new DoctorServiceImpl(patientService, doctorMatrixService);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );
        List<Patient> patientList=new ArrayList<>();
        patientList.add(patient);
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsOutOfRange=doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsOutOfRange.isEmpty());

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
    @Test
    void givenPatientsNoResponseThenReturnPatientsNoResponseList() {
        // Arrange
        PatientService patientService = new PatientServiceStub(false);
        DoctorMatrixService doctorMatrixService = new DoctorMatrixServiceDummy();
        DoctorService doctorService = new DoctorServiceImpl(patientService, doctorMatrixService);

        Patient patient1 = new Patient(
                1L,
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );
        Patient patient2 = new Patient(
                2L,
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'F',
                LocalDate.of(1987, Month.AUGUST, 12),
                null
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient1);
        patientList.add(patient2);

        Doctor doctor = new Doctor(
                1L,
                "nomeDottore",
                "cognomeDottore",
                "Via Garibaldi 1",
                "dottore@email.it",
                patientList,
                true
        );

        // Act
        List<Patient> patientsNoResponse = doctorService.getPatientsNoResponse(doctor);

        // Assert
        assertEquals(2, patientsNoResponse.size());
        assertEquals(patient1.getId(), patientsNoResponse.get(0).getId());
    }





}
