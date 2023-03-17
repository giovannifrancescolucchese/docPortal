package it.euris.service.doctorMatrix;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DoctorMatrixServiceImplTest {

    DoctorMatrixServiceImpl doctorMatrixService;

    @BeforeEach
    void init(){
        doctorMatrixService = new DoctorMatrixServiceImpl();
    }

    @Test
    void givenAFullDoctorWhenCheckingIfAPatientCanBeAddedThenExpectedFalse(){
        //arrange
        Doctor doctor = new Doctor(1L,
                "dottore",
                "dottore",
                "indirizzo",
                "mail",
                null,
                true
                );
        List<Patient> patients = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            Patient patient = new Patient(1L,
                    "",
                    "",
                    "",
                    "",
                    'M',
                    LocalDate.of(1915, Month.JULY, 29),
                    null);
            patients.add(patient);
        }
        doctor.setPatients(patients);
        Patient insertPatient = new Patient(1L,
                "",
                "",
                "",
                "",
                'M',
                LocalDate.of(1995, Month.JULY, 29),
                null);
        //act
        boolean res = doctorMatrixService.canAddPatientToDoctor(insertPatient, doctor);
        //assert
        Assertions.assertFalse(res);
    }

    @Test
    void givenAnEmptyDoctorWhenCheckingIfAPatientCanBeAddedThenExpectedTrue(){
        //arrange
        Doctor doctor = new Doctor(1L,
                "dottore",
                "dottore",
                "indirizzo",
                "mail",
                null,
                true
        );
        List<Patient> patients = new ArrayList<>();
        doctor.setPatients(patients);
        Patient insertPatient = new Patient(1L,
                "",
                "",
                "",
                "",
                'M',
                LocalDate.of(1995, Month.JULY, 29),
                null);
        //act
        boolean res = doctorMatrixService.canAddPatientToDoctor(insertPatient, doctor);
        //assert
        Assertions.assertTrue(res);
    }

    @Test
    void givenADoctorWith49PeopleOfAverageAgeOver70WhenCheckingIfAPatientCanBeAddedThenExpectedTrue(){
        //arrange
        Doctor doctor = new Doctor(1L,
                "dottore",
                "dottore",
                "indirizzo",
                "mail",
                null,
                true
        );
        List<Patient> patients = new ArrayList<>();
        for(int i = 0; i < 48; i++){
            Patient patient = new Patient(1L,
                    "",
                    "",
                    "",
                    "",
                    'M',
                    LocalDate.of(1915, Month.JULY, 29),
                    null);
            patients.add(patient);
        }
        doctor.setPatients(patients);
        Patient insertPatient = new Patient(1L,
                "",
                "",
                "",
                "",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null);
        //act
        boolean res = doctorMatrixService.canAddPatientToDoctor(insertPatient, doctor);
        //assert
        Assertions.assertTrue(res);
    }
}
