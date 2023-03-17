package it.euris.service.doctor;

import it.euris.model.Doctor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoctorTest {

    @Test
    void checkCreationOfDoctor() {

        Doctor doctor = new Doctor(1L, "nomeDottore", "cognomeDotttore", "Via Garibaldi 1", "dottore@email.it", new ArrayList<>(), true);

        assertEquals(1L, doctor.getId());
        assertEquals("nomeDottore", doctor.getName());
        assertEquals("cognomeDotttore", doctor.getSurname());
        assertEquals("Via Garibaldi 1", doctor.getAddress());
        assertEquals("dottore@email.it", doctor.getEmail());
        assertTrue(doctor.getPatients().isEmpty());
        assertTrue(doctor.getMorePatients());
    }
}
