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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    void givenPatientWithPressureLogsIn5DaysThenReturnTrue() {
        //arrange
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(4L),200);
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
        assertTrue(patientService.haveResponsefromDeviceinLast5days(patient));
    }

    @Test
    void givenPatientFemale1YearsOldWith3MeasuresOutOfRangeThenReturnTrue(){
        //arrange
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),59);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'F',
                LocalDate.of(2022, Month.JULY, 29),
                pressureDevice
        );
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertTrue(patientService.last3MeasuresOutOfRange(patient));
    }

    @Test
    void givenPatientFemale1YearsOldWith0MeasuresOutOfRangeThenReturnFalse(){
        //arrange
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),61);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'F',
                LocalDate.of(2022, Month.JULY, 29),
                pressureDevice
        );
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertFalse(patientService.last3MeasuresOutOfRange(patient));
    }

    // can be done with parameterized test
    @ParameterizedTest(name = "GivenFirstPressionValue {0} And SecondPressureValue {1} AndThirdPressureValue {2} WhenCheckLast3MeasuresReturn {3}")
    @CsvSource({
            "69, 69, 69, " + true,
            "70, 70, 70, " + false,
            "71, 71, 71, " + false,
            "100, 100, 100, " + false,
            "129, 129, 129, " + false,
            "130, 130, 130, " + false,
            "131, 131, 131, " + true,
    })
    void checkLast3MeasuresOutOfRangeOfMale0YearsOldWithBvaValues(int firstPressure, int secondPressure, int thirdPressure, boolean expectedResult ){
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),firstPressure);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),secondPressure);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),thirdPressure);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(2022, Month.JULY, 29),
                pressureDevice
        );
        //act
        patientService=new PatientServiceImpl(pressureDeviceService);
        //assert
        assertEquals(expectedResult, patientService.last3MeasuresOutOfRange(patient));
    }
}
