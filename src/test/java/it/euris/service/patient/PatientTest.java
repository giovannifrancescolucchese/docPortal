package it.euris.service.patient;

import it.euris.model.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientTest {

    @Test
    void checkCreationOfPatient() {
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );

        assertEquals(1L, patient.getId());
        assertEquals("nome", patient.getName());
        assertEquals("cognome", patient.getSurname());
        assertEquals("indirizzo", patient.getAddress());
        assertEquals("email", patient.getEmail());
        assertEquals(LocalDate.of(1915, Month.JULY, 29), patient.getBirthDate());
        assertNull(patient.getPressureDevice());
    }

    @Test
    void checkAgeOfPatient() {
        Patient patient = new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                null
        );

        assertEquals(108, patient.getAge());
    }
}
