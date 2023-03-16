package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.doctorMatrix.DoctorMatrixServiceImpl;
import it.euris.service.patient.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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





    }
