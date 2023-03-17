package it.euris.service.patient;

import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PatientServiceImplTest {
    private PatientServiceImpl patientService;
    private PressureDevice pressureDevice;



    @BeforeEach
    void setUp() {
        patientService = new PatientServiceImpl(new PressureDeviceServiceImpl());
        pressureDevice = new PressureDevice(1L, new ArrayList<>());
    }

    @Test
    void haveResponsefromDeviceinLast5days_shouldReturnTrue() {
        // crea un paziente con un registro della pressione di 4 giorni fa
        PressureLog pressureLog = new PressureLog(1L,LocalDate.now().minusDays(4), 120);
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(pressureLog);
        Patient patient = new Patient(1L, "mario", "balotelli", "via dante 22", "mb@email.com",'M',LocalDate.now().minusYears(20), pressureDevice);

        assertTrue(patientService.haveResponsefromDeviceinLast5days(patient));
    }

    @Test
    void haveResponsefromDeviceinLast5days_shouldReturnFalse() {
        // crea un paziente con un registro della pressione di 10 giorni fa
        PressureLog pressureLog = new PressureLog(1L, LocalDate.now().minusDays(10), 120);
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(pressureLog);
        Patient patient = new Patient(1L, "mario", "balotelli", "via dante 22", "mb@email.com",'M',LocalDate.now().minusYears(20), pressureDevice);

        assertFalse(patientService.haveResponsefromDeviceinLast5days(patient));
    }

    @Test
    void last3MeasuresOutOfRange_shouldReturnTrue() {
        // crea una paziente di oltre 70 anni con tre registri di pressione troppo alti
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(new PressureLog(1L,LocalDate.now().minusDays(2), 150));
        pressureLogs.add(new PressureLog(2L,LocalDate.now().minusDays(1), 160));
        pressureLogs.add(new PressureLog(3L,LocalDate.now(), 155));
        Patient patient = new Patient(1L, "mario", "balotelli", "via dante 22", "mb@email.com",'M',LocalDate.now().minusYears(20), pressureDevice);

        assertTrue(patientService.last3MeasuresOutOfRange(patient));
    }

    @Test
    void last3MeasuresOutOfRange_shouldReturnFalse() {
        // crea un paziente di sesso maschile di età inferiore ai 50 anni con tre registri della pressione tutti nel raggio d'azione
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(new PressureLog(1L, LocalDate.now().minusDays(2), 120));
        pressureLogs.add(new PressureLog(2L, LocalDate.now().minusDays(1), 125));
        pressureLogs.add(new PressureLog(3L, LocalDate.now(), 128));
        Patient patient = new Patient(1L, "mario", "balotelli", "via dante 22", "mb@email.com",'M',LocalDate.now().minusYears(20), pressureDevice);

        assertFalse(patientService.last3MeasuresOutOfRange(patient));
    }

    @Test
    void deleteLogBeforeThisYear_shouldDeleteOldPressureLogs() {
        // crea un paziente con alcuni vecchi registri di pressione
        PressureLog oldLog = new PressureLog(1L, LocalDate.now().minusYears(1), 120);
        PressureLog recentLog = new PressureLog( 2L,LocalDate.now(), 125);
        List<PressureLog> pressureLogs = new ArrayList<>();
        pressureLogs.add(oldLog);
        pressureLogs.add(recentLog);
        Patient patient = new Patient(1L, "mario", "balotelli", "via dante 22", "mb@email.com",'M',LocalDate.now().minusYears(20), pressureDevice);

        // elimina i vecchi log
        patientService.deleteLogBeforeThisYear(patient);

        // assicura che il vecchio registro sia stato cancellato
        assertFalse(patient.getPressureDevice().getPressureLogs().contains(oldLog));
        assertTrue(patient.getPressureDevice().getPressureLogs().contains(recentLog));
    }
}