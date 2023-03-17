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

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class PatientServiceTest {

    PatientService patientService;
    PressureDeviceService pressureDeviceService;

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
    void givenPatientWithLast3MeasuresOutOfRangeWhenCheckingThenExpectedTrue(){
        //arrange
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
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertTrue(patientService.last3MeasuresOutOfRange(patient));
    }


    @Test
    void givenPatientWithLast3MeasuresInRangeWhenCheckingThenExpectedFalse(){
        //arrange
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog1=new PressureLog(new Long(1), LocalDate.now().minusDays(3),100);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(2),100);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now().minusDays(1),100);
        pressureDevice.getPressureLogs().add(pressureLog1);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'F',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        );
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertFalse(patientService.last3MeasuresOutOfRange(patient));
    }
}
