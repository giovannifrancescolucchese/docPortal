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


}
