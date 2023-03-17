package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.patient.PatientService;
import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PatientServiceTest {

    PatientService patientService;
    PressureDeviceServiceDummy pressureDeviceService;

    @Mock
    PressureDeviceService pressureDevice;

    @BeforeEach
    void setup() {
        pressureDeviceService=new PressureDeviceServiceDummy();
    }

    //DUMMY
    @Test
    void givenPatientWithNoPressureLogsin5DaysThenReturnFalse() {
        //arrange
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),200);
        pressureDevice.getPressureLogs().add(pressureLog);
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
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertFalse(patientService.haveResponsefromDeviceinLast5days(patient));
    }

    @Test
    void given3LastMeasuresOutOfRangeFromPatientOver70MaleThenReturnTrue(){
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(3L),200);
        PressureLog pressureLog1=new PressureLog(new Long(2), LocalDate.now().minusDays(2L),180);
        PressureLog pressureLog2=new PressureLog(new Long(3), LocalDate.now().minusDays(1L),160);
        List<PressureLog> pressureLogs=pressureDevice.getPressureLogs();
        pressureLogs.add(pressureLog);
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);
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

        patientService=new PatientServiceImpl(pressureDeviceService);

        assertTrue(patientService.last3MeasuresOutOfRange(patient));
    }

    @Test
    void givenListPressureLogsAfterOneYearThenAfterEliminationTheListIsEmpty(){
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusYears(3L),200);
        PressureLog pressureLog1=new PressureLog(new Long(2), LocalDate.now().minusDays(2L),180);
        PressureLog pressureLog2=new PressureLog(new Long(3), LocalDate.now().minusDays(2L),160);
        List<PressureLog> pressureLogs=pressureDevice.getPressureLogs();
        pressureLogs.add(pressureLog);
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);

        patientService=new PatientServiceImpl(this.pressureDevice);
      //  Mockito
    }

}
