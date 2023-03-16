package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DoctorServiceTest_withSpy {

    PatientServiceDummy patientService;
    DoctorService doctorService;

    DoctorMatrixServiceSpy doctorMatrixService;

    @BeforeEach
    void setup() {

        patientService=new PatientServiceDummy();
        doctorMatrixService=new DoctorMatrixServiceSpy();
    }

    //STUB
    @Test
    void givenFlagAddPatientTrueWhenAddPatientThenStopAddPatient() {
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
        for (int i=0;i<10;i++)
            doctorService.addPatientToDoctor(patient, doctor);
        doctor.setMorePatients(false);
        for (int i=0;i<10;i++)
            doctorService.addPatientToDoctor(patient, doctor);
        //assert
        assertEquals(doctorMatrixService.getCounter(), 10);
    }





    }
