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

        pressureDeviceService=new PressureDeviceServiceDummy();
        patientService=new PatientServiceImpl(pressureDeviceService);
        doctorMatrixServiceDummy =new DoctorMatrixServiceDummy();
    }

    //STUB
    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmpty() {
        //arrange
        doctorService=new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        PressureLog log=new PressureLog(1L, LocalDate.now().minusDays(10), 123);
        List<PressureLog> logs=new ArrayList<>();
        logs.add(log);
        PressureDevice pressureDevice=new PressureDevice(1L, logs);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList=new ArrayList<>();
        patientList.add(patient);
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsNoResponse=doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(),1);
    }

    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsNotEmptyWithStub() {
        //arrange
        patientServiceStub=new PatientServiceStub(false);
        doctorService=new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
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
        List patientsNoResponse=doctorService.getPatientsNoResponse(doctor);
        //assert
        assertFalse(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(),1);
    }

    @Test
    void givenPatientNoResponseThenPatientsNoResponseListIsEmptyWithStub() {
        //arrange
        patientServiceStub=new PatientServiceStub(true);
        doctorService=new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
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
        List patientsNoResponse=doctorService.getPatientsNoResponse(doctor);
        //assert
        assertTrue(patientsNoResponse.isEmpty());
        assertEquals(patientsNoResponse.size(),0);
    }

    @Test
    void givenPatientOutOfRangeThenPatientsOutOfRangeListIsNotEmptyWithStub() {
        //arrange
        patientServiceStub=new PatientServiceStub(true);
        doctorService=new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
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
        List patientsOutOfRange=doctorService.getPatientsOutOfRange(doctor);
        //assert
        assertFalse(patientsOutOfRange.isEmpty());
        assertEquals(patientsOutOfRange.size(),1);
    }


    @Test
    void givenPatientOutOfRangeThenPatientsOutOfRangeListIsEmptyWithStub() {
        //arrange
        patientServiceStub=new PatientServiceStub(false);
        doctorService=new DoctorServiceImpl(patientServiceStub, doctorMatrixServiceDummy);
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
        List patientsOutOfRange=doctorService.getPatientsOutOfRange(doctor);
        //assert
        assertTrue(patientsOutOfRange.isEmpty());
        assertEquals(patientsOutOfRange.size(),0);
    }

    @Test
    void givenPatientOutOfRangeThenPatientsOutOfRangeListIsNotEmpty() {
        //arrange
        doctorService=new DoctorServiceImpl(patientService, doctorMatrixServiceDummy);
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog1=new PressureLog(new Long(1), LocalDate.now().minusDays(3),200);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(2),200);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now().minusDays(1),200);
        pressureDevice.getPressureLogs().add(pressureLog1);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        List<Patient> patientList=new ArrayList<>();
        patientList.add(patient);
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", patientList, true);
        //act
        List patientsOutOfRange=doctorService.getPatientsOutOfRange(doctor);
        //assert
        assertFalse(patientsOutOfRange.isEmpty());
        assertEquals(patientsOutOfRange.size(),1);
    }
}
