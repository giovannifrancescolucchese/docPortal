package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.doctorMatrix.DoctorMatrixServiceImpl;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DoctorServiceTest {

    PatientService patientService;
    PatientService patientServiceStubFalse;
    PatientService patientServiceStubTrue;
    DoctorService doctorService;

    PressureDeviceService pressureDeviceService;
    DoctorMatrixService doctorMatrixService;
    DoctorMatrixServiceDummy doctorMatrixServiceDummy;


    @BeforeEach
    void setup() {

        //patientService=new PatientServiceStub(false);
        patientServiceStubFalse = new PatientServiceStub(false);
        patientServiceStubTrue = new PatientServiceStub(true);
        pressureDeviceService = new PressureDeviceServiceDummy();
        patientService = new PatientServiceImpl(pressureDeviceService);
        doctorMatrixServiceDummy = new DoctorMatrixServiceDummy();
        doctorMatrixService = new DoctorMatrixServiceImpl();
    }

    //STUB
    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmpty() {
        //arrange
        doctorService = new DoctorServiceImpl(patientServiceStubFalse, doctorMatrixServiceDummy);

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
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDotttore", "Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse = doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(), 1);

    }

    @Test
    void givenPatientOutOfRangeThenPatientMeasuresAreOutOfRangeListIsNotEmpty() {
        doctorService = new DoctorServiceImpl(patientServiceStubTrue, doctorMatrixServiceDummy);

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
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDotttore", "Via Garibaldi 1", "dottore@email.it", patientList, true);
        List<Patient> patientsOutOfRange = doctorService.getPatientsOutOfRange(doctor);
        assertFalse(patientsOutOfRange.isEmpty());
        assertEquals(1, patientsOutOfRange.size());
    }

    @Test
    void given51PatientsWithAgeOver70ThenAddThe51PatientAtTheDoctorResponseIsFalse() {
        doctorService = new DoctorServiceImpl(patientServiceStubFalse, doctorMatrixService);
        List<Patient> patientsList = new ArrayList<>();
        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDotttore", "Via Garibaldi 1", "dottore@email.it", patientsList, true);

        for (int i = 0; i <= 49; i++) {
            Patient patient = new Patient(
                    new Long(i),
                    "nome",
                    "cognome",
                    "indirizzo",
                    "email",
                    'M',
                    LocalDate.of(1920, Month.JULY, 29),
                    null
            );

            doctorService.addPatientToDoctor(patient, doctor);
        }

        Boolean result = doctorService.addPatientToDoctor(new Patient(50L,
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(2000, Month.JULY, 29),
                null
        ), doctor);

        assertEquals(Boolean.FALSE, result);

    }
}
