package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.doctor.DoctorService;
import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatientServiceTest {

    PatientService patientService;
    PressureDeviceService pressureDeviceService;

    @BeforeEach
    void setup() {
        patientService =new PatientServiceDummy();
        pressureDeviceService=new PressureDeviceServiceDummy();
    }

    //DUMMY
    @Test
    void givenPatientWithNoPressureLogsin5DaysThenReturnFalse() {
        //arrange
        PressureDevice pressureDevice=new PressureDevice(1L,new ArrayList<>());
        PressureLog pressureLog=new PressureLog( 1L, LocalDate.now().minusDays(50L),200);
        pressureDevice.getPressureLogs().add(pressureLog);
        Patient patient=new Patient(
                1L,
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertFalse(patientService.haveResponsefromDeviceinLast5days(patient));
    }
    @Test
    void testHaveResponsefromDeviceinLast5days() {
        // Create a patient with a pressure device that has pressure logs
        PressureLog olderLog = new PressureLog(1L, LocalDate.now().minusDays(6), 80);
        PressureLog newerLog = new PressureLog(2L, LocalDate.now().minusDays(2), 90);
        List<PressureLog> logs = Arrays.asList(olderLog, newerLog);
        PressureDevice device = new PressureDevice(1L, logs);
        Patient patient = new Patient(1L, "Mario", "Rossi", "Via Roma 1", "mario.rossi@example.com", 'M', LocalDate.of(1980, Month.JANUARY, 1), device);

        // Test when there is a response from device in the last 5 days
        PatientServiceStub patientService = new PatientServiceStub(true);
        assertTrue(patientService.haveResponsefromDeviceinLast5days(patient));

        // Test when there is no response from device in the last 5 days
        patientService = new PatientServiceStub(false);
        assertFalse(patientService.haveResponsefromDeviceinLast5days(patient));
    }
    @Test
    void testLast3MeasuresOutOfRange() {
        // Arrange
        PressureLog pressureLog1 = new PressureLog(1L, LocalDate.of(2023, 3, 15), 130);
        PressureLog pressureLog2 = new PressureLog(2L, LocalDate.of(2023, 3, 16), 140);
        PressureLog pressureLog3 = new PressureLog(3L, LocalDate.of(2023, 3, 17), 150);
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);
        pressureLogs.add(pressureLog3);
        PressureDevice pressureDevice = new PressureDevice(1L, pressureLogs);
        Patient patient = new Patient(1L, "John", "Doe", "Address", "email", 'M', LocalDate.of(1980, 1, 1), pressureDevice);
        PatientService patientService = new PatientServiceStub(true);
        DoctorMatrixService doctorMatrixService = new DoctorMatrixServiceDummy();
        DoctorService doctorService = new DoctorServiceImpl(patientService, doctorMatrixService);

        // Act
        boolean result = patientService.last3MeasuresOutOfRange(patient);

        // Assert
        assertTrue(result);
    }

    @Test
    void givenPatientWithLogsBeforeThisYearThenLogsAreDeleted() {
        //arrange
        PressureLog pressureLog1 = new PressureLog(1L, LocalDate.of(2023, 3, 15), 130);
        PressureLog pressureLog2 = new PressureLog(2L, LocalDate.of(2023, 3, 16), 140);
        PressureLog pressureLog3 = new PressureLog(3L, LocalDate.of(2023, 3, 17), 150);
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);
        pressureLogs.add(pressureLog3);
        PressureDevice pressureDevice = new PressureDevice(1L, pressureLogs);
        Patient patient = new Patient(
                1L,
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1980, Month.JANUARY, 1),
                pressureDevice
        );


        // act
        patientService.deleteLogBeforeThisYear(patient);

        // assert
        assertEquals(3, patient.getPressureDevice().getPressureLogs().size());
        assertTrue(patient.getPressureDevice().getPressureLogs().stream()
                .allMatch(log -> log.getDate().getYear() >= LocalDate.now().getYear()));
    }

}
