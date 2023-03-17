package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceTest {

    PatientService patientService;
    PatientService patientServiceStub;
    DoctorService doctorService;

    PressureDeviceService pressureDeviceService;
    DoctorMatrixService doctorMatrixServiceDummy;

    @BeforeEach
    void setup() {

        //patientService=new PatientServiceStub(false);

        pressureDeviceService = new PressureDeviceServiceDummy();
        patientService = new PatientServiceImpl(pressureDeviceService);
        doctorMatrixServiceDummy = new DoctorMatrixServiceDummy();
    }

    //STUB
    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmpty() {
        //arrange
        doctorService = new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        PressureLog log = new PressureLog(1L, LocalDate.now().minusDays(10), 123);
        List<PressureLog> logs = new ArrayList<>();
        logs.add(log);
        PressureDevice pressureDevice = new PressureDevice(1L, logs);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse = doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(), 1);
    }


    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsEmpty() {
        //arrange
        doctorService = new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        PressureLog log = new PressureLog(1L, LocalDate.now().minusDays(2), 123);
        List<PressureLog> logs = new ArrayList<>();
        logs.add(log);
        PressureDevice pressureDevice = new PressureDevice(1L, logs);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse = doctorService.getPatientsNoResponse(doctor);
        //assert
        assertTrue(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(), 0);
    }

    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmptyWithStub() {
        //arrange
        patientServiceStub = new PatientServiceStub(false);
        doctorService = new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse = doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(), 1);
    }

    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsEmptyWithStub() {
        //arrange
        patientServiceStub = new PatientServiceStub(true);
        doctorService = new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse = doctorService.getPatientsNoResponse(doctor);
        //assert
        assertTrue(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(), 0);
    }


    @Test
    void givenPatientOutOfRangeThenPatientOutOfRangeListIsNotEmpty() {
        //arrange
        doctorService = new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        List<PressureLog> logs = new ArrayList<>();
        PressureLog log = new PressureLog(1L, LocalDate.now().minusDays(0), 300);
        logs.add(log);
        log = new PressureLog(1L, LocalDate.now().minusDays(1), 300);
        logs.add(log);
        log = new PressureLog(1L, LocalDate.now().minusDays(2), 300);
        logs.add(log);

        PressureDevice pressureDevice = new PressureDevice(1L, logs);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //act
        List patientOutOfRange = doctorService.getPatientsOutOfRange(doctor);
        //assert
        assertFalse(patientOutOfRange.isEmpty());
        assertEquals(patientOutOfRange.size(), 1);
    }


    @Test
    void givenPatientOutOfRangeWhenOneThayOutOfRangeThenPatientOutOfRangeListIsEmpty() {
        //arrange
        doctorService = new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        List<PressureLog> logs = new ArrayList<>();
        PressureLog log = new PressureLog(1L, LocalDate.now().minusDays(0), 300);
        logs.add(log);
        log = new PressureLog(1L, LocalDate.now().minusDays(1), 100);
        logs.add(log);
        log = new PressureLog(1L, LocalDate.now().minusDays(2), 100);
        logs.add(log);

        PressureDevice pressureDevice = new PressureDevice(1L, logs);
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(2000, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //act
        List patientOutOfRange = doctorService.getPatientsOutOfRange(doctor);
        //assert
        assertTrue(patientOutOfRange.isEmpty());
        assertEquals(patientOutOfRange.size(), 0);
    }


    @Test
    void givenAddPatientToDoctorWhenThereAreSpotsThenReturnTrue() {
        //arrange
        doctorService = new DoctorServiceMock();
        List<Patient> patientList = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //creo manualmente una lista di pazienti lasciando spazio libero

        for (int i = 0; i < 79; i++) {
            PressureDevice pressureDevice = new PressureDevice(new Long(i), new ArrayList<>());
            PressureLog pressureLog = new PressureLog(new Long(i), LocalDate.now(), 100);
            pressureDevice.getPressureLogs().add(pressureLog);
            Patient patient = new Patient(
                    new Long(i),
                    "nome" + i,
                    "cognome" + i,
                    "indirizzo",
                    "email",
                    'M',
                    LocalDate.of(2000, Month.JULY, 29),
                    pressureDevice
            );
            patientList.add(patient);
            //doctorService.addPatientToDoctor(patient, doctor);
        }
        //aggiungo un nuovo paziente che può essere associato al dottore
        PressureDevice pressureDevice = new PressureDevice(100L, new ArrayList<>());
        PressureLog pressureLog = new PressureLog(100L, LocalDate.now(), 200);
        pressureDevice.getPressureLogs().add(pressureLog);
        Patient pazienteNuovo = new Patient(
                100L,
                "nomeNuovo",
                "cognomeNuovo",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(2000, Month.JULY, 29),
                pressureDevice
        );
        //act
        Boolean addPatient = doctorService.addPatientToDoctor(pazienteNuovo, doctor);

        //assert
        assertTrue(addPatient);
    }

    @Test
    void givenAddPatientToDoctorWhenThereAreNotFreeSpotsThenReturnFalse() {
        //arrange
        doctorService = new DoctorServiceMockFalse();
        List<Patient> patientList = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDottore", "Via Garibaldi 1",
                "dottore@email.it", patientList, true);
        //creo manualmente una lista di pazienti lasciando spazio libero

        //aggiungo un nuovo paziente che può essere associato al dottore
        PressureDevice pressureDevice = new PressureDevice(100L, new ArrayList<>());
        PressureLog pressureLog = new PressureLog(100L, LocalDate.now(), 200);
        pressureDevice.getPressureLogs().add(pressureLog);
        Patient pazienteNuovo = new Patient(
                100L,
                "nomeNuovo",
                "cognomeNuovo",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1900, Month.JULY, 29),
                pressureDevice
        );
        //act
        Boolean addPatient = doctorService.addPatientToDoctor(pazienteNuovo, doctor);

        //assert
        assertFalse(addPatient);
    }
}