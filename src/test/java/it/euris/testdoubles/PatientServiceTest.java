package it.euris.testdoubles;

import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientServiceTest {

    PatientService patientService;
    PressureDeviceServiceDummy pressureDeviceService;

    PressureDeviceService pressureDeviceServiceWithImpl;

    @BeforeEach
    void setup() {
        pressureDeviceService = new PressureDeviceServiceDummy();
        pressureDeviceServiceWithImpl = new PressureDeviceServiceImpl();

    }

    //DUMMY
    @Test
    void givenPatientWithNoPressureLogsin5DaysThenReturnFalse() {
        //arrange
        PressureDevice pressureDevice = new PressureDevice(new Long(1), new ArrayList<>());
        PressureLog pressureLog = new PressureLog(new Long(1), LocalDate.now().minusDays(50L), 200);
        pressureDevice.getPressureLogs().add(pressureLog);
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
        //act
        patientService = new PatientServiceImpl(pressureDeviceService);
        //assert
        assertFalse(patientService.haveResponsefromDeviceinLast5days(patient));
    }

    @Test
    void given3LastMeasuresOutOfRangeFromPatientOver70MaleThenReturnTrue() {
        PressureDevice pressureDevice = new PressureDevice(new Long(1), new ArrayList<>());
        PressureLog pressureLog = new PressureLog(new Long(1), LocalDate.now().minusDays(3L), 200);
        PressureLog pressureLog1 = new PressureLog(new Long(2), LocalDate.now().minusDays(2L), 180);
        PressureLog pressureLog2 = new PressureLog(new Long(3), LocalDate.now().minusDays(1L), 160);
        List<PressureLog> pressureLogs = pressureDevice.getPressureLogs();
        pressureLogs.add(pressureLog);
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);
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

        patientService = new PatientServiceImpl(pressureDeviceService);

        assertTrue(patientService.last3MeasuresOutOfRange(patient));
    }

    @Test
    void givenListOfLogsAfterOneYearThenAfterEliminationListIsEmpty() {
        PressureDevice pressureDevice = new PressureDevice(new Long(1), new ArrayList<>());
        PressureLog pressureLog = new PressureLog(new Long(1), LocalDate.now().minusYears(3L), 200);
        PressureLog pressureLog1 = new PressureLog(new Long(2), LocalDate.now().minusYears(2L), 180);
        PressureLog pressureLog2 = new PressureLog(new Long(3), LocalDate.now().minusYears(2L), 160);
        List<PressureLog> pressureLogs = pressureDevice.getPressureLogs();
        pressureLogs.add(pressureLog);
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);
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

        patientService = new PatientServiceImpl(pressureDeviceServiceWithImpl);
        patientService.deleteLogBeforeThisYear(patient);
        assertTrue(patient.getPressureDevice().getPressureLogs().isEmpty());
    }
}
